package com.demo.web.sg;

import com.demo.model.EnfordProductCity;
import com.demo.model.EnfordProductCompetitors;
import com.demo.model.RespBody;
import com.demo.service.CityService;
import com.demo.service.CompetitorService;
import com.demo.util.FastJSONHelper;
import com.demo.util.ResponseUtil;
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
 * @date 16/2/1
 */
@Controller
public class CompetitorController {

    private static final Logger logger = Logger.getLogger(CompetitorController.class);

    @Resource
    CompetitorService competitorService;

    @Resource
    CityService cityService;

    @RequestMapping("/competitor/manage")
    public String manage() {
        return "/competitor/competitor2";
    }

    @RequestMapping("/comp/get")
    public void getCompetitors(HttpServletRequest req, HttpServletResponse resp) {
        List<EnfordProductCompetitors> compList = null;
        try {
            compList = competitorService.select();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(compList));
    }

    @RequestMapping("/comp/add")
    public void add(HttpServletRequest req, HttpServletResponse resp, EnfordProductCompetitors comp) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            String cityName = comp.getCityName();
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("name", cityName);
            List<EnfordProductCity> cityList = cityService.getCity(param);
            if (cityList != null && cityList.size() > 0) {
                comp.setCityId(cityList.get(0).getId());
                int ret = competitorService.addComp(comp);
                ResponseUtil.checkResult(ret, "添加竞争门店", respBody);
            } else {
                ResponseUtil.sendFailed("添加竞争门店失败!", respBody);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ResponseUtil.sendFailed("添加竞争门店出错:" + ex.getMessage(), respBody);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    @RequestMapping("/comp/update")
    public void update(HttpServletRequest req, HttpServletResponse resp, EnfordProductCompetitors comp) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            String cityName = comp.getCityName();
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("name", cityName);
            List<EnfordProductCity> cityList = cityService.getCity(param);
            if (cityList != null && cityList.size() > 0) {
                comp.setCityId(cityList.get(0).getId());
                int ret = competitorService.updateComp(comp);
                ResponseUtil.checkResult(ret, "更新竞争门店", respBody);
            } else {
                ResponseUtil.sendFailed("更新竞争门店失败!", respBody);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ResponseUtil.sendFailed("更新竞争门店:" + ex.getMessage(), respBody);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    @RequestMapping("/comp/delete")
    public void delete(HttpServletRequest req, HttpServletResponse resp, int id) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            //判断该区域下是否有门店,如果有问题,则不允许删除
            EnfordProductCompetitors comp = competitorService.getCompById(id);
            if (comp.getChildren().size() > 0) {
                ResponseUtil.sendFailed("该竞争门店下已有" + comp.getChildren().size() + "家门店,请删除所有门店后重试!", respBody);
            } else {
                int ret = competitorService.deleteComp(id);
                ResponseUtil.checkResult(ret, "删除竞争门店", respBody);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ResponseUtil.sendFailed("删除城市出错:" + ex.getMessage(), respBody);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }
}
