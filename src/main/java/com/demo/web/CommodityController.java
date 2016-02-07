package com.demo.web;

import com.alibaba.fastjson.JSONObject;
import com.demo.model.EnfordProductCommodity;
import com.demo.model.EnfordProductDepartment;
import com.demo.model.RespBody;
import com.demo.service.CommodityService;
import com.demo.service.DeptService;
import com.demo.util.Consts;
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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/2/1
 */
@Controller
public class CommodityController {

    private static final Logger logger = Logger.getLogger(CommodityController.class);

    @Resource
    CommodityService commodityService;

    @RequestMapping("/cod/manage")
    public String manage() {
        return "/cod/cod";
    }

    @RequestMapping("/cod/get")
    public void getCods(HttpServletRequest req, HttpServletResponse resp,
                        @RequestParam("page") int page,
                        @RequestParam("rows") int pageSize) {
        /*String pageStr = req.getParameter("page");
        String pageSizeStr = req.getParameter("rows");
        //设置参数默认值
        int page = 0;
        int pageSize = 15;
        if (!StringUtil.isEmpty(pageStr)) {
            page = Integer.parseInt(pageStr);
        }
        if (!StringUtil.isEmpty(pageSizeStr)) {
            pageSize = Integer.parseInt(pageSizeStr);
        }*/
        String order = req.getParameter("order");
        String sort = req.getParameter("sort");
        String pName = null;
        try {
            pName = req.getParameter("pName");
            if (pName != null) {
                pName = new String(pName.getBytes("ISO-8859-1"), "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {

        }
        System.out.println("pName=" + pName);
        //System.out.println("page=" + page + ",pageSize=" + pageSize + ",order=" + order + ",sort=" + sort);
        List<EnfordProductCommodity> cods = null;
        int total = 0;
        try {
            if (sort != null && sort.equals("categoryCode")) {
                sort = "category_code";
            }
            //String orderBy = sort + " " + order;
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("page", (page - 1) * pageSize);
            param.put("pageSize", pageSize);
            param.put("sort", sort);
            param.put("order", order);
            if (pName != null) {
                param.put("pName", pName);{
                    param.put("pName", pName);
                }
            }
            cods = commodityService.select(param);
            total = commodityService.count(param);
        }
        catch (Exception ex) {
            logger.error("exception occurred when getCods:" + ex);
        }
        JSONObject result = new JSONObject();
        result.put("rows", cods);
        result.put("total", total);
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(result));
    }
}
