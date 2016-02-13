package com.demo.service.api;

import com.demo.dao.*;
import com.demo.model.*;
import com.demo.util.Consts;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            if (researchState == RESEARCH_STATE_CANCELED || researchState == RESEARCH_STATE_HAVE_FINISHED
                    || researchState == RESEARCH_STATE_NOT_PUBLISH) {
                continue;
            } else {
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
        return list;
    }

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
            for (int j = 0; j < commodityList.size(); j++) {
                param.clear();
                param.put("resId", resId);
                param.put("deptId", deptId);
                param.put("codId", commodityList.get(j).getId());
                List<EnfordProductPrice> price = apiMapper.selectCommodityPriceByParam(param);
                if (price != null && price.size() > 0) {
                    commodityList.get(j).setPrice(price.get(0));
                }
            }
        }

        return categoryList;
    }
}
