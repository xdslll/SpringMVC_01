package com.demo.dao;

import com.demo.model.EnfordProductArea;

import java.util.List;
import java.util.Map;

public interface EnfordProductAreaMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enford_product_area
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enford_product_area
     *
     * @mbggenerated
     */
    int insert(EnfordProductArea record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enford_product_area
     *
     * @mbggenerated
     */
    int insertSelective(EnfordProductArea record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enford_product_area
     *
     * @mbggenerated
     */
    EnfordProductArea selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enford_product_area
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(EnfordProductArea record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enford_product_area
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(EnfordProductArea record);

    List<EnfordProductArea> selectByParam(Map<String, Object> param);

    int statsByParam(Map<String, Object> param);

    int statsPriceByParam(Map<String, Object> param);

    int statsTotalByParam(Map<String, Object> param);
}