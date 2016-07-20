package com.demo.sync;

public class MRPlanBillGoodsDetail {
    public static final String colBillNumber = "BillNumber";
    public static final String colInsideId = "InsideId";
    public static final String colGoodsCode = "GoodsCode";
    public static final String colGoodsName = "GoodsName";
    public static final String colBaseBarCode = "BaseBarCode";
    public static final String colGoodsSpec = "GoodsSpec";
    public static final String colBaseMeasureUnit = "BaseMeasureUnit";
    public static final String colCategoryCode2 = "CategoryCode2";
    public static final String colCategoryName2 = "CategoryName2";
    public static final String colCategoryCode4 = "CategoryCode4";
    public static final String colCategoryName4 = "CategoryName4";

    String billNumber;
    int insideId;
    String goodsCode;
    String goodsName;
    String baseBarCode;
    String goodsSpec;
    String baseMeasureUnit;
    String categoryCode2;
    String categoryName2;
    String categoryCode4;
    String categoryName4;

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

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getBaseBarCode() {
        return baseBarCode;
    }

    public void setBaseBarCode(String baseBarCode) {
        this.baseBarCode = baseBarCode;
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public String getBaseMeasureUnit() {
        return baseMeasureUnit;
    }

    public void setBaseMeasureUnit(String baseMeasureUnit) {
        this.baseMeasureUnit = baseMeasureUnit;
    }

    public String getCategoryCode2() {
        return categoryCode2;
    }

    public void setCategoryCode2(String categoryCode2) {
        this.categoryCode2 = categoryCode2;
    }

    public String getCategoryName2() {
        return categoryName2;
    }

    public void setCategoryName2(String categoryName2) {
        this.categoryName2 = categoryName2;
    }

    public String getCategoryCode4() {
        return categoryCode4;
    }

    public void setCategoryCode4(String categoryCode4) {
        this.categoryCode4 = categoryCode4;
    }

    public String getCategoryName4() {
        return categoryName4;
    }

    public void setCategoryName4(String categoryName4) {
        this.categoryName4 = categoryName4;
    }

    @Override
    public String toString() {
        return "MRPlanBillGoodsDetail{" +
                "billNumber='" + billNumber + '\'' +
                ", insideId=" + insideId +
                ", goodsCode='" + goodsCode + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", baseBarCode='" + baseBarCode + '\'' +
                ", goodsSpec='" + goodsSpec + '\'' +
                ", baseMeasureUnit='" + baseMeasureUnit + '\'' +
                ", categoryCode2='" + categoryCode2 + '\'' +
                ", categoryName2='" + categoryName2 + '\'' +
                ", categoryCode4='" + categoryCode4 + '\'' +
                ", categoryName4='" + categoryName4 + '\'' +
                '}';
    }
}