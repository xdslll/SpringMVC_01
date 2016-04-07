package com.demo.web.sg;

import com.demo.model.RespBody;
import com.demo.util.*;
import org.apache.commons.io.FileUtils;
import org.aspectj.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * @author xiads
 * @date 16/2/24
 */
@Controller
public class CheckController implements Consts {

    @RequestMapping("/check/enable")
    public void checkEnable(HttpServletRequest req, HttpServletResponse resp) {
        RespBody respBody = new RespBody();
        try {
            if (verify()) {
                respBody.setCode(SUCCESS);
                respBody.setMsg("验证成功");
            } else {
                respBody.setCode(FAILED);
                respBody.setMsg("验证失败");
            }
            ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
        } catch (Exception e) {
            e.printStackTrace();
            respBody.setCode(FAILED);
            respBody.setMsg("验证失败");
            ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
        }
    }

    @RequestMapping("/check/verify")
    public ModelAndView checkVerify(ServletRequest req) {
        ModelAndView model = new ModelAndView();
        model.addObject("errormsg", req.getParameter("errormsg"));
        model.setViewName("/user/verify");
        return model;
    }

    @RequestMapping("/check/update")
    public ModelAndView checkUpdate(HttpServletRequest req, HttpServletResponse resp,
                            @RequestParam("licence") String licence) {
        ModelAndView model = new ModelAndView();
        try {
            updateLicence(licence);
            if (verify()) {
                model.setViewName("/user/login");
            } else {
                model.addObject("errormsg", "验证失败");
                model.setViewName("/user/verify");
            }
        } catch (Exception e) {
            model.addObject("errormsg", "验证失败");
            model.setViewName("/user/verify");
        }
        return model;
    }

    public static void main(String[] args) {
        new CheckController().encryption();
        //System.out.println(new CheckController().verify());
    }

    private void updateLicence(String licence) {
        File licenceDir = new File(Config.getLicencePath());
        String licenceFileName = "licence";
        File licenceKey = new File(licenceDir, licenceFileName);
        if (licenceKey.exists()) {
            try {
                FileUtils.writeStringToFile(licenceKey, licence);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String encryption() {
        Calendar c = Calendar.getInstance();
        c.set(2016, Calendar.JANUARY, 1);
        Date date = c.getTime();
        long dateStart = date.getTime();
        c.set(2016, Calendar.MAY, 31);
        date = c.getTime();
        long dateEnd = date.getTime();
        System.out.println("start date : " + dateStart);
        System.out.println("end date : " + dateEnd);
        String key = String.valueOf(dateStart) + String.valueOf(dateEnd) + Config.getProductCode() + Config.getSecrectKey();
        System.out.println("key : " + key);
        System.out.println("key : " + EncryptUtil.encodeBase64(key));
        return EncryptUtil.encodeBase64(key);
    }

    private boolean verify() {
        /*boolean ret = false;
        try {
            File licenceDir = new File(Config.getLicencePath());
            String licenceFileName = "licence";
            File licenceKey = new File(licenceDir, licenceFileName);
            if (licenceKey.exists()) {
                String content = FileUtil.readAsString(licenceKey);
                System.out.println(content);
                content = EncryptUtil.decodeBase64(content);
                System.out.println(content);
                //获取开始时间
                Long start = Long.valueOf(content.substring(0, 13));
                //获取结束时间
                Long end = Long.valueOf(content.substring(13, 26));
                //获取产品编码
                String productCode = content.substring(26, 30);
                //获取SecretKey
                String secretKey = content.substring(30, content.length());

                System.out.println("start : " + start);
                System.out.println("end : " + end);
                System.out.println("productCode : " + productCode);
                System.out.println("secretKey : " + secretKey);
                Date now = new Date();
                System.out.println(String.valueOf(now.compareTo(new Date(start))));
                System.out.println(String.valueOf(now.compareTo(new Date(end))));
                if (now.compareTo(new Date(start)) > 0
                        && now.compareTo(new Date(end)) < 0
                        && productCode.equals(Config.getProductCode())
                        && secretKey.equals(Config.getSecrectKey())) {
                    ret = true;
                }
            }
        } catch (IOException ex) {

        }
        return ret;*/
        return true;
    }
}
