package com.demo.service;

import com.demo.model.EnfordProduct;
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

    List<EnfordProductCategory> getCategoryWithParent();

    int totalByParam(Map<String, Object> param);

    int finishByParam(Map<String, Object> param);
}
