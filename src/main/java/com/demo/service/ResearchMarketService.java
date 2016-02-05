package com.demo.service;

import com.demo.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/2/3
 */
public interface ResearchMarketService {

    /**
     * 统计某一品类数据
     * @param category
     * @return
     */
    int countCategory(EnfordProductCategory category);

    /**
     * 添加品类数据
     * @param category
     * @return
     */
    int addCategory(EnfordProductCategory category);

    /**
     * 更新品类数据
     * @param category
     * @return
     */
    int updateCategory(EnfordProductCategory category);

    /**
     * 删除品类数据
     * @param id
     * @return
     */
    int deleteCategory(int id);

    /**
     * 添加商品信息
     * @param product
     * @return
     */
    int addProductCommodity(EnfordProductCommodity product);

    /**
     * 更新商品信息
     * @param product
     * @return
     */
    int updateProductCommodity(EnfordProductCommodity product);

    /**
     * 删除商品信息
     * @param id
     * @return
     */
    int deleteProductCommodity(int id);

    List<EnfordProductCommodity> selectCommodityByParam(Map<String, Object> param);
    /**
     * 添加供应商信息
     * @param supplier
     * @return
     */
    int addSupplier(EnfordProductSupplier supplier);

    /**
     * 更新供应商信息
     * @param supplier
     * @return
     */
    int updateSupplier(EnfordProductSupplier supplier);

    /**
     * 删除供应商信息
     * @param id
     * @return
     */
    int deleteSupplier(int id);

    /**
     * 通过供应商编码查询供应商
     *
     * @param code
     * @return
     */
    EnfordProductSupplier selectSupplierByCode(String code);

    /**
     * 添加价格信息
     * @param price
     * @return
     */
    int addPrice(EnfordProductPrice price);

    /**
     * 更新价格信息
     * @param price
     * @return
     */
    int updatePrice(EnfordProductPrice price);

    /**
     * 删除价格信息
     * @param key
     * @return
     */
    int deletePrice(EnfordProductPriceKey key);

    /**
     * 添加市调清单信息
     * @param research
     * @return
     */
    int addResearch(EnfordMarketResearch research);

    /**
     * 更新市调清单信息
     * @param research
     * @return
     */
    int updateResearch(EnfordMarketResearch research);

    /**
     * 删除市调清单信息
     * @param id
     * @return
     */
    int deleteResearch(int id);

    /**
     * 通过import id查询市调清单
     * @param importId
     * @return
     */
    EnfordMarketResearch selectByImportId(int importId);

    /**
     * 添加市调清单下的商品
     * @param researchCommodity
     * @return
     */
    int addResearchCommodity(EnfordMarketResearchCommodity researchCommodity);

    /**
     * 更新市调清单下的商品
     * @param researchCommodity
     * @return
     */
    int updateResearchCommodity(EnfordMarketResearchCommodity researchCommodity);

    /**
     * 删除市调清单下的商品
     * @param id
     * @return
     */
    int delteResearchCommodity(int id);

    /**
     * 解析Excel,导入市调清单,品类,商品,供应商,价格等信息
     * @param importHistory
     * @param user
     * @param ifCoverData
     * @return
     */
    boolean importMarketResearchData(
            EnfordProductImportHistory importHistory,
            EnfordSystemUser user,
            boolean ifCoverData);

    boolean importDeptData(
            EnfordProductImportHistory importHistory,
            boolean ifCoverData);
}
