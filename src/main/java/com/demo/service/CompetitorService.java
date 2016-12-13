package com.demo.service;

import com.demo.model.EnfordProductArea;
import com.demo.model.EnfordProductCompetitors;

import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/2/5
 */
public interface CompetitorService {

    List<EnfordProductCompetitors> select();

    int addComp(EnfordProductCompetitors comp);

    int updateComp(EnfordProductCompetitors comp);

    int deleteComp(int id);

    EnfordProductCompetitors getCompById(int id);

    List<EnfordProductCompetitors> getComp(Map<String, Object> param);

    EnfordProductCompetitors getCompByName(String name);
}
