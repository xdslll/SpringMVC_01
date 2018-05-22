package com.demo.service.impl;

import com.demo.dao.*;
import com.demo.model.EnfordMarketResearch;
import com.demo.model.EnfordProductCommodity;
import com.demo.model.EnfordProductCompetitors;
import com.demo.model.EnfordProductPrice;
import com.demo.model.EnfordSystemUser;
import com.demo.service.CommodityPriceService;
import com.demo.sync.SQLServerHandler;
import com.demo.util.Consts;
import org.apache.poi.util.SystemOutLogger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/2/7
 */
@Service("priceService")
public class CommodityPriceServiceImpl implements CommodityPriceService, Consts {

    @Resource
    EnfordProductPriceMapper priceMapper;

    @Resource
    EnfordMarketResearchMapper researchMapper;

    @Resource
    EnfordProductCommodityMapper commodityMapper;

    @Resource
    EnfordProductCompetitorsMapper compMapper;

    @Resource
    EnfordSystemUserMapper userMapper;

    @Override
    public List<EnfordProductPrice> getPrice(Map<String, Object> param) {
        List<EnfordProductPrice> priceList = priceMapper.selectByParam(param);
        for (EnfordProductPrice price : priceList) {
            //写入市调信息
            int resId = price.getResId();
            EnfordMarketResearch research = researchMapper.selectByPrimaryKey(resId);
            if (research != null) {
                price.setResName(research.getName());
            }
            //写入商品信息
            int codId = price.getComId();
            EnfordProductCommodity cod = commodityMapper.selectByPrimaryKey(codId);
            if (cod != null) {
                price.setComName(cod.getpName());
            }
            //写入门店信息
            Integer compId = price.getCompId();
            if (compId != null) {
                EnfordProductCompetitors comp = compMapper.selectByPrimaryKey(compId);
                if (comp != null) {
                    price.setCompName(comp.getName());
                } else {
                    price.setCompName("我司");
                }
            } else {
                price.setCompName("我司");
            }
            //写入缺货状态
            int missState = price.getMiss();
            switch (missState) {
                case MISS_TAG_MISS:
                    price.setMissDesp("缺货");
                    break;
                case MISS_TAG_NOT_MISS:
                    price.setMissDesp("有货");
                    break;
            }
            int uploadUserId = price.getUploadBy();
            EnfordSystemUser user = userMapper.selectByPrimaryKey(uploadUserId);
            if (user != null) {
                price.setUploadName(user.getName());
            }
        }
        return priceList;
    }

    @Override
    public int countPrice(Map<String, Object> param) {
        return priceMapper.countByParam(param);
    }

    @Override
    public int addPrice(EnfordProductPrice price) {
        //判断该竞争对手的价格是否已经录入
        int resId = price.getResId();
        int compId = price.getCompId();
        int comId = price.getComId();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("resId", resId);
        param.put("compId", compId);
        param.put("comId", comId);
        int size = priceMapper.countByParam(param);
        //如果已经录入则更新,如果未录入,则新增
        if (size > 0) {
            return priceMapper.updateByPrimaryKeySelective(price);
        } else {
            return priceMapper.insertSelective(price);
        }
    }

    @Override
    public int updatePrice(EnfordProductPrice price) {
        return priceMapper.updateByPrimaryKeySelective(price);
    }

    @Override
    public int addPriceToSQLServer(EnfordProductPrice price) {
        try {
            // System.out.println("开始新增价格到同步服务器");
            // System.out.println(price.toString());
            //获取商品编码
            // System.out.println("商品ID:" + price.getComId());
            // System.out.println("市调ID:" + price.getResId());

            EnfordProductCommodity commodity = commodityMapper.selectByPrimaryKey(price.getComId());
            // System.out.println(commodity.toString());
            price.setCode(commodity.getCode());

            //获取市调清单编号
            EnfordMarketResearch research = researchMapper.selectByPrimaryKey(price.getResId());
            // System.out.println(research.toString());
            price.setBillNumber(research.getBillNumber());

            // System.out.println(price.toString());
            //添加价格到同步服务器
            SQLServerHandler sqlServerHandler = new SQLServerHandler();
            return sqlServerHandler.addPrice(price);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
