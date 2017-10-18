package com.demo.sync;

import com.demo.model.EnfordProductPrice;
import com.demo.util.Consts;

import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiads
 * @date 16/7/13
 */
public class SQLServerHandler implements Consts {

    /**
     * SQLServer IP
     */
    //private static final String sqlServerIP = "139.224.13.204";
    private static final String sqlServerIP = "188.100.253.207";

    /**
     * SQLServer端口号
     */
    private static final int sqlServerPort = 1433;

    /**
     * SQLServer用户名
     */
    private static final String sqlServerUserName="sa";

    /**
     * SQLServer密码
     */
    //private static final String sqlServerPwd="Xds840126";
    private static final String sqlServerPwd = "19550630";

    /**
     * SQLServer数据库名
     */
    private static final String sqlServerDatabaseName = "MarketResearch";

    /**
     * SQLServer JDBC驱动名
     */
    private static final String sqlServerDriverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";

    /**
     * SQLServer连接字符串
     */
    private static final String sqlServerDbURL="jdbc:sqlserver://" + sqlServerIP + ":"
            + sqlServerPort + ";DatabaseName=" + sqlServerDatabaseName;


    public Connection connectToSQLServer() throws ClassNotFoundException, SQLException {
        //System.out.println("数据库连接字符串:" + dbURL);
        //System.out.println("用户名:" + userName);
        //System.out.println("密码:" + userPwd);
        /*try {
            Class.forName(sqlServerDriverName);
            Connection conn= DriverManager.getConnection(
                    sqlServerDbURL, sqlServerUserName, sqlServerPwd);

            System.out.println("连接SQL Server数据库成功");
            return conn;
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.print("连接SQL Server数据库失败");
        }
        return null;*/
        Class.forName(sqlServerDriverName);
        Connection conn= DriverManager.getConnection(
                sqlServerDbURL, sqlServerUserName, sqlServerPwd);

        System.out.println("连接SQL Server数据库成功");
        return conn;
    }

    public List<MRPlanBill> syncMRPlanBill(Statement statement) {
        List<MRPlanBill> mrPlanBillList = new ArrayList<MRPlanBill>();
        try {
            String sql = "SELECT * FROM tbMRPlanBill";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                MRPlanBill mrPlanBill = new MRPlanBill();

                mrPlanBill.setBillNumber(rs.getString(MRPlanBill.colBillNumber));
                mrPlanBill.setBuildDate(rs.getString(MRPlanBill.colBuildDate));
                mrPlanBill.setMrTypeCode(rs.getString(MRPlanBill.colMRTypeCode));
                mrPlanBill.setMrTypeName(rs.getString(MRPlanBill.colMRTypeName));
                mrPlanBill.setMrUnitArea(rs.getInt(MRPlanBill.colMRUnitArea));
                mrPlanBill.setMrFrequency(rs.getInt(MRPlanBill.colMRFrequency));
                mrPlanBill.setFrenquencyDays(rs.getInt(MRPlanBill.colFrequencyDays));
                mrPlanBill.setBeginDate(rs.getString(MRPlanBill.colBeginDate));
                mrPlanBill.setEndDate(rs.getString(MRPlanBill.colEndDate));
                mrPlanBill.setMrBeginDate(rs.getString(MRPlanBill.colMrBeginDate));
                mrPlanBill.setMrEndDate(rs.getString(MRPlanBill.colMrEndDate));
                mrPlanBill.setExplain(rs.getString(MRPlanBill.colExplain));
                mrPlanBill.setBillState(rs.getInt(MRPlanBill.colBillState));
                mrPlanBill.setConfirmDate(rs.getString(MRPlanBill.colConfirmDate));
                mrPlanBill.setBlankFinishDate(rs.getString(MRPlanBill.colBlankFinishDate));
                mrPlanBill.setBlankFinishRemark(rs.getString(MRPlanBill.colBlankFinishRemark));

                System.out.println(mrPlanBill.toString());
                mrPlanBillList.add(mrPlanBill);
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mrPlanBillList;
    }

    public List<MRPlanBillDeptDetail> syncMRPlanBillDeptDetail(Statement statement) {
        List<MRPlanBillDeptDetail> mrPlanBillDeptDetailList = new ArrayList<MRPlanBillDeptDetail>();
        try {
            String sql = "SELECT * FROM tbMRPlanBillDeptDetail";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                MRPlanBillDeptDetail mrPlanBillDeptDetail = new MRPlanBillDeptDetail();

                mrPlanBillDeptDetail.setBillNumber(rs.getString(MRPlanBillDeptDetail.colBillNumber));
                mrPlanBillDeptDetail.setInsideId(rs.getInt(MRPlanBillDeptDetail.colInsideId));
                mrPlanBillDeptDetail.setDeptCode(rs.getString(MRPlanBillDeptDetail.colDeptCode));
                mrPlanBillDeptDetail.setNodeName(rs.getString(MRPlanBillDeptDetail.colNodeName));
                mrPlanBillDeptDetail.setXxBillNum(rs.getString(MRPlanBillDeptDetail.colXxBillNum));
                mrPlanBillDeptDetail.setState(rs.getInt(MRPlanBillDeptDetail.colState));
                mrPlanBillDeptDetail.setEffectiveSign(rs.getInt(MRPlanBillDeptDetail.colEffectiveSign));

                System.out.println(mrPlanBillDeptDetail.toString());
                mrPlanBillDeptDetailList.add(mrPlanBillDeptDetail);
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mrPlanBillDeptDetailList;
    }

    public List<MRPlanBillGoodsDetail> syncMRPlanBillGoodsDetail(Statement statement) {
        List<MRPlanBillGoodsDetail> mrPlanBillGoodsDetailList = new ArrayList<MRPlanBillGoodsDetail>();
        try {
            String sql = "SELECT * FROM tbMRPlanBillGoodsDetail";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                MRPlanBillGoodsDetail mrPlanBillGoodsDetail = new MRPlanBillGoodsDetail();

                mrPlanBillGoodsDetail.setBillNumber(rs.getString(MRPlanBillGoodsDetail.colBillNumber));
                mrPlanBillGoodsDetail.setInsideId(rs.getInt(MRPlanBillGoodsDetail.colInsideId));
                mrPlanBillGoodsDetail.setGoodsCode(rs.getString(MRPlanBillGoodsDetail.colGoodsCode));
                mrPlanBillGoodsDetail.setGoodsName(rs.getString(MRPlanBillGoodsDetail.colGoodsName));
                mrPlanBillGoodsDetail.setBaseBarCode(rs.getString(MRPlanBillGoodsDetail.colBaseBarCode));
                mrPlanBillGoodsDetail.setGoodsSpec(rs.getString(MRPlanBillGoodsDetail.colGoodsSpec));
                mrPlanBillGoodsDetail.setBaseMeasureUnit(rs.getString(MRPlanBillGoodsDetail.colBaseMeasureUnit));
                mrPlanBillGoodsDetail.setCategoryCode2(rs.getString(MRPlanBillGoodsDetail.colCategoryCode2));
                mrPlanBillGoodsDetail.setCategoryName2(rs.getString(MRPlanBillGoodsDetail.colCategoryName2));
                mrPlanBillGoodsDetail.setCategoryCode4(rs.getString(MRPlanBillGoodsDetail.colCategoryCode4));
                mrPlanBillGoodsDetail.setCategoryName4(rs.getString(MRPlanBillGoodsDetail.colCategoryName4));

                System.out.println(mrPlanBillGoodsDetail.toString());
                mrPlanBillGoodsDetailList.add(mrPlanBillGoodsDetail);
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mrPlanBillGoodsDetailList;
    }

    private void setMarketResearchBill(MarketResearchBill marketResearchBill, ResultSet rs) throws SQLException {
        marketResearchBill.setBillNumber(rs.getString(MarketResearchBill.colBillNumber));
        marketResearchBill.setConfirmDate(rs.getString(MarketResearchBill.colConfirmDate));
        marketResearchBill.setConfirmManCode(rs.getString(MarketResearchBill.colConfirmManCode));
        marketResearchBill.setConfirmManName(rs.getString(MarketResearchBill.colConfirmManName));
        marketResearchBill.setDeptCode(rs.getString(MarketResearchBill.colDeptCode));
        marketResearchBill.setMrDate(rs.getString(MarketResearchBill.colMRDate));
        marketResearchBill.setMrTypeCode(rs.getString(MarketResearchBill.colMRTypeCode));
        marketResearchBill.setMrTypeName(rs.getString(MarketResearchBill.colMRTypeName));
        marketResearchBill.setMrUnit(rs.getInt(MarketResearchBill.colMRUnit));
        marketResearchBill.setNodeName(rs.getString(MarketResearchBill.colNodeName));
        marketResearchBill.setState(rs.getInt(MarketResearchBill.colState));
        marketResearchBill.setLastConfirmDate(rs.getString(MarketResearchBill.colLastConfirmDate));
        marketResearchBill.setMrBeginTime(rs.getString(MarketResearchBill.colMRBeginTime));
        marketResearchBill.setMrEndTime(rs.getString(MarketResearchBill.colMREndTime));
        marketResearchBill.setRemark(rs.getString(MarketResearchBill.colRemark));
    }

    /**
     * 三期,根据新的数据结构,同步市调清单和部门信息
     *
     * @param sqlServerStatement
     * @return
     */
    public List<MarketResearchBill> syncMarketResearchBill(Statement sqlServerStatement) {
        List<MarketResearchBill> marketResearchBillList = new ArrayList<MarketResearchBill>();
        try {
            String sql = "SELECT * FROM tbMarketResearchBill";
            ResultSet rs = sqlServerStatement.executeQuery(sql);
            int index = 0;
            while (rs.next()) {
                MarketResearchBill marketResearchBill = new MarketResearchBill();

                marketResearchBill.setBillNumber(rs.getString(MarketResearchBill.colBillNumber));
                marketResearchBill.setConfirmDate(rs.getString(MarketResearchBill.colConfirmDate));
                marketResearchBill.setConfirmManCode(rs.getString(MarketResearchBill.colConfirmManCode));
                marketResearchBill.setConfirmManName(rs.getString(MarketResearchBill.colConfirmManName));
                marketResearchBill.setDeptCode(rs.getString(MarketResearchBill.colDeptCode));
                marketResearchBill.setMrDate(rs.getString(MarketResearchBill.colMRDate));
                marketResearchBill.setMrTypeCode(rs.getString(MarketResearchBill.colMRTypeCode));
                marketResearchBill.setMrTypeName(rs.getString(MarketResearchBill.colMRTypeName));
                marketResearchBill.setMrUnit(rs.getInt(MarketResearchBill.colMRUnit));
                marketResearchBill.setNodeName(rs.getString(MarketResearchBill.colNodeName));
                marketResearchBill.setState(rs.getInt(MarketResearchBill.colState));
                marketResearchBill.setLastConfirmDate(rs.getString(MarketResearchBill.colLastConfirmDate));
                marketResearchBill.setMrBeginTime(rs.getString(MarketResearchBill.colMRBeginTime));
                marketResearchBill.setMrEndTime(rs.getString(MarketResearchBill.colMREndTime));
                marketResearchBill.setRemark(rs.getString(MarketResearchBill.colRemark));

                //System.out.println(marketResearchBill.toString());
                marketResearchBillList.add(marketResearchBill);
                index++;
                System.out.println("完成同步第" + index + "条数据");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return marketResearchBillList;
    }


    /**
     * 三期,根据新的数据结构,同步商品信息
     *
     * @param sqlServerStatement
     * @param marketResearchBillList
     * @param conn2
     * @param mysqlHandler
     * @return
     */
    public void syncMrCompetitorPrice(Statement sqlServerStatement,
                                      List<MarketResearchBill> marketResearchBillList,
                                      Connection conn2,
                                      MysqlHandler mysqlHandler) {
        try {
            //循环读取市调清单,仅需读取状态为0的市调清单
            for (MarketResearchBill marketResearchBill : marketResearchBillList) {
                System.out.println("市调[" + marketResearchBill.getBillNumber() + "]状态为" + marketResearchBill.getState());
                if (marketResearchBill.getState() == RESEARCH_STATE_HAVE_STARTED) {
                    String sql = "SELECT * FROM tbMRCompetitorPrice WHERE BillNumber='" + marketResearchBill.getBillNumber() + "'";
                    ResultSet rs = sqlServerStatement.executeQuery(sql);
                    int index = 0;
                    List<MRCompetitorPrice> mrCompetitorPriceList = new ArrayList<MRCompetitorPrice>();
                    while (rs.next()) {
                        MRCompetitorPrice mrCompetitorPrice = new MRCompetitorPrice();
                        mrCompetitorPrice.setBillNumber(rs.getString(MRCompetitorPrice.colBillNumber));

                        index++;

                        System.out.println("开始同步[" + marketResearchBill.getBillNumber() + "]第" + index + "条商品数据");
                        mrCompetitorPrice.setBaseBarCode(rs.getString(MRCompetitorPrice.colBaseBarCode));
                        mrCompetitorPrice.setBaseMeasureUnit(rs.getString(MRCompetitorPrice.colBaseMeasureUnit));
                        mrCompetitorPrice.setCompetitorCode(rs.getString(MRCompetitorPrice.colCompetitorCode));
                        mrCompetitorPrice.setCompetitorName(rs.getString(MRCompetitorPrice.colCompetitorName));
                        mrCompetitorPrice.setCompetitorRetailPrice(rs.getString(MRCompetitorPrice.colCompetitorRetailPrice));
                        mrCompetitorPrice.setCompetitorSpecialOfferPrice(rs.getString(MRCompetitorPrice.colCompetitorSpecialOfferPrice));
                        mrCompetitorPrice.setGoodsCode(rs.getString(MRCompetitorPrice.colGoodsCode));
                        mrCompetitorPrice.setGoodsName(rs.getString(MRCompetitorPrice.colGoodsName));
                        mrCompetitorPrice.setGoodsSpec(rs.getString(MRCompetitorPrice.colGoodsSpec));
                        mrCompetitorPrice.setInsideId(rs.getString(MRCompetitorPrice.colInsideCode));
                        mrCompetitorPrice.setCategoryCode2(rs.getString(MRCompetitorPrice.colCategoryCode2));
                        mrCompetitorPrice.setCategoryCode4(rs.getString(MRCompetitorPrice.colCategoryCode4));
                        mrCompetitorPrice.setCategoryName2(rs.getString(MRCompetitorPrice.colCategoryName2));
                        mrCompetitorPrice.setCategoryName4(rs.getString(MRCompetitorPrice.colCategoryName4));
                        mrCompetitorPrice.setCompetitorSOPStartDate(rs.getString(MRCompetitorPrice.colCompetitorSOPStartDate));
                        mrCompetitorPrice.setCompetitorSOPEndDate(rs.getString(MRCompetitorPrice.colCompetitorSOPEndDate));

                        //System.out.println(mrCompetitorPrice.toString());
                        mrCompetitorPriceList.add(mrCompetitorPrice);
                        System.out.println("完成同步[" + marketResearchBill.getBillNumber() + "]第" + index + "条商品数据");
                    }
                    rs.close();

                    if (conn2 != null) {
                        Statement mysqlStatement = conn2.createStatement(
                                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        try {
                            System.out.println("开始写入[" + marketResearchBill.getBillNumber() + "]商品数据");
                            mysqlHandler.syncEnfordMarketResearchGoods2(conn2, mysqlStatement, mrCompetitorPriceList);
                            System.out.println("完成写入[" + marketResearchBill.getBillNumber() + "]商品数据");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    System.out.println("市调清单[" + marketResearchBill.getBillNumber() + "]已完成市调,无需更新");
                }
            }
            /*String sql = "SELECT * FROM tbMRCompetitorPrice";
            ResultSet rs = sqlServerStatement.executeQuery(sql);
            int index = 0;
            while (rs.next()) {
                MRCompetitorPrice mrCompetitorPrice = new MRCompetitorPrice();
                mrCompetitorPrice.setBillNumber(rs.getString(MRCompetitorPrice.colBillNumber));

                System.out.println("开始比对BillNumber...");
                boolean ifContinue = true;
                for (int i = 0; i < marketResearchBillList.size(); i++) {
                    MarketResearchBill researchBill = marketResearchBillList.get(i);
                    //System.out.println(researchBill.toString());
                    if (researchBill.getBillNumber().equals(mrCompetitorPrice.getBillNumber())) {
                        if (researchBill.getState() == BILL_RESEARCH_PHONE
                                || researchBill.getState() == BILL_RESEARCH_CONFIRMED) {
                            ifContinue = false;
                            break;
                        }
                    }
                }

                index++;
                if (ifContinue) {
                    System.out.println("开始同步第" + index + "条商品数据");
                    mrCompetitorPrice.setBaseBarCode(rs.getString(MRCompetitorPrice.colBaseBarCode));
                    mrCompetitorPrice.setBaseMeasureUnit(rs.getString(MRCompetitorPrice.colBaseMeasureUnit));
                    mrCompetitorPrice.setCompetitorCode(rs.getString(MRCompetitorPrice.colCompetitorCode));
                    mrCompetitorPrice.setCompetitorName(rs.getString(MRCompetitorPrice.colCompetitorName));
                    mrCompetitorPrice.setCompetitorRetailPrice(rs.getString(MRCompetitorPrice.colCompetitorRetailPrice));
                    mrCompetitorPrice.setCompetitorSpecialOfferPrice(rs.getString(MRCompetitorPrice.colCompetitorSpecialOfferPrice));
                    mrCompetitorPrice.setGoodsCode(rs.getString(MRCompetitorPrice.colGoodsCode));
                    mrCompetitorPrice.setGoodsName(rs.getString(MRCompetitorPrice.colGoodsName));
                    mrCompetitorPrice.setGoodsSpec(rs.getString(MRCompetitorPrice.colGoodsSpec));
                    mrCompetitorPrice.setInsideId(rs.getString(MRCompetitorPrice.colInsideCode));
                    mrCompetitorPrice.setCategoryCode2(rs.getString(MRCompetitorPrice.colCategoryCode2));
                    mrCompetitorPrice.setCategoryCode4(rs.getString(MRCompetitorPrice.colCategoryCode4));
                    mrCompetitorPrice.setCategoryName2(rs.getString(MRCompetitorPrice.colCategoryName2));
                    mrCompetitorPrice.setCategoryName4(rs.getString(MRCompetitorPrice.colCategoryName4));

                    //System.out.println(mrCompetitorPrice.toString());
                    mrCompetitorPriceList.add(mrCompetitorPrice);
                    System.out.println("完成同步第" + index + "条商品数据");
                } else {
                    System.out.println("调研已完成,跳过同步第" + index + "条商品数据");
                }
            }

            rs.close();*/
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //return mrCompetitorPriceList;
    }

    public int addPrice(EnfordProductPrice price) throws SQLException, ClassNotFoundException {
        Connection conn = connectToSQLServer();
        if (conn != null) {
            //插入价格信息
            Statement sqlServerStatement = conn.createStatement();
            /*
            String sql = "SELECT * FROM tbMRCompetitorPrice WHERE BillNumber='"
                    + price.getBillNumber() + "' and GoodsCode='" + price.getCode() + "'";
            System.out.println("sql=" + sql);
            ResultSet rs = sqlServerStatement.executeQuery(sql);
            if (rs.first()) {
                rs.updateFloat(MRCompetitorPrice.colCompetitorRetailPrice, price.getPurchasePrice());
                rs.updateFloat(MRCompetitorPrice.colCompetitorSpecialOfferPrice, price.getPromptPrice());
                rs.updateRow();
            }
            */
            System.out.println("准备更新价格数据");
            //System.out.println(price);
            String updateSql = "UPDATE tbMRCompetitorPrice SET " +
                    MRCompetitorPrice.colCompetitorRetailPrice + "=" + parse(price.getRetailPrice()) + "," +
                    MRCompetitorPrice.colCompetitorSpecialOfferPrice + "=" + parse(price.getPromptPrice()) +
                    " WHERE " + MRCompetitorPrice.colBillNumber + "='" + price.getBillNumber() + "' and " +
                    MRCompetitorPrice.colGoodsCode + "='" + price.getCode() + "'";
            System.out.println("udpateSql=" + updateSql);
            return sqlServerStatement.executeUpdate(updateSql);
        }
        return 0;
    }

    private double parse(Float data) {
        String strData = String.valueOf(data == null ? 0.0 : data);
        BigDecimal bd = new BigDecimal(Double.parseDouble(strData));
        return bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public int setResearchConfirmed(MarketResearchBill researchBill) throws SQLException, ClassNotFoundException {
        String updateSql = "UPDATE tbMarketResearchBill SET " +
                MarketResearchBill.colConfirmManCode + "='" + researchBill.getConfirmManCode() + "'," +
                MarketResearchBill.colConfirmManName + "='" + researchBill.getConfirmManName() + "'," +
                MarketResearchBill.colState + "='" + researchBill.getState() + "'," +
                MarketResearchBill.colConfirmDate + "='" + researchBill.getConfirmDate() + "'" +
                " WHERE " + MarketResearchBill.colBillNumber + "='" + researchBill.getBillNumber() + "'";
        System.out.println("updateSql=" + updateSql);
        Connection conn = connectToSQLServer();
        if (conn != null) {
            //插入价格信息
            Statement sqlServerStatement = conn.createStatement();
            System.out.println("准备更新市调清单数据");

            int ret = sqlServerStatement.executeUpdate(updateSql);
            System.out.println("更新市调清单数据完成");
            return ret;
        } else {
            return 0;
        }
    }

    /**
     * 同步市调清单
     *
     * @return
     */
    public List<MarketResearchBill> syncMarketResearchBill2() {
        List<MarketResearchBill> marketResearchBillList = new ArrayList<MarketResearchBill>();
        Connection conn = null;
        ResultSet rs = null;
        Statement sqlServerStatement = null;
        try {
            conn = connectToSQLServer();
            sqlServerStatement = conn.createStatement();
            String sql = "SELECT * FROM tbMarketResearchBill WHERE State=0";
            rs = sqlServerStatement.executeQuery(sql);
            int index = 0;
            while (rs.next()) {
                MarketResearchBill marketResearchBill = new MarketResearchBill();
                setMarketResearchBill(marketResearchBill, rs);
                marketResearchBillList.add(marketResearchBill);
                index++;
                System.out.println("正在同步第" + index + "条数据,市调单号["+marketResearchBill.getBillNumber() + "]");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (conn != null) {
                    conn.close();
                }
                if (sqlServerStatement != null) {
                    sqlServerStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return marketResearchBillList;
    }

    /**
     * 三期功能,同步市调商品
     *
     * @param marketResearchBillList
     * @param mysqlHandler
     */
    public void syncMrCompetitorPrice(List<MarketResearchBill> marketResearchBillList, MysqlHandler mysqlHandler) {
        Connection conn = null;
        ResultSet rs = null;
        Statement sqlServerStatement = null;
        try {
            conn = connectToSQLServer();
            sqlServerStatement = conn.createStatement();
            //循环读取市调清单,仅需读取状态为0的市调清单
            for (MarketResearchBill marketResearchBill : marketResearchBillList) {
                //System.out.println("市调[" + marketResearchBill.getBillNumber() + "]状态为" + marketResearchBill.getState());
                //if (marketResearchBill.getState() == RESEARCH_STATE_HAVE_STARTED) {
                    String sql = "SELECT * FROM tbMRCompetitorPrice WHERE BillNumber='" + marketResearchBill.getBillNumber() + "'";
                    rs = sqlServerStatement.executeQuery(sql);
                    int index = 0;
                    List<MRCompetitorPrice> mrCompetitorPriceList = new ArrayList<MRCompetitorPrice>();
                    while (rs.next()) {
                        MRCompetitorPrice mrCompetitorPrice = new MRCompetitorPrice();
                        mrCompetitorPrice.setBillNumber(rs.getString(MRCompetitorPrice.colBillNumber));

                        index++;

                        System.out.println("开始读取[" + marketResearchBill.getBillNumber() + "]第" + index + "条商品数据");
                        mrCompetitorPrice.setBaseBarCode(rs.getString(MRCompetitorPrice.colBaseBarCode));
                        mrCompetitorPrice.setBaseMeasureUnit(rs.getString(MRCompetitorPrice.colBaseMeasureUnit));
                        mrCompetitorPrice.setCompetitorCode(rs.getString(MRCompetitorPrice.colCompetitorCode));
                        mrCompetitorPrice.setCompetitorName(rs.getString(MRCompetitorPrice.colCompetitorName));
                        mrCompetitorPrice.setCompetitorRetailPrice(rs.getString(MRCompetitorPrice.colCompetitorRetailPrice));
                        mrCompetitorPrice.setCompetitorSpecialOfferPrice(rs.getString(MRCompetitorPrice.colCompetitorSpecialOfferPrice));
                        mrCompetitorPrice.setGoodsCode(rs.getString(MRCompetitorPrice.colGoodsCode));
                        mrCompetitorPrice.setGoodsName(rs.getString(MRCompetitorPrice.colGoodsName));
                        mrCompetitorPrice.setGoodsSpec(rs.getString(MRCompetitorPrice.colGoodsSpec));
                        mrCompetitorPrice.setInsideId(rs.getString(MRCompetitorPrice.colInsideCode));
                        mrCompetitorPrice.setCategoryCode2(rs.getString(MRCompetitorPrice.colCategoryCode2));
                        mrCompetitorPrice.setCategoryCode4(rs.getString(MRCompetitorPrice.colCategoryCode4));
                        mrCompetitorPrice.setCategoryName2(rs.getString(MRCompetitorPrice.colCategoryName2));
                        mrCompetitorPrice.setCategoryName4(rs.getString(MRCompetitorPrice.colCategoryName4));
                        mrCompetitorPrice.setCompetitorSOPStartDate(rs.getString(MRCompetitorPrice.colCompetitorSOPStartDate));
                        mrCompetitorPrice.setCompetitorSOPEndDate(rs.getString(MRCompetitorPrice.colCompetitorSOPEndDate));

                        //System.out.println(mrCompetitorPrice.toString());
                        mrCompetitorPriceList.add(mrCompetitorPrice);
                        System.out.println("完成读取[" + marketResearchBill.getBillNumber() + "]第" + index + "条商品数据");
                    }

                    System.out.println("开始写入[" + marketResearchBill.getBillNumber() + "]商品数据");
                    mysqlHandler.syncEnfordMarketResearchGoods2(mrCompetitorPriceList);
                    System.out.println("完成写入[" + marketResearchBill.getBillNumber() + "]商品数据");
                //} else {
                //    System.out.println("市调清单[" + marketResearchBill.getBillNumber() + "]已完成市调,无需更新");
                //}
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (conn != null) {
                    conn.close();
                }
                if (sqlServerStatement != null) {
                    sqlServerStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
