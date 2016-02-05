package com.demo.web;

import com.demo.model.RespBody;
import com.demo.util.Consts;
import com.demo.util.FastJSONHelper;
import com.demo.util.ResponseUtil;
import com.demo.model.EnfordSystemMenu;
import com.demo.service.MenuService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author xiads
 * @date 16/1/30
 */
@Controller
public class MenuController {

    private static final Logger logger = Logger.getLogger(MenuController.class);

    @Resource
    private MenuService menuService;

    @RequestMapping(value = "/menu/get")
    public void getMenu(HttpServletRequest req, HttpServletResponse resp) {
        List<EnfordSystemMenu> menuList = null;
        try {
            menuList = menuService.queryMenu();
        }
        catch (Exception ex) {
            logger.error("exception occured when getMenu:" + ex);
        }

        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(menuList));
    }

    @RequestMapping(value = "/menu/get/root")
    public void getRootMenu(HttpServletRequest req, HttpServletResponse resp) {
        List<EnfordSystemMenu> rootMenu = null;
        try {
            rootMenu = menuService.queryRootMenu();
        }
        catch (Exception ex) {
            logger.error("exception occured when getRootMenu:" + ex);
        }

        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(rootMenu));
    }

    @RequestMapping(value = "/menu/manage")
    public String showMenu(HttpServletRequest req, HttpServletResponse resp) {
        return "/menu/menu";
    }

    @RequestMapping(value = "/menu/add")
    public void addMenu(HttpServletRequest req, HttpServletResponse resp, EnfordSystemMenu menu) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            menuService.addMenu(menu);
            respBody.setCode(Consts.SUCCESS);
            respBody.setMsg("添加菜单成功！");
        }
        catch (Exception ex) {
            logger.error("exception occured when addMenu:" + ex);
            respBody.setCode(Consts.FAILED);
            respBody.setMsg("添加菜单出错:" + ex.getMessage());
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    @RequestMapping(value = "/menu/update")
    public void updateMenu(HttpServletRequest req, HttpServletResponse resp, EnfordSystemMenu menu) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            menuService.updateMenu(menu);
            respBody.setCode(Consts.SUCCESS);
            respBody.setMsg("编辑菜单成功！");
        }
        catch (Exception ex) {
            logger.error("exception occured when updateMenu:" + ex);
            respBody.setCode(Consts.FAILED);
            respBody.setMsg("编辑菜单出错:" + ex.getMessage());
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    @RequestMapping(value = "/menu/delete")
    public void deleteMenu(HttpServletRequest req, HttpServletResponse resp, int menuId) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            menuService.deleteMenu(menuId);
            respBody.setCode(Consts.SUCCESS);
            respBody.setMsg("删除菜单成功！");
        }
        catch (Exception ex) {
            logger.error("exception occured when deleteMenu:" + ex);
            respBody.setCode(Consts.FAILED);
            respBody.setMsg("删除菜单出错:" + ex.getMessage());
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

}
