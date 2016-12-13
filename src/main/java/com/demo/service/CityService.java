package com.demo.service;

import com.demo.model.*;

import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/1/30
 */
public interface CityService {

    List<EnfordProductCity> getCity(Map<String, Object> param);

    int addCity(EnfordProductCity city);

    int updateCity(EnfordProductCity city);

    int deleteCity(int id);

    EnfordProductCity getCityById(int id);

    EnfordProductCity getCityByName(String name);
}
