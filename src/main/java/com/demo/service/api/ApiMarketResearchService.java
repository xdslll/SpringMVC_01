package com.demo.service.api;

import com.demo.model.EnfordApiMarketResearch;
import com.demo.model.EnfordMarketLocation;
import com.demo.model.EnfordProductCategory;
import com.demo.model.EnfordProductCommodity;

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

    /**
     * 通过条形码查询商品
     *
     * @param resId
     * @param deptId
     * @param barcode
     * @return
     */
    List<EnfordProductCommodity> getCommodityByBarcode(int resId, int deptId, String barcode,
                                                       int page, int pageSize);

    int countCommodityByBarcode(int resId, int deptId, String barcode);

    int addLocation(EnfordMarketLocation location);

    List<EnfordMarketLocation> getLocation(int resId, int userId, int page, int pageSize);

    int countLocation(int resId, int userId);
}
