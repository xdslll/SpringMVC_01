package com.demo.service.impl;

import com.demo.dao.EnfordProductCategoryMapper;
import com.demo.dao.EnfordSystemUserMapper;
import com.demo.model.EnfordProductCategory;
import com.demo.model.EnfordSystemUser;
import com.demo.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    public List<EnfordProductCategory> select() {
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
}
