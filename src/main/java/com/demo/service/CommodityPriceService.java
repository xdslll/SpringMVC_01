package com.demo.service;

import com.demo.model.EnfordProductPrice;

import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/2/7
 */
public interface CommodityPriceService {

    List<EnfordProductPrice> getPrice(Map<String, Object> param);

    int countPrice(Map<String, Object> param);

    int addPrice(EnfordProductPrice price);

    int updatePrice(EnfordProductPrice price);

    /**
     * 三期功能,将价格数据添加到同步服务器
     *
     * @param price
     * @return
     */
    int addPriceToSQLServer(EnfordProductPrice price);

}
