package com.demo.service.impl;

import com.demo.dao.EnfordProductCategoryMapper;
import com.demo.dao.EnfordProductCommodityMapper;
import com.demo.dao.EnfordSystemUserMapper;
import com.demo.model.EnfordProductCategory;
import com.demo.model.EnfordProductCommodity;
import com.demo.model.EnfordSystemUser;
import com.demo.service.CommodityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/2/5
 */
@Service("commodityService")
public class CommodityServiceImpl implements CommodityService {

    @Resource
    EnfordProductCommodityMapper commodityMapper;

    @Resource
    EnfordProductCategoryMapper categoryMapper;

    @Resource
    EnfordSystemUserMapper userMapper;

    @Override
    public List<EnfordProductCommodity> select(Map<String, Object> param) {
        List<EnfordProductCommodity> cods = commodityMapper.select(param);
        for (EnfordProductCommodity cod : cods) {
            //获取品类名称
            Integer categoryCode = cod.getCategoryCode();
            Map<String, Object> categoryParam = new HashMap<String, Object>();
            categoryParam.put("code", categoryCode);
            List<EnfordProductCategory> category = categoryMapper.selectByParam(categoryParam);
            if (category != null && category.size() > 0) {
                cod.setCategoryName(category.get(0).getName());
            }
            //获取用户名
            EnfordSystemUser user = userMapper.selectByPrimaryKey(cod.getCreateBy());
            if (user != null) {
                cod.setCreateUsername(user.getName());
            }
        }
        return cods;
    }

    @Override
    public List<EnfordProductCommodity> selectByParam(Map<String, Object> param) {
        return commodityMapper.selectByParam(param);
    }

    @Override
    public int count(Map<String, Object> param) {
        return commodityMapper.count(param);
    }
}
