package com.demo.service.impl;

import com.demo.dao.EnfordSystemUserMapper;
import com.demo.model.EnfordProductDepartment;
import com.demo.model.EnfordSystemRole;
import com.demo.model.EnfordSystemUser;
import com.demo.service.CommodityPriceService;
import com.demo.service.DeptService;
import com.demo.service.RoleService;
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

    @Resource
    private RoleService roleService;

    @Resource
    private DeptService deptService;

    @Resource
    private CommodityPriceService priceService;

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
        return userMapper.insertSelective(user);
    }

    @Override
    public int addUser2(EnfordSystemUser user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public int updateUser(EnfordSystemUser user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int deleteUser(int userId) {
        return userMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public EnfordSystemUser getUser(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<EnfordSystemUser> getUserByParam(Map<String, Object> param) {
        List<EnfordSystemUser> userList = userMapper.selectByParam(param);
        for (EnfordSystemUser user : userList) {
            int roleId = user.getRoleId();
            EnfordSystemRole role = roleService.getRole(roleId);
            user.setRole(role);
            if (role != null) {
                user.setRoleName(role.getName());
            } else if (roleId == 0) {
                user.setRoleName("管理员");
            } else {
                user.setRoleName("未知角色");
            }

            int deptId = user.getDeptId();
            EnfordProductDepartment dept = deptService.getDepartmentByDeptId(deptId);
            if (dept != null) {
                user.setDeptName(dept.getName());
            } else {
                user.setDeptName("未分配门店");
            }
            param.clear();
            param.put("uploadBy", user.getId());
            int count = priceService.countPrice(param);
            user.setResCount(count);
        }
        return userList;
    }

    @Override
    public int count() {
        return userMapper.count();
    }
}
