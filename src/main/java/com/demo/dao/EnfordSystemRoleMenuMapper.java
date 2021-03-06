package com.demo.dao;

import com.demo.model.EnfordSystemRoleMenu;

import java.util.List;

public interface EnfordSystemRoleMenuMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enford_system_role_menu
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enford_system_role_menu
     *
     * @mbggenerated
     */
    int insert(EnfordSystemRoleMenu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enford_system_role_menu
     *
     * @mbggenerated
     */
    int insertSelective(EnfordSystemRoleMenu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enford_system_role_menu
     *
     * @mbggenerated
     */
    List<EnfordSystemRoleMenu> selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enford_system_role_menu
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(EnfordSystemRoleMenu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table enford_system_role_menu
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(EnfordSystemRoleMenu record);

    List<EnfordSystemRoleMenu> selectByRoleId(int roleId);

    int deleteByRoleId(int roleId);
}