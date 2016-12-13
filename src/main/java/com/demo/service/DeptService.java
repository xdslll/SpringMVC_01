package com.demo.service;

import com.demo.model.EnfordProductDepartment;

import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/2/5
 */
public interface DeptService {

    List<EnfordProductDepartment> selectAll();

    List<EnfordProductDepartment> get(Map<String, Object> param);

    int addDept(EnfordProductDepartment dept);

    int updateDept(EnfordProductDepartment dept);

    int deleteDept(int id);

    EnfordProductDepartment getDepartmentByDeptId(int deptId);

    EnfordProductDepartment getDepartmentByDeptCode(String deptCode);

    int count(Map<String, Object> param);

    List<EnfordProductDepartment> getNotInArea(int areaId);

    int generate(int deptId, int num, int roleId);
}
