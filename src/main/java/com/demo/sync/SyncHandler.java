package com.demo.sync;

import com.demo.dao.*;
import com.demo.model.*;
import com.demo.model.EnfordMarketResearch;
import com.demo.model.EnfordMarketResearchDept;
import com.demo.util.Consts;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
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


    //全局变量,判断是否可以同步
    public static boolean CAN_SYNC = true;

    //全局变量,判断是否可以刷新
    public static boolean CAN_REFRESH = true;

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
        try {
            if (CAN_SYNC) {
                CAN_SYNC = false;
                sync2();
                CAN_SYNC = true;
            } else {
                System.out.println("==================同步中,请耐心等候!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            CAN_SYNC = true;
            throw new RuntimeException(ex.getMessage());
        }
    }

    private void sync2() throws SQLException {
        SQLServerHandler sqlServerHandler = new SQLServerHandler();
        MysqlHandler mysqlHandler = new MysqlHandler();
        //开始获取市调清单
        System.out.println("开始获取市调清单");
        List<MarketResearchBill> marketResearchBillList = sqlServerHandler.syncMarketResearchBill2();
        System.out.println("市调清单获取成功");
        //开始同步市调清单
        System.out.println("开始同步市调清单");
        mysqlHandler.syncEnfordMarketResearch2(marketResearchBillList);
        System.out.println("市调清单同步成功");
        //开始同步市调部门
        System.out.println("开始同步市调部门");
        mysqlHandler.syncEnfordMarketResearchDept2(marketResearchBillList);
        System.out.println("市调部门同步成功");
        //开始同步市调商品
        System.out.println("开始同步市调商品");
        sqlServerHandler.syncMrCompetitorPrice(marketResearchBillList, mysqlHandler);
        System.out.println("市调商品同步成功");
    }

    private void sync() throws SQLException, ClassNotFoundException {
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

            MysqlHandler mysqlHandler = new MysqlHandler();
            Connection conn2 = mysqlHandler.connectToMySQL();
            if (conn2 != null) {
                Statement mysqlStatement = conn2.createStatement(
                        ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                try {
                    System.out.println("开始写入市调清单");
                    mysqlHandler.syncEnfordMarketResearch2(mysqlStatement, marketResearchBillList);
                    System.out.println("完成写入市调清单");

                    System.out.println("开始写入市调清单部门");
                    mysqlHandler.syncEnfordMarketResearchDept2(conn2, mysqlStatement, marketResearchBillList);
                    System.out.println("完成写入市调清单部门");

                    //System.out.println("开始同步市调清单商品");
                    //mysqlHandler.syncEnfordMarketResearchGoods2(conn2, mysqlStatement, mrCompetitorPriceList);
                    //System.out.println("完成同步市调清单商品");

                    System.out.println("开始同步市调清单下的商品");
                    //List<MRCompetitorPrice> mrCompetitorPriceList =
                    sqlServerHandler.syncMrCompetitorPrice(sqlServerStatement, marketResearchBillList, conn2, mysqlHandler);
                    System.out.println("市调清单下的商品同步成功");
                    conn2.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    conn2.close();
                }
            }
            conn.close();
        }
    }

    public void refreshData(EnfordMarketResearchMapper researchMapper,
                            EnfordMarketResearchDeptMapper researchDeptMapper,
                            EnfordSystemUserMapper userMapper,
                            EnfordProductPriceMapper priceMapper,
                            EnfordSyncLogMapper syncLogMapper) {
        System.out.println("==================开始刷新市调清单状态");
        /*
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("status", -1);
        int count = researchMapper.countByParam(param);
        System.out.println("==================共:" + count + "条记录");
        //每次刷新10条数据
        int pageSize = 10;
        int loop = 0;
        if (count % pageSize == 0) {
            loop = count / pageSize;
        } else {
            loop = count / pageSize + 1;
        }
        param.clear();
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
        }*/
        try {
            if (CAN_REFRESH) {
                CAN_REFRESH = false;
                refreshData2(researchMapper, researchDeptMapper, userMapper, priceMapper, syncLogMapper);
                CAN_REFRESH = true;
            } else {
                System.out.println("==================刷新中,请耐心等候!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            CAN_REFRESH = true;
            throw new RuntimeException(ex.getMessage());
        }
        System.out.println("==================刷新市调清单状态成功");
    }

    /**
     * 三期,刷新状态功能
     * @param researchMapper
     * @param researchDeptMapper
     * @param userMapper
     * @param syncLogMapper
     */
    private void refreshData2(EnfordMarketResearchMapper researchMapper,
                              EnfordMarketResearchDeptMapper researchDeptMapper,
                              EnfordSystemUserMapper userMapper,
                              EnfordProductPriceMapper priceMapper,
                              EnfordSyncLogMapper syncLogMapper) {
        try {
            //查询需要更新状态的市调清单，对于已经关闭且上传过的单据无需更新
            List<com.demo.model.EnfordMarketResearch> researchList = searchMarketResearch(researchMapper);
            //刷新市调清单列表
            refreshMarketResearchList(researchList, researchMapper, researchDeptMapper, userMapper, priceMapper, syncLogMapper);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void refreshMarketResearchList(List<EnfordMarketResearch> researchList,
                                           EnfordMarketResearchMapper researchMapper,
                                           EnfordMarketResearchDeptMapper researchDeptMapper,
                                           EnfordSystemUserMapper userMapper,
                                           EnfordProductPriceMapper priceMapper,
                                           EnfordSyncLogMapper syncLogMapper) throws ParseException {

        //生成解析器,只比较年月日
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        //获取当前时间
        Date now = new Date();
        //解析当前时间
        now = sdf.parse(sdf.format(now));

        for (com.demo.model.EnfordMarketResearch research : researchList) {
            refreshMarketResearch(research, sdf, now, researchMapper, researchDeptMapper, userMapper, priceMapper, syncLogMapper);
        }
    }

    private void refreshMarketResearch(EnfordMarketResearch research,
                                       SimpleDateFormat sdf,
                                       Date now,
                                       EnfordMarketResearchMapper researchMapper,
                                       EnfordMarketResearchDeptMapper researchDeptMapper,
                                       EnfordSystemUserMapper userMapper,
                                       EnfordProductPriceMapper priceMapper,
                                       EnfordSyncLogMapper syncLogMapper) throws ParseException {
        //设定通用参数
        Map<String, Object> param = new HashMap<String, Object>();
        //判断起始时间
        Date startDt = research.getStartDt();
        Date endDt = research.getEndDt();
        if (startDt == null || endDt == null) return;

        //获取状态
        int state = research.getState();
        //解析市调清单的起止时间
        startDt = sdf.parse(sdf.format(startDt));
        endDt = sdf.parse(sdf.format(endDt));
        long startLong = startDt.getTime();
        long endLong = endDt.getTime();
        //解析当前时间
        long nowLong = now.getTime();
        String nowString = sdf.format(now);
        //如果状态为已发布
        if (state == RESEARCH_STATE_HAVE_PUBLISHED) {
            //市调已经开始,但未结束,则将状态置为已开始
            if (startLong <= nowLong && nowLong <= endLong) {
                research.setState(RESEARCH_STATE_HAVE_STARTED);
                researchMapper.updateByPrimaryKeySelective(research);
            }
            //如果已到达结束时间,则将状态置为已结束
            else if (nowLong > endLong) {
                research.setState(RESEARCH_STATE_HAVE_FINISHED);
                researchMapper.updateByPrimaryKeySelective(research);
            }
        } else if (state == RESEARCH_STATE_HAVE_STARTED) {
            //判断市调是否结束
            if (nowLong > endLong) {
                research.setState(RESEARCH_STATE_HAVE_FINISHED);
                researchMapper.updateByPrimaryKeySelective(research);
            }
        } else if (state == RESEARCH_STATE_HAVE_FINISHED) {
            //获取确认状态
            int confirmType = research.getConfirmType();
            if (confirmType == RESEARCH_CONFIRM_TYPE_SYSTEM) {
                System.out.println("市调[" + research.getBillNumber() + "]数据已经回传");
            } else {
                //记录同步日志
                EnfordSyncLog syncLog = new EnfordSyncLog();
                syncLog.setResId(research.getId());
                syncLog.setBillNumber(research.getBillNumber());
                syncLog.setBeforeState(research.getState());
                syncLog.setBeforeConfirmType(research.getConfirmType());
                syncLog.setSyncDt(new Date());

                //如果市调已经结束,则写入服务器
                System.out.println("市调[" + research.getBillNumber() + "]开始执行回传程序");
                //获取市调部门
                int resId = research.getId();
                param.clear();
                param.put("resId", resId);
                List<com.demo.model.EnfordMarketResearchDept> marketResearchDeptList =
                        researchDeptMapper.selectByParam(param);
                if (marketResearchDeptList != null && marketResearchDeptList.size() > 0) {
                    EnfordMarketResearchDept researchDept = marketResearchDeptList.get(0);
                    syncMarketResearchToSQL(researchDept, param, userMapper, nowString,
                            confirmType, research, priceMapper, researchMapper, syncLog);
                } else {
                    research.setConfirmType(RESEARCH_CONFIRM_TYPE_ERROR);
                    researchMapper.updateByPrimaryKeySelective(research);
                }

                syncLog.setAfterState(RESEARCH_STATE_HAVE_FINISHED);
                syncLogMapper.insert(syncLog);
            }
        } else if (state == RESEARCH_STATE_CANCELED) {
            research.setConfirmType(RESEARCH_CONFIRM_TYPE_ERROR);
            researchMapper.updateByPrimaryKeySelective(research);
        } else {
            research.setState(RESEARCH_STATE_CANCELED);
            research.setConfirmType(RESEARCH_CONFIRM_TYPE_ERROR);
            researchMapper.updateByPrimaryKeySelective(research);
        }
    }

    private void syncMarketResearchToSQL(EnfordMarketResearchDept researchDept,
                                         Map<String, Object> param,
                                         EnfordSystemUserMapper userMapper,
                                         String nowString,
                                         int confirmType,
                                         EnfordMarketResearch research,
                                         EnfordProductPriceMapper priceMapper,
                                         EnfordMarketResearchMapper researchMapper,
                                         EnfordSyncLog syncLog) {
        param.clear();
        param.put("deptId", researchDept.getExeId());
        List<EnfordSystemUser> userList = userMapper.selectByParam(param);
        MarketResearchBill researchBill = new MarketResearchBill();
        if (userList != null && userList.size() > 0) {
            researchBill.setConfirmManCode(userList.get(0).getUsername());
            researchBill.setConfirmManName(userList.get(0).getUsername());
                            /*String name = userList.get(0).getName();
                            if (name == null || name.equals("")) {
                                researchBill.setConfirmManName(userList.get(0).getUsername());
                            } else {
                                researchBill.setConfirmManName(name);
                            }*/
        } else {
            researchBill.setConfirmManCode("888888");
            researchBill.setConfirmManName("管理员");
        }
        researchBill.setConfirmDate(nowString);
        //researchBill.setLastConfirmDate(sdf.format(now));

        //判断是否有价格,有价格返回1,没价格返回4,不考虑从手机确认还是系统确认
        if (checkPriceEmpty(research, priceMapper)) {
            syncLog.setHasPrice(0);
            syncLog.setAfterConfirmType(RESEARCH_CONFIRM_TYPE_EMPTY);
            researchBill.setState(BILL_RESEARCH_EMPTY);
            research.setConfirmType(RESEARCH_CONFIRM_TYPE_EMPTY);
        } else {
            syncLog.setHasPrice(1);
            syncLog.setAfterConfirmType(RESEARCH_CONFIRM_TYPE_SYSTEM);
            researchBill.setState(BILL_RESEARCH_PHONE);
            research.setConfirmType(RESEARCH_CONFIRM_TYPE_SYSTEM);
        }

        researchBill.setBillNumber(research.getBillNumber());
        //System.out.println(research.toString());
        //System.out.println(researchBill.toString());
        //将数据回写到SQLServer服务器
        try {
            if (researchBill.getState() == BILL_RESEARCH_EMPTY ||
                    researchBill.getState() == BILL_RESEARCH_PHONE) {
                SQLServerHandler sqlServerHandler = new SQLServerHandler();
                sqlServerHandler.setResearchConfirmed(researchBill);
                syncLog.setSyncResult(1);
            } else {
                syncLog.setSyncResult(0);
            }
            researchMapper.updateByPrimaryKey(research);
        } catch (Exception ex) {
            research.setConfirmType(RESEARCH_CONFIRM_TYPE_ERROR);
            researchMapper.updateByPrimaryKey(research);
            syncLog.setSyncResult(-1);
            syncLog.setAfterConfirmType(RESEARCH_CONFIRM_TYPE_ERROR);
            ex.printStackTrace();
        }
    }

    /**
     * 查询需要更新的市调清单
     *
     * @param researchMapper
     * @return
     */
    private List<EnfordMarketResearch> searchMarketResearch(EnfordMarketResearchMapper researchMapper) {
        return researchMapper.selectNotClosed();
    }

    /**
     * 三期功能,检查市调清单下的价格是否为空
     *
     * @param research
     * @param priceMapper
     * @return
     * true - 价格全部为空
     * false - 价格不为空
     */
    private boolean checkPriceEmpty(EnfordMarketResearch research, EnfordProductPriceMapper priceMapper) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("resId", research.getId());
        List<EnfordProductPrice> priceList = priceMapper.selectByParam(param);
        /*for (EnfordProductPrice price : priceList) {
            float promptPrice = price.getPromptPrice();
            float retailPrice = price.getRetailPrice();
            if (promptPrice > 0.0f || retailPrice > 0.0f) {
                return false;
            }
        }
        return true;*/
        if (priceList.size() > 0) {
            return false;
        } else {
            return true;
        }
    }

}
