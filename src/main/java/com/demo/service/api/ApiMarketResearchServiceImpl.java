package com.demo.service.api;

import com.demo.dao.*;
import com.demo.model.*;
import com.demo.util.Consts;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author xiads
 * @date 16/2/12
 */
@Service("apiMarketService")
public class ApiMarketResearchServiceImpl implements ApiMarketResearchService, Consts {

    @Resource
    EnfordMarketResearchMapper researchMapper;

    @Resource
    EnfordMarketResearchDeptMapper researchDeptMapper;

    @Resource
    EnfordProductCompetitorsMapper competitorsMapper;

    @Resource
    EnfordProductDepartmentMapper deptMapper;

    @Resource
    EnfordMarketResearchCommodityMapper researchCommodityMapper;

    @Resource
    EnfordProductPriceMapper priceMapper;

    @Resource
    EnfordApiMapper apiMapper;

    @Resource
    EnfordProductCategoryMapper categoryMapper;

    @Resource
    EnfordMarketLocationMapper locationMapper;

    @Override
    public List<EnfordApiMarketResearch> getMarketResearch(int deptId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("exeId", deptId);
        List<EnfordMarketResearchDept> researchDeptList = researchDeptMapper.selectByParam(param);
        List<EnfordApiMarketResearch> list = new ArrayList<EnfordApiMarketResearch>();
        for (int i = 0; i < researchDeptList.size(); i++) {
            EnfordMarketResearchDept researchDept = researchDeptList.get(i);
            //查询市调清单信息
            int resId = researchDept.getResId();
            EnfordMarketResearch research = researchMapper.selectByPrimaryKey(resId);
            //判断市调清单的状态
            int researchState = research.getState();
            Date now = new Date();
            System.out.println(research.toString());
            if (researchState == RESEARCH_STATE_CANCELED ||
                    researchState == RESEARCH_STATE_HAVE_FINISHED ||
                    researchState == RESEARCH_STATE_NOT_PUBLISH ||
                    research.getEndDt().getTime() < now.getTime()) {
                continue;
            } else {
                System.out.println(researchDept.toString());
                if ((researchDept.getState() == 1 ||
                        researchDept.getState() == 0) &&
                        researchDept.getEffectiveSign() == 0) {
                    EnfordApiMarketResearch apiMarketResearch = new EnfordApiMarketResearch();
                    //查询门店信息
                    EnfordProductDepartment dept = deptMapper.selectByPrimaryKey(deptId);
                    //查询竞争门店信息
                    int compId = researchDept.getCompId();
                    EnfordProductCompetitors comp = competitorsMapper.selectByPrimaryKey(compId);
                    //查询商品总数
                    param.clear();
                    param.put("resId", resId);
                    int codCount = researchCommodityMapper.countByParam(param);
                    //计算完成进度
                    param.clear();
                    param.put("compId", compId);
                    param.put("resId", resId);
                    int finishCount = priceMapper.countByParam(param);
                    float finishPercentFloat = (float) finishCount / codCount;
                    int finishPercent = (int) (finishPercentFloat * 100);
                    apiMarketResearch.setResearch(research);
                    apiMarketResearch.setDept(dept);
                    apiMarketResearch.setComp(comp);
                    apiMarketResearch.setCodCount(codCount);
                    apiMarketResearch.setHaveFinished(finishPercent);
                    list.add(apiMarketResearch);
                }
            }
        }
        return list;
    }

    /**
     * 获取某个分类下的所有市调商品
     *
     * @param resId
     * @param deptId
     * @param code
     * @return
     */
    @Override
    public List<EnfordProductCategory> getResearchCategory(int resId, int deptId, int code) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("parent", code);
        List<EnfordProductCategory> categoryList = categoryMapper.selectByParam(param);
        for (int i = 0; i < categoryList.size(); i++) {
            EnfordProductCategory category = categoryList.get(i);
            param.clear();
            param.put("resId", resId);
            param.put("deptId", deptId);
            param.put("code", category.getCode());
            List<EnfordProductCommodity> commodityList = apiMapper.selectCategoryCommodityByParam(param);
            category.setCodCount(commodityList.size());
            category.setCommodityList(commodityList);
            //搜索市调商品的价格,根据竞争对手来搜索价格
            for (int j = 0; j < commodityList.size(); j++) {
                EnfordProductDepartment dept = deptMapper.selectByPrimaryKey(deptId);
                param.clear();
                param.put("resId", resId);
                //param.put("deptId", deptId);
                param.put("compId", dept.getCompId());
                param.put("codId", commodityList.get(j).getId());
                param.put("page", 0);
                param.put("pageSize", 1);
                List<EnfordProductPrice> price = apiMapper.selectCommodityPriceByParam(param);
                if (price != null && price.size() > 0) {
                    commodityList.get(j).setPrice(price.get(0));
                }
            }
        }

        return categoryList;
    }

    @Override
    public List<EnfordProductCommodity> getCommodityByBarcode(int resId, int deptId, String barcode,
                                                              int page, int pageSize) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("resId", resId);
        param.put("deptId", deptId);
        param.put("barcode", barcode);
        param.put("page", page);
        param.put("pageSize", pageSize);
        List<EnfordProductCommodity> commodityList = apiMapper.selectCommodityByBarcode(param);
        for (int i = 0; i < commodityList.size(); i++) {
            EnfordProductCommodity commodity = commodityList.get(i);
            param.clear();
            param.put("resId", resId);
            param.put("deptId", deptId);
            param.put("codId", commodity.getId());
            param.put("page", 0);
            param.put("pageSize", 1);
            List<EnfordProductPrice> price = apiMapper.selectCommodityPriceByParam(param);
            if (price != null && price.size() > 0) {
                commodity.setPrice(price.get(0));
            }
        }
        return commodityList;
    }

    @Override
    public int countCommodityByBarcode(int resId, int deptId, String barcode) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("resId", resId);
        param.put("deptId", deptId);
        param.put("barcode", barcode);
        return apiMapper.countCommodityByBarcode(param);
    }

    @Override
    public int addLocation(EnfordMarketLocation location) {
        return locationMapper.insertSelective(location);
    }

    @Override
    public List<EnfordMarketLocation> getLocation(int resId, int userId, int page, int pageSize) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("resId", resId);
        param.put("userId", userId);
        if (page != -1 && pageSize != -1) {
            param.put("page", page);
            param.put("pageSize", pageSize);
        }
        return locationMapper.selectLocationByParam(param);
    }

    @Override
    public int countLocation(int resId, int userId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("resId", resId);
        param.put("userId", userId);
        return locationMapper.countLocation(param);
    }
}
