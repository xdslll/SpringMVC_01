package com.demo.service.impl;

import com.demo.dao.*;
import com.demo.model.*;
import com.demo.service.EnfordProductPriceService;
import com.demo.util.Consts;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/2/7
 */
@Service("priceService")
public class EnfordProductPriceServiceImpl implements EnfordProductPriceService, Consts {

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
    public List<EnfordProductPrice> getProductPrice(Map<String, Object> param) {
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
    public int countProductPrice(Map<String, Object> param) {
        return priceMapper.countByParam(param);
    }
}
