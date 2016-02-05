package com.demo.service;

import com.demo.model.EnfordProductCompetitors;
import com.demo.model.EnfordProductSupplier;

import java.util.List;

/**
 * @author xiads
 * @date 16/2/5
 */
public interface CompetitorService {

    List<EnfordProductCompetitors> select();

}
