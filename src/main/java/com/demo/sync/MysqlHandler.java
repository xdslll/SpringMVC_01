package com.demo.sync;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author xiads
 * @date 16/7/13
 */
public class MysqlHandler {

    /**
     * Mysql IP
     */
    private static final String mysqlServerIP = "127.0.0.1";

    /**
     * Mysql端口号
     */
    private static final int mysqlServerPort = 3306;

    /**
     * Mysql用户名
     */
    private static final String mysqlUserName = "root";

    /**
     * Mysql密码
     */
    //private static final String mysqlUserPwd = "123456";
    private static final String mysqlUserPwd = "rxXbZgK1osEprNBf";

    /**
     * Mysql驱动
     */
    private static final String mysqlDriverName = "com.mysql.jdbc.Driver";

    /**
     * Mysql数据库名
     */
    //private static final String mysqlDbName = "test";
    private static final String mysqlDbName = "sg";

    /**
     * Mysql连接字符串
     */
    private static final String mysqlDbURL = "jdbc:mysql://" + mysqlServerIP + ":"
            + mysqlServerPort + "/" + mysqlDbName + "?useUnicode=true&" +
            "characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";

    public void syncEnfordMarketResearchGoods(Connection conn2, Statement mysqlStatement, List<MRPlanBillGoodsDetail> mrPlanBillGoodsDetailList) throws SQLException {
        for (int i = 0; i < mrPlanBillGoodsDetailList.size(); i++) {
            MRPlanBillGoodsDetail mrPlanBillGoodsDetail = mrPlanBillGoodsDetailList.get(i);
            //根据货物编码查询货物是否存在
            String sqlGoodsDetail = "SELECT * FROM enford_product_commodity where code='"
                    + mrPlanBillGoodsDetail.getGoodsCode() + "'";
            ResultSet rsGoodsDetail = mysqlStatement.executeQuery(sqlGoodsDetail);
            //如果货物存在则获取货物ID,如果货物不存在,则新增货物信息
            int goodsId = -1;
            if (rsGoodsDetail.first()) {
                goodsId = rsGoodsDetail.getInt(EnfordProductCommodity.colId);
            } else {
                String sqlGetGoodsMaxId = "SELECT max(id) AS id FROM enford_product_commodity";
                ResultSet rsGoodsMaxId = mysqlStatement.executeQuery(sqlGetGoodsMaxId);
                if (rsGoodsMaxId.first()) {
                    goodsId = rsGoodsMaxId.getInt(EnfordProductCommodity.colId) + 1;
                    rsGoodsMaxId.close();
                    String sqlGoodsDetail2 = "SELECT * FROM enford_product_commodity";
                    ResultSet rsGoodsDetail2 = mysqlStatement.executeQuery(sqlGoodsDetail2);
                    //开始新增货物信息
                    rsGoodsDetail2.moveToInsertRow();
                    insertGoods(mrPlanBillGoodsDetail, rsGoodsDetail2);
                    try {
                        rsGoodsDetail2.insertRow();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    rsGoodsDetail2.close();
                }
            }
            rsGoodsDetail.close();
            System.out.println("货品ID:" + goodsId);
            if (goodsId != -1) {
                String sqlCheckGoods = "SELECT * FROM enford_market_research_commodity WHERE bill_number="
                        + mrPlanBillGoodsDetail.getBillNumber() + " and cod_id=" + goodsId;
                ResultSet rsCheckGoods = mysqlStatement.executeQuery(sqlCheckGoods);
                if (rsCheckGoods.first()) {
                    System.out.println("存在货物信息,开始更新EnfordMarketResearchCommodity");
                    updateEnfordMarketResearchGoods(conn2, mrPlanBillGoodsDetail, rsCheckGoods, goodsId);
                } else {
                    System.out.println("不存在货物信息,开始插入EnfordMarketResearchCommodity");
                    rsCheckGoods.moveToInsertRow();
                    updateEnfordMarketResearchGoods(conn2, mrPlanBillGoodsDetail, rsCheckGoods, goodsId);
                    try {
                        rsCheckGoods.insertRow();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                rsCheckGoods.close();
            }
        }
    }

    public void insertGoods(MRPlanBillGoodsDetail mrPlanBillGoodsDetail, ResultSet rs) throws SQLException {
        rs.updateString(EnfordProductCommodity.colCode, mrPlanBillGoodsDetail.getGoodsCode());
        rs.updateString(EnfordProductCommodity.colPName, mrPlanBillGoodsDetail.getGoodsName());
        rs.updateString(EnfordProductCommodity.colPSize, mrPlanBillGoodsDetail.getGoodsSpec());
        rs.updateString(EnfordProductCommodity.colUnit, mrPlanBillGoodsDetail.getBaseMeasureUnit());
        rs.updateString(EnfordProductCommodity.colBarCode, mrPlanBillGoodsDetail.getBaseBarCode());
        rs.updateInt(EnfordProductCommodity.colCategoryCode, Integer.valueOf(mrPlanBillGoodsDetail.getCategoryCode4()));

    }

    public void updateEnfordMarketResearchGoods(Connection conn, MRPlanBillGoodsDetail mrPlanBillGoodsDetail, ResultSet rsCheckGoods, int goodsId) throws SQLException {
        String sql2 = "SELECT * FROM enford_market_research WHERE bill_number='" + mrPlanBillGoodsDetail.getBillNumber() + "'";
        Statement s2 = conn.createStatement();
        ResultSet rs2 = s2.executeQuery(sql2);
        if (rs2.first()) {
            rsCheckGoods.updateInt(EnfordMarketResearchCommodity.colResId, rs2.getInt(EnfordMarketResearchCommodity.colId));
        }

        if (!isEmpty(mrPlanBillGoodsDetail.getCategoryCode2()) &&
                !isEmpty(mrPlanBillGoodsDetail.getCategoryName2())) {
            //查询分类是否存在
            String sql3 = "SELECT * FROM enford_product_category WHERE code='" + Integer.valueOf(mrPlanBillGoodsDetail.getCategoryCode2()) + "'";
            System.out.println(sql3);
            Statement s3 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs3 = s3.executeQuery(sql3);
            //插入新的父分类
            if (!rs3.first()) {
                rs3.moveToInsertRow();
                rs3.updateInt("code", Integer.valueOf(mrPlanBillGoodsDetail.getCategoryCode2()));
                rs3.updateString("name", mrPlanBillGoodsDetail.getCategoryName2());
                rs3.updateInt("parent", 0);
                try {
                    rs3.insertRow();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            rs3.close();
        }

        if (!isEmpty(mrPlanBillGoodsDetail.getCategoryCode2()) &&
                !isEmpty(mrPlanBillGoodsDetail.getCategoryName2()) &&
                !isEmpty(mrPlanBillGoodsDetail.getCategoryCode4()) &&
                !isEmpty(mrPlanBillGoodsDetail.getCategoryName4())) {
            //查询子分类是否存在
            String sql4 = "SELECT * FROM enford_product_category WHERE code='"
                    + Integer.valueOf(mrPlanBillGoodsDetail.getCategoryCode4()) + "' and parent='"
                    + Integer.valueOf(mrPlanBillGoodsDetail.getCategoryCode2()) + "'";
            System.out.println(sql4);
            Statement s4 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs4 = s4.executeQuery(sql4);
            //插入新的子分类
            if (!rs4.first()) {
                rs4.moveToInsertRow();
                rs4.updateInt("code", Integer.valueOf(mrPlanBillGoodsDetail.getCategoryCode4()));
                rs4.updateString("name", mrPlanBillGoodsDetail.getCategoryName4());
                rs4.updateInt("parent", Integer.valueOf(mrPlanBillGoodsDetail.getCategoryCode2()));
                try {
                    rs4.insertRow();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            rs4.close();
        }

        rsCheckGoods.updateInt(EnfordMarketResearchCommodity.colCodId, goodsId);
        rsCheckGoods.updateString(EnfordMarketResearchCommodity.colBillNumber, mrPlanBillGoodsDetail.getBillNumber());
        rsCheckGoods.updateInt(EnfordMarketResearchCommodity.colInsideId, mrPlanBillGoodsDetail.getInsideId());
        rsCheckGoods.updateString(EnfordMarketResearchCommodity.colGoodsSpec, mrPlanBillGoodsDetail.getGoodsSpec());
        rsCheckGoods.updateString(EnfordMarketResearchCommodity.colGoodsName, mrPlanBillGoodsDetail.getGoodsName());
        rsCheckGoods.updateString(EnfordMarketResearchCommodity.colBaseBarCode, mrPlanBillGoodsDetail.getBaseBarCode());
        rsCheckGoods.updateString(EnfordMarketResearchCommodity.colBaseMeasureUnit, mrPlanBillGoodsDetail.getBaseMeasureUnit());
        rs2.close();
    }

    public void syncEnfordMarketResearchDept(Connection conn2, Statement mysqlStatement, List<MRPlanBillDeptDetail> mrPlanBillDeptDetailList) throws SQLException {
        for (int i = 0; i < mrPlanBillDeptDetailList.size(); i++) {
            MRPlanBillDeptDetail mrPlanBillDeptDetail = mrPlanBillDeptDetailList.get(i);
            String sql = "SELECT * FROM enford_market_research_dept WHERE bill_number="
                    + mrPlanBillDeptDetail.getBillNumber() + " and dept_code="
                    + mrPlanBillDeptDetail.getDeptCode();
            ResultSet rs = mysqlStatement.executeQuery(sql);
            if (rs.first()) {
                System.out.println("开始更新EnfordMarketResearchDept");
                updateEnfordMarketResearchDept(conn2, mrPlanBillDeptDetail, rs);
                try {
                    rs.updateRow();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                System.out.println("开始插入EnfordMarketResearchDept");
                rs.moveToInsertRow();
                updateEnfordMarketResearchDept(conn2, mrPlanBillDeptDetail, rs);
                try {
                    rs.insertRow();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            rs.close();
        }
    }

    public void updateEnfordMarketResearchDept(Connection conn, MRPlanBillDeptDetail mrPlanBillDeptDetail, ResultSet rs) throws SQLException {
        String sql2 = "SELECT * FROM enford_market_research WHERE bill_number='" + mrPlanBillDeptDetail.getBillNumber() + "'";
        Statement s2 = conn.createStatement();
        ResultSet rs2 = s2.executeQuery(sql2);
        String sql3 = "SELECT * FROM enford_product_department WHERE code='" + mrPlanBillDeptDetail.getDeptCode() + "'";
        Statement s3 = conn.createStatement();
        ResultSet rs3 = s3.executeQuery(sql3);
        if (rs2.first()) {
            rs.updateInt(EnfordMarketResearchDept.colResId, rs2.getInt(EnfordMarketResearch.colId));
        } else {
            System.out.println("未查询到市调清单信息!");
        }
        if (rs3.first()) {
            rs.updateInt(EnfordMarketResearchDept.colExeId, rs3.getInt(EnfordMarketResearch.colId));
            rs.updateInt(EnfordMarketResearchDept.colCompId, rs3.getInt(EnfordMarketResearchDept.colCompId));
        } else {
            System.out.println("未查询到门店信息!");
        }
        rs.updateString(EnfordMarketResearchDept.colBillNumber, mrPlanBillDeptDetail.getBillNumber());
        rs.updateInt(EnfordMarketResearchDept.colInsideId, mrPlanBillDeptDetail.getInsideId());
        rs.updateString(EnfordMarketResearchDept.colDeptCode, mrPlanBillDeptDetail.getDeptCode());
        rs.updateString(EnfordMarketResearchDept.colDeptName, mrPlanBillDeptDetail.getNodeName());
        rs.updateString(EnfordMarketResearchDept.colBillNum, mrPlanBillDeptDetail.getXxBillNum());
        rs.updateInt(EnfordMarketResearchDept.colState, mrPlanBillDeptDetail.getState());

        rs2.close();
        rs3.close();
    }

    public void syncEnfordMarketResearch(Statement mysqlStatement, List<MRPlanBill> mrPlanBillList) throws SQLException {
        for (int i = 0; i < mrPlanBillList.size(); i++) {
            MRPlanBill mrPlanBill = mrPlanBillList.get(i);
            String sql = "SELECT * FROM enford_market_research WHERE bill_number='" + mrPlanBill.getBillNumber() + "'";
            ResultSet rs = mysqlStatement.executeQuery(sql);
            if (rs.first()) {
                System.out.println("开始更新EnfordMarketResearch");
                updateEnfordMarketResearch(mrPlanBill, rs);
                try {
                    rs.updateRow();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                System.out.println("开始插入EnfordMarketResearch");
                rs.moveToInsertRow();
                updateEnfordMarketResearch(mrPlanBill, rs);
                try {
                    rs.insertRow();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            rs.close();
        }
    }

    public void updateEnfordMarketResearch(MRPlanBill mrPlanBill, ResultSet rs) {
        try {
            String createDtStr = mrPlanBill.getBuildDate();
            if (!isEmpty(createDtStr)) {
                Date createDt = new Date(new SimpleDateFormat("yyyyMMdd").parse(createDtStr).getTime());
                rs.updateDate(EnfordMarketResearch.colCreateDt, createDt);
            }
            String beginDateStr = mrPlanBill.getBeginDate();
            if (!isEmpty(beginDateStr)) {
                Date beginDate = new Date(new SimpleDateFormat("yyyyMMdd").parse(beginDateStr).getTime());
                rs.updateDate(EnfordMarketResearch.colStartDt, beginDate);
            }
            String endDateStr = mrPlanBill.getEndDate();
            if (!isEmpty(endDateStr)) {
                Date endDate = new Date(new SimpleDateFormat("yyyyMMdd").parse(endDateStr).getTime());
                rs.updateDate(EnfordMarketResearch.colEndDt, endDate);
            }
            String mrBeginDateStr = mrPlanBill.getMrBeginDate();
            if (!isEmpty(mrBeginDateStr)) {
                Date mrBeginDate = new Date(new SimpleDateFormat("yyyyMMdd").parse(mrBeginDateStr).getTime());
                rs.updateDate(EnfordMarketResearch.colMrBeginDate, mrBeginDate);
            }
            String mrEndDateStr = mrPlanBill.getMrEndDate();
            if (!isEmpty(mrEndDateStr)) {
                Date mrEndDate = new Date(new SimpleDateFormat("yyyyMMdd").parse(mrEndDateStr).getTime());
                rs.updateDate(EnfordMarketResearch.colMrEndDate, mrEndDate);
            }
            String confirmDateStr = mrPlanBill.getConfirmDate();
            if (!isEmpty(confirmDateStr)) {
                Date confirmDate = new Date(new SimpleDateFormat("yyyyMMdd").parse(confirmDateStr).getTime());
                rs.updateDate(EnfordMarketResearch.colConfirmDate, confirmDate);
            }
            String blankFinishDateStr = mrPlanBill.getBlankFinishDate();
            if (!isEmpty(blankFinishDateStr)) {
                Date blankFinishDate = new Date(new SimpleDateFormat("yyyyMMdd").parse(blankFinishDateStr).getTime());
                rs.updateDate(EnfordMarketResearch.colCancelDate, blankFinishDate);
            }
            rs.updateString(EnfordMarketResearch.colBillNumber, mrPlanBill.getBillNumber());
            rs.updateString(EnfordMarketResearch.colType, mrPlanBill.getMrTypeCode());
            rs.updateString(EnfordMarketResearch.colName, mrPlanBill.getMrTypeName());
            rs.updateInt(EnfordMarketResearch.colUnitArea, mrPlanBill.getMrUnitArea());
            rs.updateInt(EnfordMarketResearch.colFrequency, mrPlanBill.getMrFrequency());
            rs.updateInt(EnfordMarketResearch.colFrequencyDays, mrPlanBill.getFrenquencyDays());
            rs.updateString(EnfordMarketResearch.colRemark, mrPlanBill.getExplain());
            //如果状态是已经开始,则直接切换到发布状态
            if (mrPlanBill.getBillState() == 1) {
                mrPlanBill.setBillState(4);
            }
            rs.updateInt(EnfordMarketResearch.colState, mrPlanBill.getBillState());
            rs.updateString(EnfordMarketResearch.colCancelRemark, mrPlanBill.getBlankFinishRemark());
            rs.updateInt(EnfordMarketResearch.colCreateBy, 1);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Connection connectToMySQL() {

        //System.out.println("数据库连接字符串:" + dbURL);
        //System.out.println("用户名:" + userName);
        //System.out.println("密码:" + userPwd);

        try {
            Class.forName(mysqlDriverName);
            Connection conn= DriverManager.getConnection(
                    mysqlDbURL, mysqlUserName, mysqlUserPwd);

            System.out.println("连接MySQL数据库成功");
            return conn;
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.print("连接MySQL数据库失败");
        }
        return null;
    }

    private boolean isEmpty(String s) {
        return s == null || s.equals("");
    }
}
