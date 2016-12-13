package com.demo.web.sg;

import com.alibaba.fastjson.JSONObject;
import com.demo.model.EnfordProductArea;
import com.demo.model.EnfordProductCity;
import com.demo.model.EnfordSystemUser;
import com.demo.model.RespBody;
import com.demo.service.AreaService;
import com.demo.service.CityService;
import com.demo.util.Consts;
import com.demo.util.FastJSONHelper;
import com.demo.util.ResponseUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/2/1
 */
@Controller
public class CityController {

    private static final Logger logger = Logger.getLogger(CityController.class);

    @Resource
    CityService cityService;

    @RequestMapping("/city/list")
    public String get(HttpServletRequest req, HttpServletResponse resp) {
        return "/city/city";
    }

    @RequestMapping("/city/get")
    public void get2(HttpServletRequest req, HttpServletResponse resp) {
        List<EnfordProductCity> cityList = null;
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            String cityName = req.getParameter("name");
            if (cityName != null && !cityName.trim().equals("")) {
                param.put("name", URLDecoder.decode(cityName, "UTF-8"));
            }
            cityList = cityService.getCity(param);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        JSONObject result = new JSONObject();
        result.put("rows", cityList);
        //result.put("total", total);
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(result));
    }

    @RequestMapping("/city/add")
    public void add(HttpServletRequest req, HttpServletResponse resp, EnfordProductCity city) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            int ret = cityService.addCity(city);
            ResponseUtil.checkResult(ret, "添加城市", respBody);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            ResponseUtil.sendFailed("添加城市出错:" + ex.getMessage(), respBody);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    @RequestMapping("/city/update")
    public void update(HttpServletRequest req, HttpServletResponse resp, EnfordProductCity city) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            int ret = cityService.updateCity(city);
            ResponseUtil.checkResult(ret, "更新城市", respBody);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            ResponseUtil.sendFailed("更新城市出错:" + ex.getMessage(), respBody);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    @RequestMapping("/city/delete")
    public void delete(HttpServletRequest req, HttpServletResponse resp, int id) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            //判断该区域下是否有门店,如果有问题,则不允许删除
            EnfordProductCity city = cityService.getCityById(id);
            if (city.getChildren().size() > 0) {
                ResponseUtil.sendFailed("该区域下已有" + city.getChildren().size() + "家门店,请删除所有门店后重试!", respBody);
            } else {
                int ret = cityService.deleteCity(id);
                ResponseUtil.checkResult(ret, "删除城市", respBody);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            ResponseUtil.sendFailed("删除城市出错:" + ex.getMessage(), respBody);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }
}
