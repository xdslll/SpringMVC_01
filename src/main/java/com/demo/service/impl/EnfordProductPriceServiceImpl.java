package com.demo.service.impl;

import com.demo.dao.EnfordProductPriceMapper;
import com.demo.model.EnfordProductPrice;
import com.demo.service.EnfordProductPriceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/2/7
 */
@Service("priceService")
public class EnfordProductPriceServiceImpl implements EnfordProductPriceService {

    @Resource
    EnfordProductPriceMapper priceMapper;

    @Override
    public List<EnfordProductPrice> getProductPrice(Map<String, Object> param) {
        List<EnfordProductPrice> priceList = priceMapper.selectByParam(param);
        return priceList;
    }

    @Override
    public int countProductPrice(Map<String, Object> param) {
        return priceMapper.countByParam(param);
    }
}
