package com.demo.sync;

public class MarketResearchBill {
    public static final String colBillNumber = "BillNumber";
    public static final String colDeptCode = "DeptCode";
    public static final String colNodeName = "NodeName";
    public static final String colMRTypeCode = "MRTypeCode";
    public static final String colMRTypeName = "MRTypeName";
    public static final String colMRUnit = "MRUnit";
    public static final String colState = "State";
    public static final String colConfirmDate = "ConfirmDate";
    public static final String colConfirmManCode = "ConfirmManCode";
    public static final String colConfirmManName = "ConfirmManName";
    public static final String colMRDate = "MRDate";
    public static final String colLastConfirmDate = "LastConfimDate";
    public static final String colMRBeginTime = "MRBeginTime";
    public static final String colMREndTime = "MREndTime";

    String billNumber;
    String deptCode;
    String nodeName;
    String mrTypeCode;
    String mrTypeName;
    int mrUnit;
    int state;
    String confirmDate;
    String confirmManCode;
    String confirmManName;
    String mrDate;
    String lastConfirmDate;
    String mrBeginTime;
    String mrEndTime;

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getMrTypeName() {
        return mrTypeName;
    }

    public void setMrTypeName(String mrTypeName) {
        this.mrTypeName = mrTypeName;
    }

    public int getMrUnit() {
        return mrUnit;
    }

    public void setMrUnit(int mrUnit) {
        this.mrUnit = mrUnit;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getConfirmManCode() {
        return confirmManCode;
    }

    public void setConfirmManCode(String confirmManCode) {
        this.confirmManCode = confirmManCode;
    }

    public String getConfirmManName() {
        return confirmManName;
    }

    public void setConfirmManName(String confirmManName) {
        this.confirmManName = confirmManName;
    }

    public String getMrDate() {
        return mrDate;
    }

    public void setMrDate(String mrDate) {
        this.mrDate = mrDate;
    }

    public String getLastConfirmDate() {
        return lastConfirmDate;
    }

    public void setLastConfirmDate(String lastConfirmDate) {
        this.lastConfirmDate = lastConfirmDate;
    }

    public String getMrBeginTime() {
        return mrBeginTime;
    }

    public void setMrBeginTime(String mrBeginTime) {
        this.mrBeginTime = mrBeginTime;
    }

    public String getMrEndTime() {
        return mrEndTime;
    }

    public void setMrEndTime(String mrEndTime) {
        this.mrEndTime = mrEndTime;
    }

    public String getMrTypeCode() {
        return mrTypeCode;
    }

    public void setMrTypeCode(String mrTypeCode) {
        this.mrTypeCode = mrTypeCode;
    }

    @Override
    public String toString() {
        return "MarketResearchBill{" +
                "billNumber='" + billNumber + '\'' +
                ", deptCode='" + deptCode + '\'' +
                ", nodeName='" + nodeName + '\'' +
                ", mrTypeCode='" + mrTypeCode + '\'' +
                ", mrTypeName='" + mrTypeName + '\'' +
                ", mrUnit=" + mrUnit +
                ", state=" + state +
                ", confirmDate='" + confirmDate + '\'' +
                ", confirmManCode='" + confirmManCode + '\'' +
                ", confirmManName='" + confirmManName + '\'' +
                ", mrDate='" + mrDate + '\'' +
                ", lastConfirmDate='" + lastConfirmDate + '\'' +
                ", mrBeginTime='" + mrBeginTime + '\'' +
                ", mrEndTime='" + mrEndTime + '\'' +
                '}';
    }
}