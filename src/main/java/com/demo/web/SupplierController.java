package com.demo.web;

import com.demo.model.EnfordProductCategory;
import com.demo.model.EnfordProductSupplier;
import com.demo.service.CategoryService;
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
public class SupplierController {

    private static final Logger logger = Logger.getLogger(SupplierController.class);

    @Resource
    SupplierService supplierService;

    @RequestMapping("/supplier/manage")
    public String manage() {
        return "/supplier/supplier";
    }

    @RequestMapping("/supplier/get")
    public void getSuppliers(HttpServletRequest req, HttpServletResponse resp) {
        List<EnfordProductSupplier> suppliers = null;
        try {
            suppliers = supplierService.select();
        }
        catch (Exception ex) {
            logger.error("exception occurred when getSuppliers:" + ex);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(suppliers));
    }
}
