package com.demo.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class EnfordProductPrice {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column enford_product_price.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column enford_product_price.purchase_price
     *
     * @mbggenerated
     */
    private Float purchasePrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column enford_product_price.retail_price
     *
     * @mbggenerated
     */
    private Float retailPrice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column enford_product_price.com_id
     *
     * @mbggenerated
     */
    private Integer comId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column enford_product_price.miss
     *
     * @mbggenerated
     */
    private Integer miss;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column enford_product_price.res_id
     *
     * @mbggenerated
     */
    private Integer resId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column enford_product_price.comp_id
     *
     * @mbggenerated
     */
    private Integer compId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column enford_product_price.upload_by
     *
     * @mbggenerated
     */
    private Integer uploadBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column enford_product_price.upload_dt
     *
     * @mbggenerated
     */
    @JSONField(format = "yyyy-MM-dd")
    private Date uploadDt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column enford_product_price.dept_id
     *
     * @mbggenerated
     */
    private Integer deptId;

    private String resName;

    private String comName;

    private String compName;

    private String missDesp;

    private String uploadName;

    private Float promptPrice;

    private String remark;

    private String code;

    private String billNumber;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column enford_product_price.id
     *
     * @return the value of enford_product_price.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column enford_product_price.id
     *
     * @param id the value for enford_product_price.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column enford_product_price.purchase_price
     *
     * @return the value of enford_product_price.purchase_price
     *
     * @mbggenerated
     */
    public Float getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column enford_product_price.purchase_price
     *
     * @param purchasePrice the value for enford_product_price.purchase_price
     *
     * @mbggenerated
     */
    public void setPurchasePrice(Float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column enford_product_price.retail_price
     *
     * @return the value of enford_product_price.retail_price
     *
     * @mbggenerated
     */
    public Float getRetailPrice() {
        return retailPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column enford_product_price.retail_price
     *
     * @param retailPrice the value for enford_product_price.retail_price
     *
     * @mbggenerated
     */
    public void setRetailPrice(Float retailPrice) {
        this.retailPrice = retailPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column enford_product_price.com_id
     *
     * @return the value of enford_product_price.com_id
     *
     * @mbggenerated
     */
    public Integer getComId() {
        return comId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column enford_product_price.com_id
     *
     * @param comId the value for enford_product_price.com_id
     *
     * @mbggenerated
     */
    public void setComId(Integer comId) {
        this.comId = comId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column enford_product_price.miss
     *
     * @return the value of enford_product_price.miss
     *
     * @mbggenerated
     */
    public Integer getMiss() {
        return miss;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column enford_product_price.miss
     *
     * @param miss the value for enford_product_price.miss
     *
     * @mbggenerated
     */
    public void setMiss(Integer miss) {
        this.miss = miss;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column enford_product_price.res_id
     *
     * @return the value of enford_product_price.res_id
     *
     * @mbggenerated
     */
    public Integer getResId() {
        return resId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column enford_product_price.res_id
     *
     * @param resId the value for enford_product_price.res_id
     *
     * @mbggenerated
     */
    public void setResId(Integer resId) {
        this.resId = resId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column enford_product_price.comp_id
     *
     * @return the value of enford_product_price.comp_id
     *
     * @mbggenerated
     */
    public Integer getCompId() {
        return compId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column enford_product_price.comp_id
     *
     * @param compId the value for enford_product_price.comp_id
     *
     * @mbggenerated
     */
    public void setCompId(Integer compId) {
        this.compId = compId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column enford_product_price.upload_by
     *
     * @return the value of enford_product_price.upload_by
     *
     * @mbggenerated
     */
    public Integer getUploadBy() {
        return uploadBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column enford_product_price.upload_by
     *
     * @param uploadBy the value for enford_product_price.upload_by
     *
     * @mbggenerated
     */
    public void setUploadBy(Integer uploadBy) {
        this.uploadBy = uploadBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column enford_product_price.upload_dt
     *
     * @return the value of enford_product_price.upload_dt
     *
     * @mbggenerated
     */
    public Date getUploadDt() {
        return uploadDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column enford_product_price.upload_dt
     *
     * @param uploadDt the value for enford_product_price.upload_dt
     *
     * @mbggenerated
     */
    public void setUploadDt(Date uploadDt) {
        this.uploadDt = uploadDt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column enford_product_price.dept_id
     *
     * @return the value of enford_product_price.dept_id
     *
     * @mbggenerated
     */
    public Integer getDeptId() {
        return deptId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column enford_product_price.dept_id
     *
     * @param deptId the value for enford_product_price.dept_id
     *
     * @mbggenerated
     */
    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getMissDesp() {
        return missDesp;
    }

    public void setMissDesp(String missDesp) {
        this.missDesp = missDesp;
    }

    public String getUploadName() {
        return uploadName;
    }

    public void setUploadName(String uploadName) {
        this.uploadName = uploadName;
    }

    public Float getPromptPrice() {
        return promptPrice;
    }

    public void setPromptPrice(Float promptPrice) {
        this.promptPrice = promptPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    @Override
    public String toString() {
        return "EnfordProductPrice{" +
                "id=" + id +
                ", purchasePrice=" + purchasePrice +
                ", retailPrice=" + retailPrice +
                ", comId=" + comId +
                ", miss=" + miss +
                ", resId=" + resId +
                ", compId=" + compId +
                ", uploadBy=" + uploadBy +
                ", uploadDt=" + uploadDt +
                ", deptId=" + deptId +
                ", resName='" + resName + '\'' +
                ", comName='" + comName + '\'' +
                ", compName='" + compName + '\'' +
                ", missDesp='" + missDesp + '\'' +
                ", uploadName='" + uploadName + '\'' +
                ", promptPrice=" + promptPrice +
                ", remark='" + remark + '\'' +
                ", code='" + code + '\'' +
                ", billNumber='" + billNumber + '\'' +
                '}';
    }
}