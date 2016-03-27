package com.demo.service;

import com.demo.exception.BusinessException;
import com.demo.model.EnfordProductArea;
import com.demo.model.EnfordProductDepartment;

import java.util.List;

/**
 * @author xiads
 * @date 16/1/30
 */
public interface AreaService {

    List<EnfordProductArea> getAreaTree(int resId);

    List<EnfordProductDepartment> getAreaDept(int resId, String areaName);
}