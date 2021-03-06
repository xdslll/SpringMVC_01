package com.demo.web.system;

import com.alibaba.fastjson.JSONObject;
import com.demo.dao.EnfordSystemUserMapper;
import com.demo.model.RespBody;
import com.demo.model.EnfordSystemUser;
import com.demo.service.CommodityPriceService;
import com.demo.service.UserService;
import com.demo.util.*;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/2/1
 */
@Controller
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);

    @Resource
    UserService userService;

    @Resource
    EnfordSystemUserMapper userMapper;

    @Resource
    CommodityPriceService priceService;

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
            if (user != null) {// && !StringUtil.isEmpty(rememberpwd)) {
                /*if("on".equals(rememberpwd)) {
                    CookiesUtil.saveCookie(resp,"rempwd","1");
                    CookiesUtil.saveCookie(resp,"username",username);
                    CookiesUtil.saveCookie(resp,"password",password);
                }
                else {
                    CookiesUtil.removeCookie(resp, "rempwd");
                    CookiesUtil.removeCookie(resp,"username");
                    CookiesUtil.removeCookie(resp,"password");
                }*/
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
        int count = 0;
        try {
            String _page = req.getParameter("page");
            String _rows = req.getParameter("rows");
            Map<String, Object> param = new HashMap<String, Object>();
            if (_page != null && _rows != null) {
                int page = Integer.valueOf(_page);
                int pageSize = Integer.valueOf(_rows);
                param.put("page", (page - 1) * pageSize);
                param.put("pageSize", pageSize);
            }
            users = userService.getUserByParam(param);
            count = userService.count();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        JSONObject result = new JSONObject();
        result.put("rows", users);
        result.put("total", count);
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(result));
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
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("uploadBy", userId);
            int count = priceService.countPrice(param);
            if (count > 0) {
                ResponseUtil.sendFailed("该用户已上报" + count + "条市调记录,无法删除!", respBody);
            } else {
                int ret = userService.deleteUser(userId);
                ResponseUtil.checkResult(ret, "删除用户", respBody);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ResponseUtil.sendFailed("删除用户出错:" + ex.getMessage(), respBody);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    @RequestMapping("/user/changePwd")
    public void changePwd(HttpServletRequest req, HttpServletResponse resp, int userId) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            String orgPwd = req.getParameter("origin_pwd");
            String newPwd = req.getParameter("new_pwd");
            String confirmPwd = req.getParameter("confirm_pwd");
            EnfordSystemUser user = userService.getUser(userId);
            // System.out.println("原密码:" + orgPwd + ",新密码:" + newPwd + ",新密码2:" + confirmPwd + ",真实密码:" + user.getPassword());
            if (user != null) {
                String realPwd = user.getPassword();
                if (!EncryptUtil.md5(orgPwd).equals(realPwd)) {
                    respBody.setCode(Consts.FAILED);
                    respBody.setMsg("原密码输入错误!");
                } else {
                    if (!newPwd.equals(confirmPwd)) {
                        respBody.setCode(Consts.FAILED);
                        respBody.setMsg("两次输入的新密码不符!");
                    } else {
                        user.setPassword(EncryptUtil.md5(newPwd));
                        //userService.updateUser(user);
                        userMapper.updateByPrimaryKeySelective(user);
                        respBody.setCode(Consts.SUCCESS);
                        respBody.setMsg("修改密码成功！");
                    }
                }
            } else {
                respBody.setCode(Consts.FAILED);
                respBody.setMsg("用户不存在!");
            }
        }
        catch (Exception ex) {
            logger.error("exception occured when changePwd:" + ex);
            respBody.setCode(Consts.FAILED);
            respBody.setMsg("修改密码失败:" + ex.getMessage());
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }
}
