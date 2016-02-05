package com.demo.service.impl;

import com.demo.dao.EnfordSystemUserMapper;
import com.demo.model.EnfordSystemUser;
import com.demo.service.UserService;
import com.demo.util.StringUtil;
import com.demo.util.EncryptUtil;
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
        user.setPassword(EncryptUtil.md5(user.getPassword()));
        //如果用户名称为空,则默认填入登录名
        if (StringUtil.isEmpty(user.getName())) {
            user.setName(user.getUsername());
        }
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
