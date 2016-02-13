package com.demo.service.api;

import com.demo.model.EnfordApiMarketResearch;
import com.demo.model.EnfordProductCategory;

import java.util.List;

/**
 * @author xiads
 * @date 16/2/12
 */
public interface ApiMarketResearchService {

    /**
     * 根据用户所在部门,获取市调清单的相关信息
     *
     * @param deptId
     * @return
     */
    List<EnfordApiMarketResearch> getMarketResearch(int deptId);

    /**
     * 获取某一分类下的所有商品信息
     *
     * @param resId
     * @param deptId
     * @param code
     * @return
     */
    List<EnfordProductCategory> getResearchCategory(int resId, int deptId, int code);
}
