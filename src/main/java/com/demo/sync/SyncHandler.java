package com.demo.sync;

import com.demo.dao.EnfordMarketResearchDeptMapper;
import com.demo.dao.EnfordMarketResearchMapper;
import com.demo.dao.EnfordSystemUserMapper;
import com.demo.model.*;
import com.demo.model.EnfordMarketResearchDept;
import com.demo.service.impl.ScheduleServiceImpl;
import com.demo.util.Consts;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
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


    //全局变量,判断是否可以同步
    public static boolean CAN_SYNC = true;

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
                sync();
                CAN_SYNC = true;
            } else {
                System.out.println("==================同步中,请耐心等候!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            CAN_SYNC = true;
        }
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
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            System.out.println("开始同步市调清单下的商品");
            //List<MRCompetitorPrice> mrCompetitorPriceList =
                    sqlServerHandler.syncMrCompetitorPrice(sqlServerStatement, marketResearchBillList);
            System.out.println("市调清单下的商品同步成功");

            conn.close();
        }
    }

    public void refreshData(EnfordMarketResearchMapper researchMapper,
                            EnfordMarketResearchDeptMapper researchDeptMapper,
                            EnfordSystemUserMapper userMapper) {
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
        refreshData2(researchMapper, researchDeptMapper, userMapper);
        System.out.println("==================刷新市调清单状态成功");
    }

    /**
     * 三期,刷新状态功能
     *  @param researchMapper
     * @param researchDeptMapper
     * @param userMapper
     */
    private void refreshData2(EnfordMarketResearchMapper researchMapper,
                              EnfordMarketResearchDeptMapper researchDeptMapper,
                              EnfordSystemUserMapper userMapper) {
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("status", -1);
            List<com.demo.model.EnfordMarketResearch> researchList = researchMapper.selectByParam(param);

            Date now = new Date();
            //只比较年月日
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            now = sdf.parse(sdf.format(now));
            long nowLong = now.getTime();
            System.out.println("当前时间:" + now);

            for (com.demo.model.EnfordMarketResearch research : researchList) {
                int state = research.getState();
                Date startDt = research.getStartDt();
                Date endDt = research.getEndDt();
                if (startDt != null && endDt != null) {
                    startDt = sdf.parse(sdf.format(startDt));
                    endDt = sdf.parse(sdf.format(endDt));
                    long startLong = startDt.getTime();
                    long endLong = endDt.getTime();
                    if (state == RESEARCH_STATE_HAVE_PUBLISHED) {
                        //市调已经开始,但未结束,则将状态置为已开始
                        if (startLong <= nowLong && nowLong <= endLong) {
                            research.setState(RESEARCH_STATE_HAVE_STARTED);
                            researchMapper.updateByPrimaryKeySelective(research);
                        } else if (nowLong > endLong) {
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
                        //如果市调已经结束,则写入服务器
                        System.out.println("开始回写市调信息数据到同步服务器");
                        //获取市调部门
                        int resId = research.getId();
                        param.clear();
                        param.put("resId", resId);
                        List<EnfordMarketResearchDept> marketResearchDeptList =
                                researchDeptMapper.selectByParam(param);
                        if (marketResearchDeptList != null && marketResearchDeptList.size() > 0) {
                            EnfordMarketResearchDept researchDept = marketResearchDeptList.get(0);
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
                            researchBill.setConfirmDate(sdf.format(now));
                            researchBill.setState(BILL_RESEARCH_FINISHED);
                            researchBill.setBillNumber(research.getBillNumber());
                            System.out.println(researchBill.toString());
                            //将数据回写到SQLServer服务器
                            try {
                                SQLServerHandler sqlServerHandler = new SQLServerHandler();
                                sqlServerHandler.setResearchConfirmed(researchBill);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                            //更新确认状态为系统确认
                            research.setConfirmType(RESEARCH_CONFIRM_TYPE_SYSTEM);
                            researchMapper.updateByPrimaryKey(research);
                        } else {
                            research.setConfirmType(RESEARCH_CONFIRM_TYPE_ERROR);
                            researchMapper.updateByPrimaryKeySelective(research);
                        }
                    } else if (state == RESEARCH_STATE_CANCELED){
                        research.setConfirmType(RESEARCH_CONFIRM_TYPE_ERROR);
                        researchMapper.updateByPrimaryKeySelective(research);
                    } else {
                        research.setState(RESEARCH_STATE_CANCELED);
                        research.setConfirmType(RESEARCH_CONFIRM_TYPE_ERROR);
                        researchMapper.updateByPrimaryKeySelective(research);
                    }
                } else {
                    research.setState(RESEARCH_STATE_CANCELED);
                    research.setConfirmType(RESEARCH_CONFIRM_TYPE_ERROR);
                    researchMapper.updateByPrimaryKeySelective(research);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
