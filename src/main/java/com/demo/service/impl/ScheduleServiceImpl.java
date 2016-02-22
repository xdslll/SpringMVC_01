package com.demo.service.impl;

import com.demo.dao.EnfordMarketResearchMapper;
import com.demo.model.EnfordMarketResearch;
import com.demo.service.ScheduleService;
import com.demo.util.Consts;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

    /**
     * 每30分钟触发一次,刷新市调清单状态
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    @Override
    public void checkMarketResearchState() {
        System.out.println("==================开始刷新市调清单的状态");
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
            List<EnfordMarketResearch> researchList = researchMapper.selectByParam(param);
            for (EnfordMarketResearch research : researchList) {
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
    }
}
