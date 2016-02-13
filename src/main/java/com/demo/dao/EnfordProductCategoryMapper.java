package com.demo.dao;

import com.demo.model.EnfordProductCategory;

import java.util.List;
import java.util.Map;

public interface EnfordProductCategoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enford_product_category
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enford_product_category
     *
     * @mbggenerated
     */
    int insert(EnfordProductCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enford_product_category
     *
     * @mbggenerated
     */
    int insertSelective(EnfordProductCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enford_product_category
     *
     * @mbggenerated
     */
    EnfordProductCategory selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enford_product_category
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(EnfordProductCategory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enford_product_category
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(EnfordProductCategory record);

    int countByCode(int code);

    List<EnfordProductCategory> selectByParam(Map<String, Object> param);

    List<EnfordProductCategory> select();

    int countCodCount(Map<String, Object> param);

    int countHaveFinished(Map<String, Object> param);
}