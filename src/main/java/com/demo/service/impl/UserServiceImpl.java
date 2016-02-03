package com.demo.service.impl;

import com.demo.dao.EnfordSystemUserMapper;
import com.demo.model.EnfordSystemUser;
import com.demo.service.UserService;
import com.demo.util.Tools;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/2/1
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private EnfordSystemUserMapper userMapper;

    @Override
    public EnfordSystemUser login(String username, String password) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("username", username);
        param.put("password", password);
        return userMapper.selectByUsernameAndPassword(param);
    }

    @Override
    public List<EnfordSystemUser> getUsers() {
        return userMapper.select();
    }

    @Override
    public int addUser(EnfordSystemUser user) {
        //对密码进行MD5加密
        user.setPassword(Tools.md5(user.getPassword()));
        return userMapper.insert(user);
    }

    @Override
    public int updateUser(EnfordSystemUser user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int deleteUser(int userId) {
        return userMapper.deleteByPrimaryKey(userId);
    }
}
