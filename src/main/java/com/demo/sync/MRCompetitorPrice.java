package com.demo.sync;

public class MRCompetitorPrice {
    public static final String colBillNumber = "BillNumber";
    public static final String colInsideCode = "InsideCode";
    public static final String colGoodsCode = "GoodsCode";
    public static final String colGoodsName = "GoodsName";
    public static final String colBaseBarCode = "BaseBarCode";
    public static final String colGoodsSpec = "GoodsSpec";
    public static final String colBaseMeasureUnit = "BaseMeasureUnit";
    public static final String colCompetitorCode = "CompetitorCode";
    public static final String colCompetitorName = "CompetitorName";
    public static final String colCompetitorRetailPrice = "CompetitorRetailPrice";
    public static final String colCompetitorSpecialOfferPrice = "CompetitorSpecialOfferPrice";
    public static final String colCategoryCode2 = "CategoryCode2";
    public static final String colCategoryName2 = "CategoryName2";
    public static final String colCategoryCode4 = "CategoryCode4";
    public static final String colCategoryName4 = "CategoryName4";

    String billNumber;
    String insideId;
    String goodsCode;
    String goodsName;
    String baseBarCode;
    String goodsSpec;
    String baseMeasureUnit;
    String competitorCode;
    String competitorName;
    String competitorRetailPrice;
    String competitorSpecialOfferPrice;
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

    public String getInsideId() {
        return insideId;
    }

    public void setInsideId(String insideId) {
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

    public String getCompetitorCode() {
        return competitorCode;
    }

    public void setCompetitorCode(String competitorCode) {
        this.competitorCode = competitorCode;
    }

    public String getCompetitorName() {
        return competitorName;
    }

    public void setCompetitorName(String competitorName) {
        this.competitorName = competitorName;
    }

    public String getCompetitorRetailPrice() {
        return competitorRetailPrice;
    }

    public void setCompetitorRetailPrice(String competitorRetailPrice) {
        this.competitorRetailPrice = competitorRetailPrice;
    }

    public String getCompetitorSpecialOfferPrice() {
        return competitorSpecialOfferPrice;
    }

    public void setCompetitorSpecialOfferPrice(String competitorSpecialOfferPrice) {
        this.competitorSpecialOfferPrice = competitorSpecialOfferPrice;
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
        return "MRCompetitorPrice{" +
                "billNumber='" + billNumber + '\'' +
                ", insideId='" + insideId + '\'' +
                ", goodsCode='" + goodsCode + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", baseBarCode='" + baseBarCode + '\'' +
                ", goodsSpec='" + goodsSpec + '\'' +
                ", baseMeasureUnit='" + baseMeasureUnit + '\'' +
                ", competitorCode='" + competitorCode + '\'' +
                ", competitorName='" + competitorName + '\'' +
                ", competitorRetailPrice='" + competitorRetailPrice + '\'' +
                ", competitorSpecialOfferPrice='" + competitorSpecialOfferPrice + '\'' +
                ", categoryCode2='" + categoryCode2 + '\'' +
                ", categoryName2='" + categoryName2 + '\'' +
                ", categoryCode4='" + categoryCode4 + '\'' +
                ", categoryName4='" + categoryName4 + '\'' +
                '}';
    }
}