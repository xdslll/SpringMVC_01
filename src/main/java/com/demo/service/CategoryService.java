package com.demo.service;

import com.demo.model.EnfordProductCategory;

import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/2/5
 */
public interface CategoryService {

    List<EnfordProductCategory> getAllCategory();

    List<EnfordProductCategory> getRootCategory(int resId, int deptId);

    int update(EnfordProductCategory category);

    List<EnfordProductCategory> getCategoryByParam(Map<String, Object> param);

}
