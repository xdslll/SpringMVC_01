package com.demo.sync;

public class MRPlanBill {
        public static final String colBillNumber = "BillNumber";
        public static final String colBuildDate = "BuildDate";
        public static final String colMRTypeCode = "MRTypeCode";
        public static final String colMRTypeName = "MRTypeName";
        public static final String colMRUnitArea = "MRUnitArea";
        public static final String colMRFrequency = "MRFrequency";
        public static final String colFrequencyDays = "FrequencyDays";
        public static final String colBeginDate = "BeginDate";
        public static final String colEndDate = "EndDate";
        public static final String colMrBeginDate = "MrBeginDate";
        public static final String colMrEndDate = "MrEndDate";
        public static final String colExplain = "Explain";
        public static final String colBillState = "BillState";
        public static final String colConfirmDate = "ConfirmDate";
        public static final String colBlankFinishDate = "BlankFinishDate";
        public static final String colBlankFinishRemark = "BlankFinishRemark";


        String billNumber;
        String buildDate;
        String mrTypeCode;
        String mrTypeName;
        int mrUnitArea;
        int mrFrequency;
        int frenquencyDays;
        String beginDate;
        String endDate;
        String mrBeginDate;
        String mrEndDate;
        String explain;
        int billState;
        String confirmDate;
        String blankFinishDate;
        String blankFinishRemark;

        public String getBillNumber() {
            return billNumber;
        }

        public void setBillNumber(String billNumber) {
            this.billNumber = billNumber;
        }

        public String getBuildDate() {
            return buildDate;
        }

        public void setBuildDate(String buildDate) {
            this.buildDate = buildDate;
        }

        public String getMrTypeCode() {
            return mrTypeCode;
        }

        public void setMrTypeCode(String mrTypeCode) {
            this.mrTypeCode = mrTypeCode;
        }

        public String getMrTypeName() {
            return mrTypeName;
        }

        public void setMrTypeName(String mrTypeName) {
            this.mrTypeName = mrTypeName;
        }

        public int getMrUnitArea() {
            return mrUnitArea;
        }

        public void setMrUnitArea(int mrUnitArea) {
            this.mrUnitArea = mrUnitArea;
        }

        public int getMrFrequency() {
            return mrFrequency;
        }

        public void setMrFrequency(int mrFrequency) {
            this.mrFrequency = mrFrequency;
        }

        public int getFrenquencyDays() {
            return frenquencyDays;
        }

        public void setFrenquencyDays(int frenquencyDays) {
            this.frenquencyDays = frenquencyDays;
        }

        public String getBeginDate() {
            return beginDate;
        }

        public void setBeginDate(String beginDate) {
            this.beginDate = beginDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getMrBeginDate() {
            return mrBeginDate;
        }

        public void setMrBeginDate(String mrBeginDate) {
            this.mrBeginDate = mrBeginDate;
        }

        public String getMrEndDate() {
            return mrEndDate;
        }

        public void setMrEndDate(String mrEndDate) {
            this.mrEndDate = mrEndDate;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }

        public int getBillState() {
            return billState;
        }

        public void setBillState(int billState) {
            this.billState = billState;
        }

        public String getConfirmDate() {
            return confirmDate;
        }

        public void setConfirmDate(String confirmDate) {
            this.confirmDate = confirmDate;
        }

        public String getBlankFinishDate() {
            return blankFinishDate;
        }

        public void setBlankFinishDate(String blankFinishDate) {
            this.blankFinishDate = blankFinishDate;
        }

        public String getBlankFinishRemark() {
            return blankFinishRemark;
        }

        public void setBlankFinishRemark(String blankFinishRemark) {
            this.blankFinishRemark = blankFinishRemark;
        }

        @Override
        public String toString() {
            return "MRPlanBill{" +
                    "billNumber='" + billNumber + '\'' +
                    ", buildDate='" + buildDate + '\'' +
                    ", mrTypeCode='" + mrTypeCode + '\'' +
                    ", mrTypeName='" + mrTypeName + '\'' +
                    ", mrUnitArea=" + mrUnitArea +
                    ", mrFrequency=" + mrFrequency +
                    ", frenquencyDays=" + frenquencyDays +
                    ", beginDate='" + beginDate + '\'' +
                    ", endDate='" + endDate + '\'' +
                    ", mrBeginDate='" + mrBeginDate + '\'' +
                    ", mrEndDate='" + mrEndDate + '\'' +
                    ", explain='" + explain + '\'' +
                    ", billState='" + billState + '\'' +
                    ", confirmDate='" + confirmDate + '\'' +
                    ", blankFinishDate='" + blankFinishDate + '\'' +
                    ", blankFinishRemark='" + blankFinishRemark + '\'' +
                    '}';
        }
    }