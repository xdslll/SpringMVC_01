package com.demo.service.impl;

import com.demo.dao.*;
import com.demo.model.*;
import com.demo.service.DeptService;
import com.demo.service.EnfordUserService;
import com.demo.service.UserService;
import com.demo.util.EncryptUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/2/5
 */
@Service("deptService")
public class DeptServiceImpl implements DeptService {

    @Resource
    EnfordProductDepartmentMapper deptMapper;

    @Resource
    EnfordProductCityMapper cityMapper;

    @Resource
    EnfordProductCompetitorsMapper competitorsMapper;

    @Resource
    EnfordProductAreaMapper areaMapper;

    @Resource
    EnfordMarketResearchDeptMapper researchDeptMapper;

    @Resource
    UserService userService;

    @Override
    public List<EnfordProductDepartment> selectAll() {
        List<EnfordProductDepartment> depts = deptMapper.selectAll();
        for (int i = 0; i < depts.size(); i++) {
            EnfordProductDepartment dept = depts.get(i);
            int cityId = dept.getCityId();
            int compId = dept.getCompId();
            EnfordProductCity city = cityMapper.selectByPrimaryKey(cityId);
            EnfordProductCompetitors comp = competitorsMapper.selectByPrimaryKey(compId);
            if (city != null) {
                dept.setCityName(city.getName());
            }
            if (comp != null) {
                dept.setCompName(comp.getName());
            }
        }
        return depts;
    }

    @Override
    public List<EnfordProductDepartment> get(Map<String, Object> param) {
        List<EnfordProductDepartment> depts = deptMapper.selectByParam(param);
        for (int i = 0; i < depts.size(); i++) {
            EnfordProductDepartment dept = depts.get(i);
            int cityId = dept.getCityId();
            int compId = dept.getCompId();
            int areaId = dept.getAreaId();
            EnfordProductCity city = cityMapper.selectByPrimaryKey(cityId);
            EnfordProductCompetitors comp = competitorsMapper.selectByPrimaryKey(compId);
            EnfordProductArea area = areaMapper.selectByPrimaryKey(areaId);
            if (city != null) {
                dept.setCityName(city.getName());
            }
            if (comp != null) {
                dept.setCompName(comp.getName());
            }
            if (area != null) {
                dept.setAreaName(area.getName());
            }
        }
        return depts;
    }

    @Override
    public int addDept(EnfordProductDepartment dept) {
        return deptMapper.insert(dept);
    }

    @Override
    public int updateDept(EnfordProductDepartment dept) {
        return deptMapper.updateByPrimaryKeySelective(dept);
    }

    @Override
    public int deleteDept(int id) {
        return deptMapper.deleteByPrimaryKey(id);
    }

    @Override
    public EnfordProductDepartment getDepartmentByDeptId(int deptId) {
        return deptMapper.selectByPrimaryKey(deptId);
    }

    @Override
    public EnfordProductDepartment getDepartmentByDeptCode(String deptCode) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("code", deptCode);
        return deptMapper.selectByParam(param).get(0);
    }

    @Override
    public int count(Map<String, Object> param) {
        return deptMapper.countByParam(param);
    }

    @Override
    public List<EnfordProductDepartment> getNotInArea(int areaId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("areaId", areaId);
        List<EnfordProductDepartment> deptList = deptMapper.selectNotInArea(param);
        for (EnfordProductDepartment dept : deptList) {
            int _areaId = dept.getAreaId();
            if (_areaId == -1) {
                dept.setAreaName("未分配区域");
            } else {
                EnfordProductArea area = areaMapper.selectByPrimaryKey(_areaId);
                dept.setAreaName(area.getName());
            }
            param.clear();
            param.put("exeId", dept.getId());
            dept.setResCount(researchDeptMapper.countByParam(param));
        }
        return deptList;
    }

    @Override
    public int generate(int deptId, int num, int roleId) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("deptId", deptId);
        List<EnfordSystemUser> userList = userService.getUserByParam(param);
        EnfordProductDepartment dept = getDepartmentByDeptId(deptId);
        int maxCode = 0;
        int ret = 0;
        String deptCode = dept.getCode();
        if (userList != null && userList.size() > 0) {
            for (EnfordSystemUser user : userList) {
                String userName = user.getUsername();
                if (userName.length() == 6) {
                    String index = userName.substring(4, userName.length());
                    int _maxCode = Integer.valueOf(index);
                    if (_maxCode > maxCode) {
                        maxCode = _maxCode;
                    }
                }
            }
        }
        maxCode++;
        if (num >= 100 || maxCode >= 100) {
            return ret;
        } else {
            for (int i = 0; i < num; i++) {
                String newUsername = deptCode + (maxCode >= 10 ? String.valueOf(maxCode) : "0" + maxCode);
                EnfordSystemUser newUser = new EnfordSystemUser();
                newUser.setUsername(newUsername);
                newUser.setPassword(EncryptUtil.md5(newUsername));
                newUser.setRoleId(roleId);
                newUser.setDeptId(deptId);
                newUser.setName(newUsername);
                newUser.setType(0);
                newUser.setOrgId(0);
                ret += userService.addUser2(newUser);
                maxCode++;
            }
        }
        return ret;
    }
}
