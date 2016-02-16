package com.demo.service.impl;

import com.demo.dao.*;
import com.demo.model.*;
import com.demo.service.CommodityPriceService;
import com.demo.util.Consts;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}
