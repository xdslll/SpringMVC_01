package com.demo.model;

/**
 * @author xiads
 * @date 16/2/12
 */
public class EnfordApiMarketResearch {

    EnfordMarketResearch research;

    EnfordProductDepartment dept;

    EnfordProductCompetitors comp;

    int codCount;

    int haveFinished;

    public EnfordMarketResearch getResearch() {
        return research;
    }

    public void setResearch(EnfordMarketResearch research) {
        this.research = research;
    }

    public EnfordProductDepartment getDept() {
        return dept;
    }

    public void setDept(EnfordProductDepartment dept) {
        this.dept = dept;
    }

    public EnfordProductCompetitors getComp() {
        return comp;
    }

    public void setComp(EnfordProductCompetitors comp) {
        this.comp = comp;
    }

    public int getCodCount() {
        return codCount;
    }

    public void setCodCount(int codCount) {
        this.codCount = codCount;
    }

    public int getHaveFinished() {
        return haveFinished;
    }

    public void setHaveFinished(int haveFinished) {
        this.haveFinished = haveFinished;
    }
}
