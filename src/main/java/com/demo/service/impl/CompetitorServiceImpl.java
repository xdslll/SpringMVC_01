package com.demo.service.impl;

import com.demo.dao.EnfordProductCategoryMapper;
import com.demo.dao.EnfordProductCityMapper;
import com.demo.dao.EnfordProductCompetitorsMapper;
import com.demo.dao.EnfordSystemUserMapper;
import com.demo.model.EnfordProductCategory;
import com.demo.model.EnfordProductCity;
import com.demo.model.EnfordProductCompetitors;
import com.demo.model.EnfordSystemUser;
import com.demo.service.CategoryService;
import com.demo.service.CompetitorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
}
