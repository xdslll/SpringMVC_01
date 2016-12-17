package com.demo.service.impl;

import com.demo.dao.EnfordMarketResearchDeptMapper;
import com.demo.dao.EnfordProductAreaMapper;
import com.demo.dao.EnfordProductDepartmentMapper;
import com.demo.model.EnfordProductArea;
import com.demo.model.EnfordProductDepartment;
import com.demo.model.EnfordSystemUser;
import com.demo.service.AreaService;
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
        return areaMapper.selectByParam(param);
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
}
