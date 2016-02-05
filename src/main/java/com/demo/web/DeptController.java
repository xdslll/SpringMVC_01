package com.demo.web;

import com.demo.model.EnfordProductDepartment;
import com.demo.model.EnfordProductOrganization;
import com.demo.model.RespBody;
import com.demo.service.DeptService;
import com.demo.service.OrgService;
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
public class DeptController {

    private static final Logger logger = Logger.getLogger(DeptController.class);

    @Resource
    DeptService deptService;

    @RequestMapping("/dept/manage")
    public String manage() {
        return "/dept/dept";
    }

    @RequestMapping("/dept/get")
    public void getDepts(HttpServletRequest req, HttpServletResponse resp) {
        List<EnfordProductDepartment> depts = null;
        try {
            depts = deptService.selectAll();
        }
        catch (Exception ex) {
            logger.error("exception occurred when getDepts:" + ex);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(depts));
    }

    @RequestMapping("/dept/add")
    public void addDept(HttpServletRequest req, HttpServletResponse resp, EnfordProductDepartment dept) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            deptService.addDept(dept);
            respBody.setCode(Consts.SUCCESS);
            respBody.setMsg("添加部门成功！");
        }
        catch (Exception ex)
        {
            logger.error("exception occurred when addDept:" + ex);
            respBody.setCode(Consts.FAILED);
            respBody.setMsg("添加部门出错:" + ex.getMessage());
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    @RequestMapping("/dept/update")
    public void updateDept(HttpServletRequest req, HttpServletResponse resp, EnfordProductDepartment dept) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            deptService.updateDept(dept);
            respBody.setCode(Consts.SUCCESS);
            respBody.setMsg("编辑部门成功！");
        }
        catch (Exception ex) {
            logger.error("exception occurred when updateDept:" + ex);
            respBody.setCode(Consts.FAILED);
            respBody.setMsg("编辑部门出错:" + ex.getMessage());
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    @RequestMapping("/dept/delete")
    public void deleteOrg(HttpServletRequest req, HttpServletResponse resp, int deptId) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            deptService.deleteDept(deptId);
            respBody.setCode(Consts.SUCCESS);
            respBody.setMsg("删除部门成功！");
        }
        catch (Exception ex) {
            logger.error("exception occurred when deleteOrg:" + ex);
            respBody.setCode(Consts.FAILED);
            respBody.setMsg("删除部门出错:" + ex.getMessage());
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }
    
}
