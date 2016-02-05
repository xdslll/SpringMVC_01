package com.demo.web;

import com.demo.model.EnfordProductCommodity;
import com.demo.model.EnfordProductDepartment;
import com.demo.model.RespBody;
import com.demo.service.CommodityService;
import com.demo.service.DeptService;
import com.demo.util.Consts;
import com.demo.util.FastJSONHelper;
import com.demo.util.ResponseUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
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
public class CommodityController {

    private static final Logger logger = Logger.getLogger(CommodityController.class);

    @Resource
    CommodityService commodityService;

    @RequestMapping("/cod/manage")
    public String manage() {
        return "/cod/cod";
    }

    @RequestMapping("/cod/get")
    public void getCods(HttpServletRequest req, HttpServletResponse resp) {
        List<EnfordProductCommodity> cods = null;
        try {
            cods = commodityService.select();
        }
        catch (Exception ex) {
            logger.error("exception occurred when getCods:" + ex);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(cods));
    }
}
