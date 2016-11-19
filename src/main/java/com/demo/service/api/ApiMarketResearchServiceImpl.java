package com.demo.service.api;

import com.demo.dao.*;
import com.demo.model.*;
import com.demo.util.Consts;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
                    //!checkConfirmDate(research.getEndDt())) {
                    //research.getEndDt().getTime() < now.getTime()) {
                    checkEndDate(research.getEndDt()) ||
                    checkEndDate(research.getMrEndDate())) {
                continue;
            } else {
                System.out.println(researchDept.toString());
                /*if (researchDept.getState() == 0 &&
                        researchDept.getEffectiveSign() == 0) {

                }*/
                /*if (researchDept.getState() == 0 || research.getState() == 4) {

                }*/
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
        return list;
    }

    /**
     * 判断当前时间是否超出了最后时间
     *
     * @param endDate
     * @return
     */
    public static boolean checkEndDate(Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        try {
            now = sdf.parse(sdf.format(now));
            System.out.println("当前时间:" + now);
            endDate = sdf.parse(sdf.format(endDate));
            System.out.println("结束时间:" + now);
            System.out.println("判断结果:" + (!endDate.equals(now) || endDate.before(now)));
            if (endDate.equals(now) || endDate.after(now)) {
                return false;
            } else {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * 判断市调清单的确认时间是否已经在当前时间后面
     *
     * @param confirmDate
     * @return
     */
    private boolean checkConfirmDate(Date confirmDate) {
        //将当天日期增加1
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);
        Date now = calendar.getTime();
        //将日期变成年月日后进行对比
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            now = sdf.parse(sdf.format(now));
            System.out.println("当前时间:" + now.toString());
            confirmDate = sdf.parse(sdf.format(confirmDate));
            System.out.println("过期时间:" + confirmDate.toString());
            System.out.println(confirmDate.after(now));
        } catch (ParseException e) {
            e.printStackTrace();
            return true;
        }
        //如果确认时间在当前日期后面,则返回true
        return confirmDate.after(now);
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
