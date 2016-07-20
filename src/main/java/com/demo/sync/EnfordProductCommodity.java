package com.demo.sync;

import java.util.Date;

public class EnfordProductCommodity {

    public static final String colId = "id";
    public static final String colCode = "code";
    public static final String colArtNo = "art_no";
    public static final String colPName = "p_name";
    public static final String colPSize = "p_size";
    public static final String colUnit = "unit";
    public static final String colBarCode = "bar_code";
    public static final String colSupplierCode = "supplier_code";
    public static final String colCategoryCode = "category_code";
    public static final String colCreateDt = "create_dt";
    public static final String colCreateBy = "create_by";
    public static final String colUpdateDt = "update_dt";
    public static final String colUpdateBy = "update_by";

    private Integer id;

    private String code;

    private String artNo;

    private String pName;

    private String pSize;

    private String unit;

    private String barCode;

    private String supplierCode;

    private Integer categoryCode;

    private String categoryName;

    private Date createDt;

    private Date updateDt;

    private Integer createBy;

    private String createUsername;

    private Integer updateBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getArtNo() {
        return artNo;
    }

    public void setArtNo(String artNo) {
        this.artNo = artNo;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpSize() {
        return pSize;
    }

    public void setpSize(String pSize) {
        this.pSize = pSize;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public Integer getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(Integer categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public Date getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public String getCreateUsername() {
        return createUsername;
    }

    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }
}