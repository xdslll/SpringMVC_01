package com.demo.sync;

public class MRPlanBillDeptDetail {
        public static final String colBillNumber = "BillNumber";
        public static final String colInsideId = "InsideId";
        public static final String colDeptCode = "DeptCode";
        public static final String colNodeName = "NodeName";
        public static final String colXxBillNum = "XxBillNum";
        public static final String colState = "State";
        public static final String colEffectiveSign = "EffectiveSign";

        String billNumber;
        int insideId;
        String deptCode;
        String nodeName;
        String xxBillNum;
        int state;
        int effectiveSign;

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

        public int getEffectiveSign() {
            return effectiveSign;
        }

        public void setEffectiveSign(int effectiveSign) {
            this.effectiveSign = effectiveSign;
        }

    @Override
    public String toString() {
        return "MRPlanBillDeptDetail{" +
                "billNumber='" + billNumber + '\'' +
                ", insideId=" + insideId +
                ", deptCode='" + deptCode + '\'' +
                ", nodeName='" + nodeName + '\'' +
                ", xxBillNum='" + xxBillNum + '\'' +
                ", state=" + state +
                ", effectiveSign=" + effectiveSign +
                '}';
    }

    public String getXxBillNum() {
            return xxBillNum;
        }

        public void setXxBillNum(String xxBillNum) {
            this.xxBillNum = xxBillNum;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }


}