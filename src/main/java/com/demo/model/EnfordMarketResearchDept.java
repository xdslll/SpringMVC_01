package com.demo.model;

public class EnfordMarketResearchDept extends EnfordMarketResearchDeptKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column enford_market_research_dept.comp_id
     *
     * @mbggenerated
     */
    private Integer compId;

    private String resName;

    private String exeName;

    private String compName;

    private String haveFinished;

    private int effectiveSign;

    private int state;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column enford_market_research_dept.comp_id
     *
     * @return the value of enford_market_research_dept.comp_id
     *
     * @mbggenerated
     */
    public Integer getCompId() {
        return compId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column enford_market_research_dept.comp_id
     *
     * @param compId the value for enford_market_research_dept.comp_id
     *
     * @mbggenerated
     */
    public void setCompId(Integer compId) {
        this.compId = compId;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getExeName() {
        return exeName;
    }

    public void setExeName(String exeName) {
        this.exeName = exeName;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getHaveFinished() {
        return haveFinished;
    }

    public void setHaveFinished(String haveFinished) {
        this.haveFinished = haveFinished;
    }

    public int getEffectiveSign() {
        return effectiveSign;
    }

    public void setEffectiveSign(int effectiveSign) {
        this.effectiveSign = effectiveSign;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "EnfordMarketResearchDept{" +
                "compId=" + compId +
                ", resName='" + resName + '\'' +
                ", exeName='" + exeName + '\'' +
                ", compName='" + compName + '\'' +
                ", haveFinished='" + haveFinished + '\'' +
                ", effectiveSign=" + effectiveSign +
                ", state=" + state +
                '}';
    }
}