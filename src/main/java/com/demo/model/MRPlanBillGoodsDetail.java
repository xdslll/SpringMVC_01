package com.demo.model;

public class MRPlanBillGoodsDetail {
        public static final String colBillNumber = "BillNumber";
        public static final String colInsideId = "InsideId";
        public static final String colGoodsCode = "GoodsCode";
        public static final String colGoodsName = "GoodsName";
        public static final String colBaseBarCode = "BaseBarCode";
        public static final String colGoodsSpec = "GoodsSpec";
        public static final String colBaseMeasureUnit = "BaseMeasureUnit";

        String billNumber;
        int insideId;
        String goodsCode;
        String goodsName;
        String baseBarCode;
        String goodsSpec;
        String baseMeasureUnit;

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
                    '}';
        }
    }