package com.demo.sync;

public class MRPlanBillDeptDetail {
        public static final String colBillNumber = "BillNumber";
        public static final String colInsideId = "InsideId";
        public static final String colDeptCode = "DeptCode";
        public static final String colNodeName = "NodeName";

        String billNumber;
        int insideId;
        String deptCode;
        String nodeName;

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

        @Override
        public String toString() {
            return "MRPlanBillDeptDetail{" +
                    "billNumber='" + billNumber + '\'' +
                    ", insideId=" + insideId +
                    ", deptCode='" + deptCode + '\'' +
                    ", nodeName='" + nodeName + '\'' +
                    '}';
        }
    }