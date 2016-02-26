package com.demo.service.impl;

import com.demo.dao.EnfordUserMapper;
import com.demo.model.EnfordUser;
import com.demo.service.EnfordUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiads
 * @date 16/2/25
 */
@Service
public class EnfordUserServiceImpl implements EnfordUserService {

    @Resource
    private EnfordUserMapper userMapper;

    @Override
    public EnfordUser login(String username, String password) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("username", username);
        param.put("password", password);
        return userMapper.selectByParam(param);
    }
}
