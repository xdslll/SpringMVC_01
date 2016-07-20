package com.demo.sync;

import java.util.Date;

public class EnfordMarketResearch {

    public static final String colId = "id";
    public static final String colName = "name";
    public static final String colCreateBy = "create_by";
    public static final String colCreateDt = "create_dt";
    public static final String colStartDt = "start_dt";
    public static final String colEndDt = "end_dt";
    public static final String colExeStoreId = "exe_store_id";
    public static final String colResStoreId = "res_store_id";
    public static final String colImportId = "import_id";
    public static final String colState = "state";
    public static final String colBillNumber = "bill_number";
    public static final String colType = "type";
    public static final String colUnitArea = "unit_area";
    public static final String colFrequency = "frequency";
    public static final String colFrequencyDays = "frequency_days";
    public static final String colMrBeginDate = "mr_begin_date";
    public static final String colMrEndDate = "mr_end_date";
    public static final String colRemark = "remark";
    public static final String colConfirmDate = "confirm_date";
    public static final String colCancelDate = "cancel_date";
    public static final String colCancelRemark = "cancel_remark";

    private Integer id;
    
    private String name;

    private Integer createBy;

    private Date createDt;

    private Date startDt;

    private Date endDt;

    private Integer exeStoreId;

    private Integer resStoreId;

    private Integer importId;

    private String createUsername;

    private String cityName;

    private String exeStoreName;

    private String resStoreName;

    private int state;

    private String stateDesp;

    private String billNumber;

    private String type;

    private int unitArea;

    private int frequency;

    private int frequencyDays;

    private Date mrBeginDate;

    private Date mrEndDate;

    private String remark;

    private Date confirmDate;

    private Date cancelDate;

    private String cancelRemark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public Date getStartDt() {
        return startDt;
    }

    public void setStartDt(Date startDt) {
        this.startDt = startDt;
    }

    public Date getEndDt() {
        return endDt;
    }

    public void setEndDt(Date endDt) {
        this.endDt = endDt;
    }

    public Integer getExeStoreId() {
        return exeStoreId;
    }

    public void setExeStoreId(Integer exeStoreId) {
        this.exeStoreId = exeStoreId;
    }

    public Integer getResStoreId() {
        return resStoreId;
    }

    public void setResStoreId(Integer resStoreId) {
        this.resStoreId = resStoreId;
    }

    public Integer getImportId() {
        return importId;
    }

    public void setImportId(Integer importId) {
        this.importId = importId;
    }

    public String getCreateUsername() {
        return createUsername;
    }

    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getExeStoreName() {
        return exeStoreName;
    }

    public void setExeStoreName(String exeStoreName) {
        this.exeStoreName = exeStoreName;
    }

    public String getResStoreName() {
        return resStoreName;
    }

    public void setResStoreName(String resStoreName) {
        this.resStoreName = resStoreName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateDesp() {
        return stateDesp;
    }

    public void setStateDesp(String stateDesp) {
        this.stateDesp = stateDesp;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUnitArea() {
        return unitArea;
    }

    public void setUnitArea(int unitArea) {
        this.unitArea = unitArea;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getFrequencyDays() {
        return frequencyDays;
    }

    public void setFrequencyDays(int frequencyDays) {
        this.frequencyDays = frequencyDays;
    }

    public Date getMrBeginDate() {
        return mrBeginDate;
    }

    public void setMrBeginDate(Date mrBeginDate) {
        this.mrBeginDate = mrBeginDate;
    }

    public Date getMrEndDate() {
        return mrEndDate;
    }

    public void setMrEndDate(Date mrEndDate) {
        this.mrEndDate = mrEndDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Date getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getCancelRemark() {
        return cancelRemark;
    }

    public void setCancelRemark(String cancelRemark) {
        this.cancelRemark = cancelRemark;
    }
}