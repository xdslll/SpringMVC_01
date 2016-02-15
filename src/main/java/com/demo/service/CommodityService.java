package com.demo.service;

import com.demo.model.EnfordProductCommodity;

import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/2/5
 */
public interface CommodityService {

    List<EnfordProductCommodity> select(Map<String, Object> param);

    List<EnfordProductCommodity> selectByParam(Map<String, Object> param);

    int count(Map<String, Object> param);
}
