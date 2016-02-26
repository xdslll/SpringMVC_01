package com.demo.web.sg;

import com.alibaba.fastjson.JSONObject;
import com.demo.model.EnfordMarketResearchCommodity;
import com.demo.service.MarketResearchSerivce;
import com.demo.util.FastJSONHelper;
import com.demo.util.ResponseUtil;
import com.demo.util.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
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
public class MarketCodController {

    private static final Logger logger = Logger.getLogger(MarketCodController.class);

    @Resource
    private MarketResearchSerivce marketService;

    @RequestMapping("/market_research/cod/manage")
    public String manage() {
        return "/market_research/cod";
    }

    @RequestMapping("/market_research/cod/get")
    public void getMarketCods(HttpServletRequest req, HttpServletResponse resp,
                               @RequestParam("page") int page,
                               @RequestParam("rows") int pageSize) {
        if (StringUtil.isEmpty(req.getParameter("resId"))) {
            return;
        }
        List<EnfordMarketResearchCommodity> codList = null;
        int total = 0;
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            //设置页数信息
            param.put("page", (page - 1) * pageSize);
            param.put("pageSize", pageSize);
            param.put("resId", req.getParameter("resId"));
            codList = marketService.getResearchCodByParam(param);
            total = marketService.countResearchCodByParam(param);
        }
        catch (Exception ex) {
            logger.error("exception occurred when getMarketCods:" + ex);
            ex.printStackTrace();
        }
        JSONObject result = new JSONObject();
        result.put("rows", codList);
        result.put("total", total);
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(result));
    }
}
