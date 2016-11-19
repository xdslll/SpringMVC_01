package com.demo.sync;

import com.demo.dao.EnfordMarketResearchMapper;
import com.demo.model.*;
import com.demo.util.Consts;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/7/3
 */
public class SyncHandler implements Consts {

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
            System.out.println("开始同步市调清单");
            List<MarketResearchBill> marketResearchBillList = sqlServerHandler.syncMarketResearchBill(sqlServerStatement);
            System.out.println("市调清单同步成功");

            System.out.println("开始同步市调清单下的商品");
            List<MRCompetitorPrice> mrCompetitorPriceList = sqlServerHandler.syncMrCompetitorPrice(sqlServerStatement);
            System.out.println("市调清单下的商品同步成功");

            conn.close();

            MysqlHandler mysqlHandler = new MysqlHandler();
            Connection conn2 = mysqlHandler.connectToMySQL();
            if (conn2 != null) {
                Statement mysqlStatement = conn2.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                try {
                    System.out.println("开始同步市调清单");
                    mysqlHandler.syncEnfordMarketResearch2(mysqlStatement, marketResearchBillList);
                    System.out.println("完成同步市调清单");

                    System.out.println("开始同步市调清单部门");
                    mysqlHandler.syncEnfordMarketResearchDept2(conn2, mysqlStatement, marketResearchBillList);
                    System.out.println("完成同步市调清单部门");

                    System.out.println("开始同步市调清单商品");
                    mysqlHandler.syncEnfordMarketResearchGoods2(conn2, mysqlStatement, mrCompetitorPriceList);
                    System.out.println("完成同步市调清单商品");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void refreshData(EnfordMarketResearchMapper researchMapper) {
        System.out.println("==================开始刷新市调清单状态");
        int count = researchMapper.countByParam(null);
        System.out.println("==================共:" + count + "条记录");
        //每次刷新10条数据
        int pageSize = 10;
        int loop = 0;
        if (count % pageSize == 0) {
            loop = count / pageSize;
        } else {
            loop = count / pageSize + 1;
        }
        Map<String, Object> param = new HashMap<String, Object>();
        Date now = new Date();
        for (int page = 0; page < loop; page++) {
            param.put("page", page * pageSize);
            param.put("pageSize", pageSize);
            List<com.demo.model.EnfordMarketResearch> researchList = researchMapper.selectByParam(param);
            for (com.demo.model.EnfordMarketResearch research : researchList) {
                int state = research.getState();
                Date startDt = research.getStartDt();
                Date endDt = research.getEndDt();
                if (state == RESEARCH_STATE_HAVE_PUBLISHED) {
                    //市调已经开始,但未结束
                    if (now.compareTo(startDt) >= 0 && now.compareTo(endDt) <= 0) {
                        research.setState(RESEARCH_STATE_HAVE_STARTED);
                        researchMapper.updateByPrimaryKeySelective(research);
                    }
                } else if (state == RESEARCH_STATE_HAVE_STARTED) {
                    //市调已经结束
                    if (now.compareTo(endDt) > 0) {
                        research.setState(RESEARCH_STATE_HAVE_FINISHED);
                        researchMapper.updateByPrimaryKeySelective(research);
                    }
                }
            }
        }

        System.out.println("==================刷新市调清单状态成功");
    }

}
