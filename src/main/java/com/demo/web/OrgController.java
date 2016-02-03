package com.demo.web;

import com.demo.model.EnfordProductOrganization;
import com.demo.model.RespBody;
import com.demo.service.OrgService;
import com.demo.util.*;
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
public class OrgController {

    private static final Logger logger = Logger.getLogger(OrgController.class);

    @Resource
    OrgService orgService;

    @RequestMapping("/org/manage")
    public String manage() {
        return "/org/org";
    }

    @RequestMapping("/org/get")
    public void getOrgs(HttpServletRequest req, HttpServletResponse resp) {
        List<EnfordProductOrganization> orgs = null;
        try {
            orgs = orgService.getOrgs();
        }
        catch (Exception ex) {
            logger.error("exception occuried when getOrgs:" + ex);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(orgs));
    }

    @RequestMapping("/org/add")
    public void addOrg(HttpServletRequest req, HttpServletResponse resp, EnfordProductOrganization org) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            orgService.addOrg(org);
            respBody.setCode(Consts.SUCCESS);
            respBody.setMsg("添加机构成功！");
        }
        catch (Exception ex)
        {
            logger.error("exception occuried when addOrg:" + ex);
            respBody.setCode(Consts.FAILED);
            respBody.setErrorMsg("添加机构出错:" + ex.getMessage());
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    @RequestMapping("/org/update")
    public void updateOrg(HttpServletRequest req, HttpServletResponse resp, EnfordProductOrganization org) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            orgService.updateOrg(org);
            respBody.setCode(Consts.SUCCESS);
            respBody.setMsg("编辑机构成功！");
        }
        catch (Exception ex) {
            logger.error("exception occuried when updateOrg:" + ex);
            respBody.setCode(Consts.FAILED);
            respBody.setErrorMsg("编辑机构出错:" + ex.getMessage());
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    @RequestMapping("/org/delete")
    public void deleteOrg(HttpServletRequest req, HttpServletResponse resp, int orgId) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            orgService.deleteOrg(orgId);
            respBody.setCode(Consts.SUCCESS);
            respBody.setMsg("删除机构成功！");
        }
        catch (Exception ex) {
            logger.error("exception occuried when deleteOrg:" + ex);
            respBody.setCode(Consts.FAILED);
            respBody.setErrorMsg("删除用户出错:" + ex.getMessage());
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }
    
}
