package com.demo.service;

import com.demo.model.EnfordProductDepartment;

import java.util.List;

/**
 * @author xiads
 * @date 16/2/5
 */
public interface DeptService {

    List<EnfordProductDepartment> selectAll();

    int addDept(EnfordProductDepartment dept);

    int updateDept(EnfordProductDepartment dept);

    int deleteDept(int id);

    EnfordProductDepartment getDepartmentByDeptId(int deptId);

    EnfordProductDepartment getDepartmentByDeptCode(String deptCode);
}
