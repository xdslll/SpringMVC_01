package com.demo.service.impl;

import com.demo.dao.EnfordMarketResearchDeptMapper;
import com.demo.dao.EnfordMarketResearchMapper;
import com.demo.dao.EnfordProductPriceMapper;
import com.demo.dao.EnfordSystemUserMapper;
import com.demo.model.EnfordMarketResearch;
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

    /**
     * 每30分钟触发一次,同步市调清单数据
     */
    //@Scheduled(cron = "0 0 2,4,6,8,10,12,14,16,18,20,22 * * ?")
    @Scheduled(cron = "0 0/30 * * * ?")
    @Override
    public void syncMarketResearchData() {
        System.out.println("==================开始同步市调清单数据");
        SyncHandler syncHandler = new SyncHandler();
        try {
            syncHandler.syncData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("==================同步市调清单数据完成");
    }

    /**
     * 每30分钟触发一次,刷新市调清单状态
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    @Override
    public void checkMarketResearchState() {
        SyncHandler syncHandler = new SyncHandler();
        try {
            syncHandler.refreshData(researchMapper, researchDeptMapper, userMapper, priceMapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
