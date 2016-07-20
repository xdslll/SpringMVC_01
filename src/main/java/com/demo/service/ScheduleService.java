package com.demo.service;

/**
 * @author xiads
 * @date 16/2/6
 */
public interface ScheduleService {

    /**
     * 检查市调清单的状态
     */
    void checkMarketResearchState();

    /**
     * 同步市调清单数据
     */
    void syncMarketResearchData();

}
