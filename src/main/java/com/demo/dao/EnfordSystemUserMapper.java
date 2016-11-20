package com.demo.dao;

import com.demo.model.EnfordSystemUser;

import java.util.List;
import java.util.Map;

public interface EnfordSystemUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated
     */
    int insert(EnfordSystemUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated
     */
    int insertSelective(EnfordSystemUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated
     */
    EnfordSystemUser selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(EnfordSystemUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(EnfordSystemUser record);

    EnfordSystemUser selectByUsernameAndPassword(Map<String, Object> param);

    List<EnfordSystemUser> select();

    List<EnfordSystemUser> selectByParam(Map<String, Object> param);
}