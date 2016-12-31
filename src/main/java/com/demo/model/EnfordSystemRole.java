package com.demo.model;

public class EnfordSystemRole {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column enford_system_role.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column enford_system_role.name
     *
     * @mbggenerated
     */
    private String name;

    private String menu_ids;

    private String areaId;

    private String areaName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column enford_system_role.id
     *
     * @return the value of enford_system_role.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column enford_system_role.id
     *
     * @param id the value for enford_system_role.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column enford_system_role.name
     *
     * @return the value of enford_system_role.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column enford_system_role.name
     *
     * @param name the value for enford_system_role.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMenu_ids() {
        return menu_ids;
    }

    public void setMenu_ids(String menu_ids) {
        this.menu_ids = menu_ids;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}