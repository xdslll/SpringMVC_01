package com.demo.service.impl;

import com.demo.dao.EnfordMarketResearchDeptMapper;
import com.demo.dao.EnfordMarketResearchMapper;
import com.demo.dao.EnfordProductPriceMapper;
import com.demo.dao.EnfordSystemUserMapper;
import com.demo.model.EnfordMarketResearch;
import com.demo.model.EnfordSystemLog;
import com.demo.service.LogService;
import com.demo.service.ScheduleService;
import com.demo.sync.SyncHandler;
import com.demo.util.Consts;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/2/6
 */
@Component
public class ScheduleServiceImpl implements ScheduleService, Consts {

    @Resource
    EnfordMarketResearchMapper researchMapper;

    @Resource
    EnfordMarketResearchDeptMapper researchDeptMapper;

    @Resource
    EnfordSystemUserMapper userMapper;

    @Resource
    EnfordProductPriceMapper priceMapper;

    @Resource
    LogService logService;

    /**
     * 每30分钟触发一次,同步市调清单数据
     */
    //@Scheduled(cron = "0 0 2,4,6,8,10,12,14,16,18,20,22 * * ?")
    @Scheduled(cron = "0 0/60 * * * ?")
    @Override
    public void syncMarketResearchData() {
        System.out.println("==================开始同步市调清单数据");
        SyncHandler syncHandler = new SyncHandler();
        EnfordSystemLog log = new EnfordSystemLog();
        log.setType(LOG_TYPE_SYNC);
        log.setStart(new Date());
        long start = System.currentTimeMillis();
        try {
            syncHandler.syncData();

            log.setResult(LOG_RESULT_SUCCESS);
            log.setEnd(new Date());
            long end = System.currentTimeMillis();
            long cost = end - start;
            log.setTime(String.valueOf((double)cost / 1000) + "秒");
            log.setRemark("同步百年数据成功");
        } catch (Exception e) {
            e.printStackTrace();

            log.setResult(LOG_RESULT_FAILED);
            log.setEnd(new Date());
            long end = System.currentTimeMillis();
            long cost = end - start;
            log.setTime(String.valueOf((double)cost / 1000) + "秒");
            log.setRemark("同步百年数据失败,失败原因:" + e.getMessage());
        }
        try {
            logService.insert(log);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("==================同步市调清单数据完成");
    }

    /**
     * 每30分钟触发一次,刷新市调清单状态
     */
    @Scheduled(cron = "0 0/60 * * * ?")
    @Override
    public void checkMarketResearchState() {
        SyncHandler syncHandler = new SyncHandler();
        EnfordSystemLog log = new EnfordSystemLog();
        log.setType(LOG_TYPE_STATE);
        log.setStart(new Date());
        long start = System.currentTimeMillis();
        try {
            syncHandler.refreshData(researchMapper, researchDeptMapper, userMapper, priceMapper);
            //写入日志数据
            log.setResult(LOG_RESULT_SUCCESS);
            log.setEnd(new Date());
            long end = System.currentTimeMillis();
            long cost = end - start;
            log.setTime(((double)cost / 1000) + "秒");
            log.setRemark("刷新市调状态数据成功");
        } catch (Exception e) {
            e.printStackTrace();

            log.setResult(LOG_RESULT_FAILED);
            log.setEnd(new Date());
            long end = System.currentTimeMillis();
            long cost = end - start;
            log.setTime(((double)cost / 1000) + "秒");
            log.setRemark("刷新市调状态失败,失败原因:" + e.getMessage());
        }
        logService.insert(log);
    }
}
