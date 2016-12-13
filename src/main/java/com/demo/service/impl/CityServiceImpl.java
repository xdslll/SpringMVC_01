package com.demo.service.impl;

import com.demo.dao.EnfordProductCityMapper;
import com.demo.dao.EnfordProductDepartmentMapper;
import com.demo.model.EnfordProductCity;
import com.demo.model.EnfordProductDepartment;
import com.demo.service.CityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/3/3
 */
@Service
public class CityServiceImpl implements CityService {

    @Resource
    EnfordProductCityMapper cityMapper;

    @Resource
    EnfordProductDepartmentMapper deptMapper;

    @Override
    public List<EnfordProductCity> getCity(Map<String, Object> param) {
        return cityMapper.selectByParam(param);
    }

    @Override
    public int addCity(EnfordProductCity city) {
        return cityMapper.insertSelective(city);
    }

    @Override
    public int updateCity(EnfordProductCity city) {
        return cityMapper.updateByPrimaryKeySelective(city);
    }

    @Override
    public int deleteCity(int id) {
        return cityMapper.deleteByPrimaryKey(id);
    }

    @Override
    public EnfordProductCity getCityById(int id) {
        EnfordProductCity city = cityMapper.selectByPrimaryKey(id);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("cityId", city.getId());
        List<EnfordProductDepartment> deptList = deptMapper.selectByParam(param);
        city.setChildren(deptList);
        return city;
    }

    @Override
    public EnfordProductCity getCityByName(String name) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("name", name);
        List<EnfordProductCity> cityList = getCity(param);
        if (cityList != null && cityList.size() > 0) {
            return cityList.get(0);
        } else {
            return null;
        }
    }
}
