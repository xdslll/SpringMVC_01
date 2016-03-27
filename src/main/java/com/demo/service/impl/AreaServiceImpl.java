package com.demo.service.impl;

import com.demo.dao.EnfordMarketResearchDeptMapper;
import com.demo.dao.EnfordProductAreaMapper;
import com.demo.dao.EnfordProductDepartmentMapper;
import com.demo.model.EnfordProductArea;
import com.demo.model.EnfordProductDepartment;
import com.demo.service.AreaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<EnfordProductDepartment> getAreaDept(int resId, String areaName) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("areaName", areaName);
        param.put("resId", resId);
        return deptMapper.selectByAreaName(param);
    }
}
