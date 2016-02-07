package com.demo.service;

import com.demo.model.EnfordProductPrice;

import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/2/7
 */
public interface EnfordProductPriceService {

    List<EnfordProductPrice> getProductPrice(Map<String, Object> param);

    int countProductPrice(Map<String, Object> param);

}
