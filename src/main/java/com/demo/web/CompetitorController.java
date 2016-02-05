package com.demo.web;

import com.demo.model.EnfordProductCompetitors;
import com.demo.model.EnfordProductSupplier;
import com.demo.service.CompetitorService;
import com.demo.service.SupplierService;
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
public class CompetitorController {

    private static final Logger logger = Logger.getLogger(CompetitorController.class);

    @Resource
    CompetitorService competitorService;

    @RequestMapping("/competitor/manage")
    public String manage() {
        return "/competitor/competitor";
    }

    @RequestMapping("/competitor/get")
    public void getCompetitors(HttpServletRequest req, HttpServletResponse resp) {
        List<EnfordProductCompetitors> suppliers = null;
        try {
            suppliers = competitorService.select();
        }
        catch (Exception ex) {
            logger.error("exception occurred when getCompetitors:" + ex);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(suppliers));
    }
}
