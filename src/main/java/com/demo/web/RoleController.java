package com.demo.web;

import com.demo.model.EnfordSystemMenu;
import com.demo.model.EnfordSystemRole;
import com.demo.model.RespBody;
import com.demo.service.RoleService;
import com.demo.util.Consts;
import com.demo.util.FastJSONHelper;
import com.demo.util.ResponseUtil;
import com.demo.util.Tools;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author xiads
 * @date 16/2/2
 */
@Controller
public class RoleController {

    private static final Logger logger = Logger.getLogger(RoleController.class);

    @Resource
    private RoleService roleService;

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
            logger.error("exception occuried when getRoles:" + ex);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(roles));
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
        }
        catch (Exception ex)
        {
            logger.error("exception occuried when addRole:" + ex);
            respBody.setCode(Consts.FAILED);
            respBody.setErrorMsg("添加角色出错:" + ex.getMessage());
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
            roleService.addRoleMenu(roleId, menuIds);
            respBody.setCode(Consts.SUCCESS);
            respBody.setMsg("编辑角色成功！");
        }
        catch (Exception ex) {
            logger.error("exception occuried when updateRole:" + ex);
            respBody.setCode(Consts.FAILED);
            respBody.setErrorMsg("编辑角色出错:" + ex.getMessage());
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    @RequestMapping("/role/delete")
    public void deleteRole(HttpServletRequest req, HttpServletResponse resp, int roleId) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            roleService.deleteRole(roleId);
            respBody.setCode(Consts.SUCCESS);
            respBody.setMsg("删除角色成功！");
        }
        catch (Exception ex) {
            logger.error("exception occuried when deleteRole:" + ex);
            respBody.setCode(Consts.FAILED);
            respBody.setErrorMsg("删除角色出错:" + ex.getMessage());
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
            logger.error("exception occuried when getMenu:" + ex);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(menuList));
    }

    private int[] getMenuIds(EnfordSystemRole role) {
        //获取菜单id
        String menuIdsStr = role.getMenu_ids();
        //对菜单id进行处理
        if (!Tools.isEmpty(menuIdsStr) && menuIdsStr.contains(",")) {
            String[] menuIdsArrayStr = menuIdsStr.split(",");
            return Tools.convert(menuIdsArrayStr);
        }
        {
            return new int[0];
        }
    }

}
