package com.demo.dao;

import com.demo.model.EnfordProductCommodity;
import com.demo.model.EnfordProductPrice;

import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/2/12
 */
public interface EnfordApiMapper {

    /**
     * 查询某一分类下的所有商品信息
     *
     * @param param
     * @return
     */
    List<EnfordProductCommodity> selectCategoryCommodityByParam(Map<String, Object> param);

    List<EnfordProductPrice> selectCommodityPriceByParam(Map<String, Object> param);
}
