package com.demo.model;

import java.util.List;

public class EnfordProductDepartment {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column enford_product_department.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column enford_product_department.code
     *
     * @mbggenerated
     */
    private String code;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column enford_product_department.name
     *
     * @mbggenerated
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column enford_product_department.city_id
     *
     * @mbggenerated
     */
    private Integer cityId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column enford_product_department.comp_id
     *
     * @mbggenerated
     */
    private Integer compId;

    private String cityName;

    private String compName;

    private Integer areaId;

    private String areaName;

    /**
     * 三期新增字段,累计市调清单数量
     */
    private int resCount;

    private List<EnfordMarketResearch> children;


    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column enford_product_department.id
     *
     * @return the value of enford_product_department.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column enford_product_department.id
     *
     * @param id the value for enford_product_department.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column enford_product_department.code
     *
     * @return the value of enford_product_department.code
     *
     * @mbggenerated
     */
    public String getCode() {
        return code;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column enford_product_department.code
     *
     * @param code the value for enford_product_department.code
     *
     * @mbggenerated
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column enford_product_department.name
     *
     * @return the value of enford_product_department.name
     *
     * @mbggenerated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column enford_product_department.name
     *
     * @param name the value for enford_product_department.name
     *
     * @mbggenerated
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column enford_product_department.city_id
     *
     * @return the value of enford_product_department.city_id
     *
     * @mbggenerated
     */
    public Integer getCityId() {
        return cityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column enford_product_department.city_id
     *
     * @param cityId the value for enford_product_department.city_id
     *
     * @mbggenerated
     */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column enford_product_department.comp_id
     *
     * @return the value of enford_product_department.comp_id
     *
     * @mbggenerated
     */
    public Integer getCompId() {
        return compId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column enford_product_department.comp_id
     *
     * @param compId the value for enford_product_department.comp_id
     *
     * @mbggenerated
     */
    public void setCompId(Integer compId) {
        this.compId = compId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getResCount() {
        return resCount;
    }

    public void setResCount(int resCount) {
        this.resCount = resCount;
    }

    public List<EnfordMarketResearch> getChildren() {
        return children;
    }

    public void setChildren(List<EnfordMarketResearch> children) {
        this.children = children;
    }
}