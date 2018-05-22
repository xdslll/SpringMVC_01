package com.demo.web.weixin;

import com.demo.util.Consts;
import com.demo.util.EncryptUtil;
import com.demo.util.ResponseUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 微信相关的操作
 *
 * @author xiads
 * @date 16/2/22
 */
@Controller
public class WexinController implements Consts {

    private static final Logger logger = Logger.getLogger(WexinController.class);

    @RequestMapping("/weixin/check")
    public void check(HttpServletRequest req, HttpServletResponse resp) {
        Map param = req.getParameterMap();
        Set<Map.Entry> set = param.entrySet();
        Iterator<Map.Entry> it = set.iterator();
        while (it.hasNext()) {
            Map.Entry entry = it.next();
            // System.out.println("------------------------");
            // System.out.println(entry.getKey().toString() + "=" + entry.getValue().toString());
            // System.out.println("------------------------");
        }
        /*try {
            String echostr = req.getParameter("echostr");
            String timestamp = req.getParameter("timestamp");
            String nonce = req.getParameter("nonce");
            String signature = req.getParameter("signature");
            String[] array = {TOKEN, timestamp, nonce};
            if (checkSignature(array, signature)) {
                ResponseUtil.writeStringResponse(resp, echostr);
            } else {
                ResponseUtil.writeStringResponse(resp, "error!");
            }
        } catch (Exception ex) {
            ResponseUtil.writeStringResponse(resp, "error!");
        }*/
    }

    /**
     *
     * @param array
     * @param signature
     * @return
     */
    private boolean checkSignature(String[] array, String signature) {
        Arrays.sort(array);
        String sortStr = array[0] + array[1] + array[2];
        // System.out.println("origin: " + sortStr);
        String sign = EncryptUtil.encrypt(sortStr, "SHA-1");
        // System.out.println("SHA1: " + sign);
        if (sign != null && sign.equals(signature)) {
            return true;
        } else {
            return false;
        }
    }

    @RequestMapping("/weixin/login")
    public String login() {
        return "/weixin/login";
    }

    @RequestMapping("/weixin/login.do")
    public ModelAndView dologin(HttpServletRequest req, HttpServletResponse resp,
                                @RequestParam("username") String username,
                                @RequestParam("password") String password) {
        ModelAndView model = new ModelAndView();
        model.addObject("errormsg", "登录失败!");
        model.setViewName("/weixin/login");
        return model;
    }

    @RequestMapping("/weixin/menu")
    public String menu() {
        return "/weixin/menu";
    }
}
