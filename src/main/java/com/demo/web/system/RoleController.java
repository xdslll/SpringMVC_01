package com.demo.web.system;

import com.alibaba.fastjson.JSONObject;
import com.demo.model.EnfordSystemMenu;
import com.demo.model.EnfordSystemRole;
import com.demo.model.EnfordSystemUser;
import com.demo.model.RespBody;
import com.demo.service.RoleService;
import com.demo.service.UserService;
import com.demo.util.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/2/2
 */
@Controller
public class RoleController {

    private static final Logger logger = Logger.getLogger(RoleController.class);

    @Resource
    private RoleService roleService;

    @Resource
    private UserService userService;

    @RequestMapping("/role/manage")
    public String manage() {
        return "/user/role";
    }

    @RequestMapping("/role/get")
    public void getRoles(HttpServletRequest req, HttpServletResponse resp) {
        List<EnfordSystemRole> roles = null;
        try {
            roles = roleService.getRoles();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(roles));
    }

    @RequestMapping("/role/get2")
    public void getRoles2(HttpServletRequest req, HttpServletResponse resp) {
        List<EnfordSystemRole> roles = null;
        JSONObject result = new JSONObject();
        try {
            roles = roleService.getRoles();
            result.put("rows", roles);
            result.put("total", roles.size());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(result));
    }

    @RequestMapping("/role/add")
    public void addRole(HttpServletRequest req, HttpServletResponse resp, EnfordSystemRole role) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            roleService.addRole(role);
            //获取角色id
            int roleId = roleService.maxRoleId();
            //将角色id和菜单id关联
            roleService.addRoleMenu(roleId, getMenuIds(role));
            respBody.setCode(Consts.SUCCESS);
            respBody.setMsg("添加角色成功！");
        } catch (Exception ex) {
            logger.error("exception occured when addRole:" + ex);
            respBody.setCode(Consts.FAILED);
            respBody.setMsg("添加角色出错:" + ex.getMessage());
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    @RequestMapping("/role/update")
    public void updateRole(HttpServletRequest req, HttpServletResponse resp, EnfordSystemRole role) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            //更新权限信息
            roleService.updateRole(role);
            int roleId = role.getId();
            //删除该角色对应的所有权限
            roleService.deleteRoleMenuByRoldId(roleId);
            //更新权限关联的菜单信息
            int[] menuIds = getMenuIds(role);
            //重新添加权限
            int ret = roleService.updateRoleMenu(roleId, menuIds);
            respBody.setCode(Consts.SUCCESS);
            respBody.setMsg("编辑角色成功！");
        }
        catch (Exception ex) {
            logger.error("exception occured when updateRole:" + ex);
            respBody.setCode(Consts.FAILED);
            respBody.setMsg("编辑角色出错:" + ex.getMessage());
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    @RequestMapping("/role/delete")
    public void deleteRole(HttpServletRequest req, HttpServletResponse resp, int roleId) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("roleId", roleId);
            List<EnfordSystemUser> userList = userService.getUserByParam(param);
            if (userList != null && userList.size() > 0) {
                ResponseUtil.sendFailed("该角色下已有" + userList.size() + "个用户,无法删除!", respBody);
            } else {
                int ret = roleService.deleteRole(roleId);
                ResponseUtil.checkResult(ret, "删除角色", respBody);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            respBody.setCode(Consts.FAILED);
            respBody.setMsg("删除角色出错:" + ex.getMessage());
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    @RequestMapping("/role_menu/get")
    public void getRoleMenu(HttpServletRequest req, HttpServletResponse resp, int roleId) {
        List<EnfordSystemMenu> menuList = null;
        try {
            menuList = roleService.getMenusByRoleId(roleId);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            logger.error("exception occured when getMenu:" + ex);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(menuList));
    }

    private int[] getMenuIds(EnfordSystemRole role) {
        //获取菜单id
        String menuIdsStr = role.getMenu_ids();
        //对菜单id进行处理
        if (!StringUtil.isEmpty(menuIdsStr) && menuIdsStr.contains(",")) {
            String[] menuIdsArrayStr = menuIdsStr.split(",");
            return StringUtil.convert(menuIdsArrayStr);
        } else if(!StringUtil.isEmpty(menuIdsStr)) {
            return new int[]{Integer.valueOf(menuIdsStr)};
        } else{
            return new int[0];
        }
    }

}
