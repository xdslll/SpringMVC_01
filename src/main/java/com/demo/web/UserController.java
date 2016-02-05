package com.demo.web;

import com.demo.model.RespBody;
import com.demo.model.EnfordSystemUser;
import com.demo.service.UserService;
import com.demo.util.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author xiads
 * @date 16/2/1
 */
@Controller
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);

    @Resource
    UserService userService;

    @RequestMapping("/login")
    public String login() {
        return "/user/login";
    }

    /**
     * 登录方法
     *
     * @param req
     * @param resp
     */
    @RequestMapping("/dologin")
    public String dologin(HttpServletRequest req, HttpServletResponse resp, Model model) {
        //获取参数
        String rememberpwd = req.getParameter("rememberpwd");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        //登录后的用户信息
        EnfordSystemUser user = null;
        //返回值
        RespBody<String> respBody = new RespBody<String>();
        try
        {
            //对密码进行md5加密
            password = EncryptUtil.md5(password);
            //执行登录判断
            user = userService.login(username, password);
            respBody.setCode(Consts.SUCCESS);
            respBody.setMsg("登录成功");
            //登录成功后,将用户信息写入session
            if (user != null) {
                if("on".equals(rememberpwd))
                {
                    CookiesUtil.saveCookie(resp,"rempwd","1");
                    CookiesUtil.saveCookie(resp,"username",username);
                    CookiesUtil.saveCookie(resp,"password",password);
                }
                else
                {
                    CookiesUtil.removeCookie(resp, "rempwd");
                    CookiesUtil.removeCookie(resp,"username");
                    CookiesUtil.removeCookie(resp,"password");
                }
                req.getSession().setAttribute("user", user);

                model.addAttribute("user", user);
                return "redirect:/home";
            } else {
                model.addAttribute("errormsg", "登录失败!");
                return "/user/login";
            }
        }
        catch (Exception ex)
        {
            model.addAttribute("errormsg", "用户名或密码错误");
            return "/user/login";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().removeAttribute("user");
        return "/user/login";
    }

    @RequestMapping("/user/manage")
    public String manage() {
        return "/user/user";
    }

    @RequestMapping("/user/get")
    public void getUsers(HttpServletRequest req, HttpServletResponse resp) {
        List<EnfordSystemUser> users = null;
        try {
            users = userService.getUsers();
        }
        catch (Exception ex) {
            logger.error("exception occured when getUsers:" + ex);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(users));
    }

    @RequestMapping("/user/add")
    public void addUser(HttpServletRequest req, HttpServletResponse resp, EnfordSystemUser user) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            userService.addUser(user);
            respBody.setCode(Consts.SUCCESS);
            respBody.setMsg("添加用户成功！");
        }
        catch (Exception ex)
        {
            logger.error("exception occured when addUser:" + ex);
            respBody.setCode(Consts.FAILED);
            respBody.setMsg("添加用户出错:" + ex.getMessage());
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    @RequestMapping("/user/update")
    public void updateUser(HttpServletRequest req, HttpServletResponse resp, EnfordSystemUser user) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            userService.updateUser(user);
            respBody.setCode(Consts.SUCCESS);
            respBody.setMsg("编辑用户成功！");
        }
        catch (Exception ex) {
            logger.error("exception occured when updateUser:" + ex);
            respBody.setCode(Consts.FAILED);
            respBody.setMsg("编辑用户出错:" + ex.getMessage());
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    @RequestMapping("/user/delete")
    public void deleteUser(HttpServletRequest req, HttpServletResponse resp, int userId) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            userService.deleteUser(userId);
            respBody.setCode(Consts.SUCCESS);
            respBody.setMsg("删除用户成功！");
        }
        catch (Exception ex) {
            logger.error("exception occured when deleteUser:" + ex);
            respBody.setCode(Consts.FAILED);
            respBody.setMsg("删除用户出错:" + ex.getMessage());
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }
    
}
