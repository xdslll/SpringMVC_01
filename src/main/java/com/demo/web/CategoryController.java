package com.demo.web;

import com.demo.model.EnfordProductCategory;
import com.demo.model.EnfordProductCommodity;
import com.demo.service.CategoryService;
import com.demo.service.CommodityService;
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
public class CategoryController {

    private static final Logger logger = Logger.getLogger(CategoryController.class);

    @Resource
    CategoryService categoryService;

    @RequestMapping("/category/manage")
    public String manage() {
        return "/category/category";
    }

    @RequestMapping("/category/get")
    public void getCagetory(HttpServletRequest req, HttpServletResponse resp) {
        List<EnfordProductCategory> cats = null;
        try {
            cats = categoryService.select();
        }
        catch (Exception ex) {
            logger.error("exception occurred when getCagetory:" + ex);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(cats));
    }
}
