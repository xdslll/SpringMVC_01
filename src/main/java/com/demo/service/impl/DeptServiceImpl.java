package com.demo.service.impl;

import com.demo.dao.EnfordProductCityMapper;
import com.demo.dao.EnfordProductCompetitorsMapper;
import com.demo.dao.EnfordProductDepartmentMapper;
import com.demo.model.EnfordProductCity;
import com.demo.model.EnfordProductCompetitors;
import com.demo.model.EnfordProductDepartment;
import com.demo.service.DeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiads
 * @date 16/2/5
 */
@Service("deptService")
public class DeptServiceImpl implements DeptService {

    @Resource
    EnfordProductDepartmentMapper deptMapper;

    @Resource
    EnfordProductCityMapper cityMapper;

    @Resource
    EnfordProductCompetitorsMapper competitorsMapper;

    @Override
    public List<EnfordProductDepartment> selectAll() {
        List<EnfordProductDepartment> depts = deptMapper.selectAll();
        for (int i = 0; i < depts.size(); i++) {
            EnfordProductDepartment dept = depts.get(i);
            int cityId = dept.getCityId();
            int compId = dept.getCompId();
            EnfordProductCity city = cityMapper.selectByPrimaryKey(cityId);
            EnfordProductCompetitors comp = competitorsMapper.selectByPrimaryKey(compId);
            if (city != null) {
                dept.setCityName(city.getName());
            }
            if (comp != null) {
                dept.setCompName(comp.getName());
            }
        }
        return depts;
    }

    @Override
    public int addDept(EnfordProductDepartment dept) {
        return deptMapper.insert(dept);
    }

    @Override
    public int updateDept(EnfordProductDepartment dept) {
        return deptMapper.updateByPrimaryKeySelective(dept);
    }

    @Override
    public int deleteDept(int id) {
        return deptMapper.deleteByPrimaryKey(id);
    }
}
