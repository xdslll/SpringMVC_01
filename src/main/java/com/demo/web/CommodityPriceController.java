package com.demo.web;

import com.alibaba.fastjson.JSONObject;
import com.demo.model.EnfordProductPrice;
import com.demo.service.EnfordProductPriceService;
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
 * @date 16/2/7
 */
@Controller
public class CommodityPriceController {

    private static final Logger logger = Logger.getLogger(CommodityPriceController.class);

    @Resource
    EnfordProductPriceService priceService;

    @RequestMapping("/cod/price/get")
    public void getCodPrice(HttpServletRequest req, HttpServletResponse resp,
                              @RequestParam("page") int page,
                              @RequestParam("rows") int pageSize) {
        if (StringUtil.isEmpty(req.getParameter("comId"))) {
            return;
        }
        List<EnfordProductPrice> priceList = null;
        int total = 0;
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            //设置页数信息
            param.put("page", (page - 1) * pageSize);
            param.put("pageSize", pageSize);
            param.put("comId", req.getParameter("comId"));
            priceList = priceService.getProductPrice(param);
            total = priceService.countProductPrice(param);
        }
        catch (Exception ex) {
            logger.error("exception occurred when getCodPrice:" + ex);
            ex.printStackTrace();
        }
        JSONObject result = new JSONObject();
        result.put("rows", priceList);
        result.put("total", total);
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(result));
    }
}
