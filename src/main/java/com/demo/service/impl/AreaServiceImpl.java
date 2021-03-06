package com.demo.service.impl;

import com.demo.dao.EnfordMarketResearchDeptMapper;
import com.demo.dao.EnfordProductAreaMapper;
import com.demo.dao.EnfordProductDepartmentMapper;
import com.demo.model.*;
import com.demo.service.AreaService;
import com.demo.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author xiads
 * @date 16/3/3
 */
@Service
public class AreaServiceImpl implements AreaService {

    @Resource
    EnfordProductAreaMapper areaMapper;

    @Resource
    EnfordProductDepartmentMapper deptMapper;

    @Resource
    EnfordMarketResearchDeptMapper researchDeptMapper;

    @Resource
    CategoryService categoryService;

    @Override
    public List<EnfordProductArea> getAreaTree(int resId) {
        //查询所有的区域
        Map<String, Object> param = new HashMap<String, Object>();
        List<EnfordProductArea> areaList = areaMapper.selectByParam(null);
        int id = 1;
        if (areaList != null && areaList.size() > 0) {
            for (int i = 0; i < areaList.size(); i++) {
                EnfordProductArea area = areaList.get(i);
                //查询该区域下的所有部门
                param.clear();
                param.put("areaId", area.getId());
                param.put("resId", resId);
                area.setId(id++);
                List<EnfordProductDepartment> deptList =
                        deptMapper.selectByResId(param);
                //更新部门id
                for (int j = 0; j < deptList.size(); j++) {
                    deptList.get(j).setId(id++);
                }
                area.setChildren(deptList);
            }
        }
        //创建一个未划分区域
        EnfordProductArea emptyArea = new EnfordProductArea();
        emptyArea.setId(id++);
        emptyArea.setName("未划分区分");
        //查询所有未划分区域的部门
        param.clear();
        param.put("areaId", -1);
        List<EnfordProductDepartment> deptList =
                deptMapper.selectByParam(param);
        //更新部门id
        for (int j = 0; j < deptList.size(); j++) {
            deptList.get(j).setId(id++);
        }
        emptyArea.setChildren(deptList);
        if (areaList == null) {
            areaList = new ArrayList<EnfordProductArea>();
        }
        areaList.add(emptyArea);
        return areaList;
    }

    @Override
    public EnfordProductArea getAreaDeptTree(int areaId) {
        EnfordProductArea area = areaMapper.selectByPrimaryKey(areaId);
        if (area != null) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("areaId", area.getId());
            List<EnfordProductDepartment> deptList = null;
            deptList = deptMapper.selectByParam(param);
            for (EnfordProductDepartment dept : deptList) {
                dept.setAreaName(area.getName());
                param.clear();
                param.put("exeId", dept.getId());
                dept.setResCount(researchDeptMapper.countByParam(param));
            }
            area.setChildren(deptList);
        } else if (areaId == 0) {
            area = new EnfordProductArea();
            area.setId(0);
            area.setName("全部区域");
            Map<String, Object> param = new HashMap<String, Object>();
            List<EnfordProductDepartment> deptList = deptMapper.selectAll();
            for (EnfordProductDepartment dept : deptList) {
                dept.setAreaName("全部区域");
                param.clear();
                param.put("exeId", dept.getId());
                dept.setResCount(researchDeptMapper.countByParam(param));
            }
            area.setChildren(deptList);
        }
        return area;
    }

    @Override
    public List<EnfordProductDepartment> getAreaDept(int resId, String areaName) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("areaName", areaName);
        param.put("resId", resId);
        return deptMapper.selectByAreaName(param);
    }

    @Override
    public List<EnfordProductArea> getAreaTree2(int resId, EnfordSystemUser user) {
        //判断用户所在的部门
        int deptId = user.getDeptId();
        System.out.println("deptId=" + deptId);
        if (deptId != -1) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("deptId", deptId);
            List<EnfordProductArea> areaList = new ArrayList<EnfordProductArea>();
            EnfordProductArea area = new EnfordProductArea();
            area.setId(1);
            area.setName("区域");
            List<EnfordProductDepartment> deptList = new ArrayList<EnfordProductDepartment>();
            EnfordProductDepartment dept = deptMapper.selectByPrimaryKey(deptId);
            dept.setId(1);
            deptList.add(dept);
            area.setChildren(deptList);
            return areaList;
        } else {
            //查询所有的区域
            Map<String, Object> param = new HashMap<String, Object>();
            List<EnfordProductArea> areaList = areaMapper.selectByParam(null);
            int id = 1;
            if (areaList != null && areaList.size() > 0) {
                for (int i = 0; i < areaList.size(); i++) {
                    EnfordProductArea area = areaList.get(i);
                    //查询该区域下的所有部门
                    param.clear();
                    param.put("areaId", area.getId());
                    param.put("resId", resId);
                    area.setId(id++);
                    List<EnfordProductDepartment> deptList =
                            deptMapper.selectByResId(param);
                    //更新部门id
                    for (int j = 0; j < deptList.size(); j++) {
                        deptList.get(j).setId(id++);
                    }
                }
            }
            //删除多余的,没有子部门的区域
            /*Iterator<EnfordProductArea> areaIterator = areaList.iterator();
            while (areaIterator.hasNext()) {
                EnfordProductArea area = areaIterator.next();
                if (area.getChildren() == null ||
                        area.getChildren().size() == 0) {
                    areaIterator.remove();
                }
            }*/
            //创建一个未划分区域
            EnfordProductArea emptyArea = new EnfordProductArea();
            emptyArea.setId(id++);
            emptyArea.setName("未划分区分");
            //查询所有未划分区域的部门
            param.clear();
            param.put("areaId", -1);
            List<EnfordProductDepartment> deptList =
                    deptMapper.selectByParam(param);
            //更新部门id
            for (int j = 0; j < deptList.size(); j++) {
                deptList.get(j).setId(id++);
            }
            emptyArea.setChildren(deptList);
            if (areaList == null) {
                areaList = new ArrayList<EnfordProductArea>();
            }
            areaList.add(emptyArea);
            return areaList;
        }
    }

    @Override
    public List<EnfordProductArea> getArea(Map<String, Object> param) {
        List<EnfordProductArea> areaList = areaMapper.selectByParam(param);
        for (EnfordProductArea area : areaList) {
            int ifShow = area.getIfShow();
            if (ifShow == 0) {
                area.setIfShowText("显示");
            } else {
                area.setIfShowText("不显示");
            }
        }
        return areaList;
    }

    @Override
    public int addArea(EnfordProductArea area) {
        return areaMapper.insertSelective(area);
    }

    @Override
    public int updateArea(EnfordProductArea area) {
        return areaMapper.updateByPrimaryKeySelective(area);
    }

    @Override
    public int deleteArea(int id) {
        return areaMapper.deleteByPrimaryKey(id);
    }



    @Override
    public EnfordProductArea getAreaDeptTreeByKeyword(int areaId, String keyword, int page, int pageSize) {
        EnfordProductArea area = areaMapper.selectByPrimaryKey(areaId);
        if (area != null) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("areaId", area.getId());
            param.put("keyword", keyword);
            param.put("page", (page - 1) * pageSize);
            param.put("pageSize", pageSize);
            List<EnfordProductDepartment> deptList = null;
            deptList = deptMapper.selectByParam(param);
            for (EnfordProductDepartment dept : deptList) {
                dept.setAreaName(area.getName());
                param.clear();
                param.put("exeId", dept.getId());
                dept.setResCount(researchDeptMapper.countByParam(param));
            }
            area.setChildren(deptList);
        } else if (areaId == 0) {
            area = new EnfordProductArea();
            area.setId(0);
            area.setName("全部区域");
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("keyword", keyword);
            param.put("page", (page - 1) * pageSize);
            param.put("pageSize", pageSize);
            List<EnfordProductDepartment> deptList = deptMapper.selectByParam(param);
            for (EnfordProductDepartment dept : deptList) {
                dept.setAreaName("全部区域");
                param.clear();
                param.put("exeId", dept.getId());
                dept.setResCount(researchDeptMapper.countByParam(param));
            }
            area.setChildren(deptList);
        }
        return area;
    }

    @Override
    public int countByKeyword(Integer areaId, String keyword) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("areaId", areaId);
        param.put("keyword", keyword);
        return deptMapper.countByParam(param);
    }

    @Override
    public EnfordProductArea getAreaDeptTreeByKeywords(String areaIds, String keyword, int page, int pageSize) {
        EnfordProductArea area = null;
        List<EnfordProductDepartment> deptList = new ArrayList<EnfordProductDepartment>();
        if (areaIds.contains(",")) {
            String[] areaIdsStr = areaIds.split(",");
            for (int i = 0; i < areaIdsStr.length; i++) {
                int areaId = Integer.valueOf(areaIdsStr[i]);
                EnfordProductArea area1 = areaMapper.selectByPrimaryKey(areaId);
                deptList.addAll(area1.getChildren());
            }
            area = new EnfordProductArea();
            area.setId(0);
            area.setName("市调区域");
            area.setChildren(deptList);
        } else {
            int areaId = Integer.valueOf(areaIds);
            area = areaMapper.selectByPrimaryKey(areaId);
        }
        return area;
    }

    /**
     * 三期功能:统计区域下的市调进度
     *
     * @param area
     */
    private void statsArea(EnfordProductArea area) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        String yearBegin = year + "-1-1";
        String yearEnd = year + "-12-31";
        String monthBegin = year + "-" + month + "-1";
        String monthEnd = year + "-" + month + "-31";
        statsArea(yearBegin, yearEnd, monthBegin, monthEnd, area);
    }

    /**
     * 三期功能:通过年月和区域进行统计区域
     *
     * @param area
     * @param year
     * @param month
     */
    private void statsAreaByYearAndMonth(EnfordProductArea area, String year, String month) {
        String yearBegin = year + "-1-1";
        String yearEnd = year + "-12-31";
        String monthBegin = year + "-" + month + "-1";
        String monthEnd = year + "-" + month + "-31";
        statsArea(yearBegin, yearEnd, monthBegin, monthEnd, area);
    }

    /**
     * 三期功能:
     *
     * @param yearBegin
     * @param yearEnd
     * @param monthBegin
     * @param monthEnd
     * @param area
     */
    private void statsArea(String yearBegin, String yearEnd, String monthBegin, String monthEnd, EnfordProductArea area) {
        //统计年度市调总数
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDt", yearBegin);
        param.put("endDt", yearEnd);
        param.put("areaId", area.getId());
        int countByYear = areaMapper.statsByParam(param);
        area.setCountByYear(countByYear);
        //统计年度市调总进度
        int resPriceByYear = areaMapper.statsPriceByParam(param);
        int resTotalByYear = areaMapper.statsTotalByParam(param);
        String percentByYear = ((int) (((double) (resPriceByYear) / resTotalByYear) * 100)) + "%";
        area.setFinishPercentByYear(percentByYear);
        area.setFinishByYear(String.valueOf(resPriceByYear));
        area.setNotFinishByYear(String.valueOf(resTotalByYear - resPriceByYear));
        area.setCodCountByYear(String.valueOf(resTotalByYear));
        //统计月度市调总数
        param.clear();
        param.put("startDt", monthBegin);
        param.put("endDt", monthEnd);
        param.put("areaId", area.getId());
        int countByMonth = areaMapper.statsByParam(param);
        area.setCountByMonth(countByMonth);
        //统计月度市调总进度
        int resPriceByMonth = areaMapper.statsPriceByParam(param);
        int resTotalByMonth = areaMapper.statsTotalByParam(param);
        String percentByMonth = ((int) (((double) (resPriceByMonth) / resTotalByMonth) * 100)) + "%";
        area.setFinishPercentByMonth(percentByMonth);
        area.setFinishByMonth(String.valueOf(resPriceByMonth));
        area.setNotFinishByMonth(String.valueOf(resTotalByMonth - resPriceByMonth));
        area.setCodCountByMonth(String.valueOf(resTotalByMonth));
    }

    /**
     * 三期功能:通过年月和区域进行统计部门
     *
     * @param dept
     * @param year
     * @param month
     */
    private void statsDeptByYearAndMonth(EnfordProductDepartment dept, String year, String month) {
        String yearBegin = year + "-1-1";
        String yearEnd = year + "-12-31";
        String monthBegin = year + "-" + month + "-1";
        String monthEnd = year + "-" + month + "-31";
        statsDept(yearBegin, yearEnd, monthBegin, monthEnd, dept);
    }

    /**
     * 三期功能:统计部门市调进度
     *
     * @param dept
     */
    private void statsDept(EnfordProductDepartment dept) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        String yearBegin = year + "-1-1";
        String yearEnd = year + "-12-31";
        String monthBegin = year + "-" + month + "-1";
        String monthEnd = year + "-" + month + "-31";
        statsDept(yearBegin, yearEnd, monthBegin, monthEnd, dept);
    }

    private void statsDept(String yearBegin, String yearEnd, String monthBegin, String monthEnd, EnfordProductDepartment dept) {
        //统计年度市调总数
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDt", yearBegin);
        param.put("endDt", yearEnd);
        param.put("deptId", dept.getId());
        int countByYear = areaMapper.statsByParam(param);
        dept.setCountByYear(countByYear);
        //统计年度市调总进度
        int resPriceByYear = areaMapper.statsPriceByParam(param);
        int resTotalByYear = areaMapper.statsTotalByParam(param);
        String percentByYear = ((int) (((double) (resPriceByYear) / resTotalByYear) * 100)) + "%";
        dept.setFinishPercentByYear(percentByYear);
        dept.setFinishByYear(String.valueOf(resPriceByYear));
        dept.setNotFinishByYear(String.valueOf(resTotalByYear - resPriceByYear));
        dept.setCodCountByYear(String.valueOf(resTotalByYear));
        //统计月度市调总数
        param.clear();
        param.put("startDt", monthBegin);
        param.put("endDt", monthEnd);
        param.put("deptId", dept.getId());
        int countByMonth = areaMapper.statsByParam(param);
        dept.setCountByMonth(countByMonth);
        //统计月度市调总进度
        int resPriceByMonth = areaMapper.statsPriceByParam(param);
        int resTotalByMonth = areaMapper.statsTotalByParam(param);
        String percentByMonth = ((int) (((double) (resPriceByMonth) / resTotalByMonth) * 100)) + "%";
        dept.setFinishPercentByMonth(percentByMonth);
        dept.setFinishByMonth(String.valueOf(resPriceByMonth));
        dept.setNotFinishByMonth(String.valueOf(resTotalByMonth - resPriceByMonth));
        dept.setCodCountByMonth(String.valueOf(resTotalByMonth));
    }

    /**
     * 三期功能:统计区域下的市调进度
     *
     * @param areaIds
     * @return
     */
    @Override
    public List<EnfordProductArea> getAreaStats(List<Integer> areaIds) {
        List<EnfordProductArea> areaList;
        if (areaIds.size() == 1 && areaIds.get(0) == 0) {
            areaList = areaMapper.selectByParam(null);
            Iterator<EnfordProductArea> it = areaList.iterator();
            while (it.hasNext()) {
                EnfordProductArea area = it.next();
                if (area.getIfShow() != 0) {
                    it.remove();
                } else {
                    statsArea(area);
                }
            }
        } else {
            areaList = new ArrayList<EnfordProductArea>();
            for (int id : areaIds) {
                EnfordProductArea area = areaMapper.selectByPrimaryKey(id);
                if (area.getIfShow() == 0) {
                    statsArea(area);
                    areaList.add(area);
                }
            }
        }
        return areaList;
    }

    /**
     * 三期功能,增加按照年月的统计功能
     *
     * @param areaIds
     * @param year
     * @param month
     * @return
     */
    @Override
    public List<EnfordProductArea> getAreaStatsByYearAndMonth(List<Integer> areaIds, String year, String month) {
        List<EnfordProductArea> areaList;
        if (areaIds.size() == 1 && areaIds.get(0) == 0) {
            areaList = areaMapper.selectByParam(null);
            Iterator<EnfordProductArea> it = areaList.iterator();
            while (it.hasNext()) {
                EnfordProductArea area = it.next();
                if (area.getIfShow() != 0) {
                    it.remove();
                } else {
                    statsAreaByYearAndMonth(area, year, month);
                }
            }
        } else {
            areaList = new ArrayList<EnfordProductArea>();
            for (int id : areaIds) {
                EnfordProductArea area = areaMapper.selectByPrimaryKey(id);
                if (area.getIfShow() == 0) {
                    statsAreaByYearAndMonth(area, year, month);
                    areaList.add(area);
                }
            }
        }
        return areaList;
    }

    /**
     * 三期功能:统计某个区域下的所有部门市调进度
     *
     * @param areaId
     * @return
     */
    @Override
    public List<EnfordProductDepartment> getDeptStats(int areaId) {
        List<EnfordProductDepartment> deptList;
        EnfordProductArea area = areaMapper.selectByPrimaryKey(areaId);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("areaId", areaId);
        deptList = deptMapper.selectByParam(param);
        for (EnfordProductDepartment dept: deptList) {
            statsDept(dept);
            dept.setAreaName(area.getName());
        }
        return deptList;
    }

    @Override
    public List<EnfordProductDepartment> getDeptStatsByYearAndMonth(int areaId, String year, String month) {
        List<EnfordProductDepartment> deptList;
        EnfordProductArea area = areaMapper.selectByPrimaryKey(areaId);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("areaId", areaId);
        deptList = deptMapper.selectByParam(param);
        for (EnfordProductDepartment dept: deptList) {
            statsDeptByYearAndMonth(dept, year, month);
            dept.setAreaName(area.getName());
        }
        return deptList;
    }

    @Override
    public List<EnfordProductCategory> getCatStats(int areaId) {
        List<EnfordProductCategory> catList = categoryService.getCategoryWithParent();
        for(EnfordProductCategory cat : catList) {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH) + 1;
            String yearBegin = year + "-1-1";
            String yearEnd = year + "-12-31";
            String monthBegin = year + "-" + month + "-1";
            String monthEnd = year + "-" + month + "-31";
            //统计年度市调总数
            Map<String, Object> param = new HashMap<String, Object>();
            /*yearBegin = "2016-01-01";
            yearEnd = "2016-12-31";
            monthBegin = "2016-12-01";
            monthEnd = "2016-12-31";*/
            param.put("startDt", yearBegin);
            param.put("endDt", yearEnd);
            param.put("areaId", areaId);
            param.put("code", cat.getCode());
            int totalByYear = categoryService.totalByParam(param);
            int finishByYear = categoryService.finishByParam(param);
            String percentByYear = ((int) (((double) (finishByYear) / totalByYear) * 100)) + "%";
            cat.setCodCountByYear(totalByYear);
            cat.setFinishCountByYear(percentByYear);

            param.clear();
            param.put("startDt", monthBegin);
            param.put("endDt", monthEnd);
            param.put("areaId", areaId);
            param.put("code", cat.getCode());
            int totalByMonth = categoryService.totalByParam(param);
            int finishByMonth = categoryService.finishByParam(param);
            String percentByMonth = ((int) (((double) (finishByMonth) / totalByMonth) * 100)) + "%";
            cat.setCodCount(totalByMonth);
            cat.setFinishCountByMonth(percentByMonth);
        }
        return catList;
    }

}
