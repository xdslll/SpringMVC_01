package com.demo.dao;

import com.demo.model.EnfordSystemPlatform;

public interface EnfordSystemPlatformMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enford_system_platform
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enford_system_platform
     *
     * @mbggenerated
     */
    int insert(EnfordSystemPlatform record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enford_system_platform
     *
     * @mbggenerated
     */
    int insertSelective(EnfordSystemPlatform record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enford_system_platform
     *
     * @mbggenerated
     */
    EnfordSystemPlatform selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enford_system_platform
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(EnfordSystemPlatform record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enford_system_platform
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(EnfordSystemPlatform record);
}