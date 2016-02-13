package com.demo.service.impl;

import com.demo.dao.EnfordProductCategoryMapper;
import com.demo.dao.EnfordSystemUserMapper;
import com.demo.model.EnfordProductCategory;
import com.demo.model.EnfordSystemUser;
import com.demo.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/2/5
 */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Resource
    EnfordProductCategoryMapper categoryMapper;

    @Resource
    EnfordSystemUserMapper userMapper;

    @Override
    public List<EnfordProductCategory> getAllCategory() {
        List<EnfordProductCategory> categoryList = categoryMapper.select();
        EnfordProductCategory root = null;
        for (EnfordProductCategory cat : categoryList) {
            if (cat.getParent() == -1) {
                root = cat;
            }
            EnfordSystemUser user = userMapper.selectByPrimaryKey(cat.getCreateBy());
            if (user != null) {
                cat.setCreateUsername(user.getName());
            }
        }
        if (root != null) {
            categoryList.remove(root);
        }
        return categoryList;
    }

    @Override
    public List<EnfordProductCategory> getRootCategory(int resId, int deptId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("parent", 0);
        List<EnfordProductCategory> categoryList = categoryMapper.selectByParam(param);
        for (int i = 0; i < categoryList.size(); i++) {
            EnfordProductCategory category = categoryList.get(i);
            //统计该分类下的商品总数
            param.clear();
            param.put("parent", category.getCode());
            param.put("resId", resId);
            int codCount = categoryMapper.countCodCount(param);
            category.setCodCount(codCount);
            //统计该分类下的市调进度
            param.clear();
            param.put("parent", category.getCode());
            param.put("resId", resId);
            param.put("deptId", deptId);
            int finishCount = categoryMapper.countHaveFinished(param);
            category.setHaveFinished(finishCount);
        }
        return categoryList;
    }

    @Override
    public int update(EnfordProductCategory category) {
        return categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public List<EnfordProductCategory> getCategoryByParam(Map<String, Object> param) {
        return categoryMapper.selectByParam(param);
    }
}
