package com.demo.sync;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author xiads
 * @date 16/7/3
 */
public class SyncHandler {

    private static final Logger logger = Logger.getLogger(SyncHandler.class);

    /**
     * 判断是否处于更新的时间
     * @return
     */
    private boolean isSyncTime(String[] dateArray) {
        return (dateArray[0].equals("12")
                && dateArray[1].equals("00")
                && (dateArray[2].equals("00") ||
                dateArray[2].equals("01") ||
                dateArray[2].equals("02") ||
                dateArray[2].equals("03") ||
                dateArray[2].equals("04"))) ||
                (dateArray[0].equals("00")
                && dateArray[1].equals("00")
                && (dateArray[2].equals("00") ||
                dateArray[2].equals("01") ||
                dateArray[2].equals("02") ||
                dateArray[2].equals("03") ||
                dateArray[2].equals("04")));
    }

    public void syncData() throws SQLException {
        /*try {
            for (;;) {
                Date date = new Date();
                System.out.println(SimpleDateFormat.getDateInstance().format(date));
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                String dateStr = df.format(date);
                System.out.println(dateStr);
                String[] dateArray = dateStr.split(":");
                if (isSyncTime(dateArray)) {
                    System.out.println("到达同步时间,开始同步!");
                    logger.info("到达同步时间,开始同步!");
                }
                Thread.sleep(5000);
            }
        } catch (Exception e){
            e.printStackTrace();
        }*/
        sync();
    }

    private void sync() throws SQLException {
        SQLServerHandler sqlServerHandler = new SQLServerHandler();
        Connection conn = sqlServerHandler.connectToSQLServer();
        if (conn != null) {
            Statement sqlServerStatement = conn.createStatement();
            /*List<MRPlanBill> mrPlanBillList = sqlServerHandler.syncMRPlanBill(sqlServerStatement);
            List<MRPlanBillDeptDetail> mrPlanBillDeptDetailList =
                    sqlServerHandler.syncMRPlanBillDeptDetail(sqlServerStatement);
            List<MRPlanBillGoodsDetail> mrPlanBillGoodsDetailList =
                    sqlServerHandler.syncMRPlanBillGoodsDetail(sqlServerStatement);*/

            //获取同步数据
            List<MarketResearchBill> marketResearchBillList = sqlServerHandler.syncMarketResearchBill(sqlServerStatement);
            List<MRCompetitorPrice> mrCompetitorPriceList = sqlServerHandler.syncMrCompetitorPrice(sqlServerStatement);
            conn.close();

            MysqlHandler mysqlHandler = new MysqlHandler();
            Connection conn2 = mysqlHandler.connectToMySQL();
            if (conn2 != null) {
                Statement mysqlStatement = conn2.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                try {
                    mysqlHandler.syncEnfordMarketResearch2(mysqlStatement, marketResearchBillList);
                    mysqlHandler.syncEnfordMarketResearchDept2(conn2, mysqlStatement, marketResearchBillList);
                    mysqlHandler.syncEnfordMarketResearchGoods2(conn2, mysqlStatement, mrCompetitorPriceList);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
