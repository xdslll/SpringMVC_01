package com.demo.sync;

public class MarketResearchBill {
    public static final String colBillNumber = "BillNumber";
    public static final String colDeptCode = "DeptCode";
    public static final String colNodeName = "NodeName";
    public static final String colMRTypeName = "MRTypeName";
    public static final String colMRUnit = "MRUnit";
    public static final String colState = "State";
    public static final String colConfirmDate = "ConfirmDate";
    public static final String colConfirmManCode = "ConfirmManCode";
    public static final String colCPModifyManName = "CPModifyManName";
    public static final String colMRDate = "MRDate";
    public static final String colLastConfirmDate = "LastConfirmDate";
    public static final String colMRBeginTime = "MRBeginTime";
    public static final String colMREndTime = "MREndTime";

    String billNumber;
    String deptCode;
    String nodeName;
    String mrTypeName;
    int mrUnit;
    int state;
    String confirmDate;
    String confirmManCode;
    String cpModifyManName;
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

    public String getCpModifyManName() {
        return cpModifyManName;
    }

    public void setCpModifyManName(String cpModifyManName) {
        this.cpModifyManName = cpModifyManName;
    }

    public String getMrDate() {
        return mrDate;
    }

    public void setMrDate(String mrDate) {
        this.mrDate = mrDate;
    }

    @Override
    public String toString() {
        return "MarketResearchBill{" +
                "billNumber='" + billNumber + '\'' +
                ", deptCode='" + deptCode + '\'' +
                ", nodeName='" + nodeName + '\'' +
                ", mrTypeName='" + mrTypeName + '\'' +
                ", mrUnit=" + mrUnit +
                ", state=" + state +
                ", confirmDate='" + confirmDate + '\'' +
                ", confirmManCode='" + confirmManCode + '\'' +
                ", cpModifyManName='" + cpModifyManName + '\'' +
                ", mrDate='" + mrDate + '\'' +
                '}';
    }
}