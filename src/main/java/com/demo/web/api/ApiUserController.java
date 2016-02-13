package com.demo.web.api;

import com.demo.model.EnfordSystemUser;
import com.demo.model.RespBody;
import com.demo.service.UserService;
import com.demo.util.Consts;
import com.demo.util.FastJSONHelper;
import com.demo.util.ResponseUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xiads
 * @date 16/2/8
 */
@Controller
public class ApiUserController implements Consts {

    private static final Logger logger = Logger.getLogger(ApiUserController.class);

    @Resource
    UserService userService;

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public void doLogin(HttpServletRequest req, HttpServletResponse resp,
                        @RequestParam("user") String user,
                        @RequestParam("pwd") String pwd) {
        RespBody<EnfordSystemUser> respBody = new RespBody<EnfordSystemUser>();
        EnfordSystemUser enfordUser = null;
        try {
            enfordUser = userService.login(user, pwd);
            if (enfordUser != null) {
                respBody.setCode(SUCCESS);
                respBody.setMsg("登录成功!");
                respBody.setData(enfordUser);
            } else {
                respBody.setCode(FAILED);
                respBody.setMsg("登录失败!");
            }
        } catch (Exception ex) {
            respBody.setCode(FAILED);
            respBody.setMsg("登录失败:" + ex.getMessage());
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }
}
