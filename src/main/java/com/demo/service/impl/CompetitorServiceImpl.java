package com.demo.service.impl;

import com.demo.dao.EnfordProductCityMapper;
import com.demo.dao.EnfordProductCompetitorsMapper;
import com.demo.dao.EnfordProductDepartmentMapper;
import com.demo.model.EnfordProductCity;
import com.demo.model.EnfordProductCompetitors;
import com.demo.model.EnfordProductDepartment;
import com.demo.service.CompetitorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/2/5
 */
@Service("competitorService")
public class CompetitorServiceImpl implements CompetitorService {

    @Resource
    EnfordProductCompetitorsMapper competitorsMapper;

    @Resource
    EnfordProductCityMapper cityMapper;

    @Resource
    EnfordProductDepartmentMapper deptMapper;

    @Override
    public List<EnfordProductCompetitors> select() {
        List<EnfordProductCompetitors> competitors = competitorsMapper.select();
        for (EnfordProductCompetitors competitor : competitors) {
            int cityId = competitor.getCityId();
            EnfordProductCity city = cityMapper.selectByPrimaryKey(cityId);
            if (city != null) {
                competitor.setCityName(city.getName());
            }
        }
        return competitors;
    }

    @Override
    public int addComp(EnfordProductCompetitors comp) {
        return competitorsMapper.insertSelective(comp);
    }

    @Override
    public int updateComp(EnfordProductCompetitors comp) {
        return competitorsMapper.updateByPrimaryKeySelective(comp);
    }

    @Override
    public int deleteComp(int id) {
        return competitorsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public EnfordProductCompetitors getCompById(int id) {
        EnfordProductCompetitors comp = competitorsMapper.selectByPrimaryKey(id);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("compId", comp.getId());
        List<EnfordProductDepartment> deptList = deptMapper.selectByParam(param);
        comp.setChildren(deptList);
        return comp;
    }

    @Override
    public List<EnfordProductCompetitors> getComp(Map<String, Object> param) {
        return competitorsMapper.selectByParam(param);
    }

    @Override
    public EnfordProductCompetitors getCompByName(String name) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("name", name);
        List<EnfordProductCompetitors> compList = getComp(param);
        if (compList != null && compList.size() > 0) {
            return compList.get(0);
        } else {
            return null;
        }
    }
}
