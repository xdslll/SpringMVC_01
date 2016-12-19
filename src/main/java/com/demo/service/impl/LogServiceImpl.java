package com.demo.service.impl;

import com.demo.dao.EnfordSystemLogMapper;
import com.demo.model.EnfordSystemLog;
import com.demo.service.LogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xiads
 * @date 2016/12/19
 */
@Service("logService")
public class LogServiceImpl implements LogService {

    @Resource
    EnfordSystemLogMapper logMapper;

    @Override
    public int insert(EnfordSystemLog log) {
        return logMapper.insertSelective(log);
    }
}
