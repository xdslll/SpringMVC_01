package com.demo.sync;

public class EnfordMarketResearchDept {

    public static final String colResId = "res_id";
    public static final String colExeId = "exe_id";
    public static final String colCompId = "comp_id";
    public static final String colBillNumber = "bill_number";
    public static final String colInsideId = "inside_id";
    public static final String colDeptCode = "dept_code";
    public static final String colDeptName = "dept_name";
    public static final String colBillNum = "bill_num";
    public static final String colState = "state";
    public static final String colEffectiveSign = "effective_sign";

    private Integer resId;

    private Integer exeId;

    private Integer compId;

    private String billNumber;

    private int insideId;

    private int deptCode;

    private int deptName;

    private String billNum;

    private int state;

    private int effectiveSign;

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public Integer getExeId() {
        return exeId;
    }

    public void setExeId(Integer exeId) {
        this.exeId = exeId;
    }

    public Integer getCompId() {
        return compId;
    }

    public void setCompId(Integer compId) {
        this.compId = compId;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public int getInsideId() {
        return insideId;
    }

    public void setInsideId(int insideId) {
        this.insideId = insideId;
    }

    public int getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(int deptCode) {
        this.deptCode = deptCode;
    }

    public int getDeptName() {
        return deptName;
    }

    public void setDeptName(int deptName) {
        this.deptName = deptName;
    }

    public String getBillNum() {
        return billNum;
    }

    public void setBillNum(String billNum) {
        this.billNum = billNum;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getEffectiveSign() {
        return effectiveSign;
    }

    public void setEffectiveSign(int effectiveSign) {
        this.effectiveSign = effectiveSign;
    }
}