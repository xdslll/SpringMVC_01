package com.demo.web.sg;

import com.alibaba.fastjson.JSONObject;
import com.demo.model.*;
import com.demo.service.CityService;
import com.demo.service.CompetitorService;
import com.demo.service.DeptService;
import com.demo.service.MarketResearchSerivce;
import com.demo.util.Consts;
import com.demo.util.FastJSONHelper;
import com.demo.util.ResponseUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/2/1
 */
@Controller
public class DeptController {

    private static final Logger logger = Logger.getLogger(DeptController.class);

    @Resource
    DeptService deptService;

    @Resource
    CityService cityService;

    @Resource
    CompetitorService compService;

    @Resource
    MarketResearchSerivce marketResearchSerivce;

    @RequestMapping("/dept/manage")
    public String manage() {
        return "/dept/dept2";
    }

    @RequestMapping("/dept/get")
    public void getDepts(HttpServletRequest req, HttpServletResponse resp,
                         @RequestParam("page") int page,
                         @RequestParam("rows") int pageSize) {
        List<EnfordProductDepartment> depts = null;
        int total = 0;
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            //设置搜索条件
            String name = req.getParameter("name");
            if (name != null) {
                name = URLDecoder.decode(name, "UTF-8");
                param.put("keyword", name);
            }
            //设置页数信息
            param.put("page", (page - 1) * pageSize);
            param.put("pageSize", pageSize);
            depts = deptService.get(param);
            total = deptService.count(param);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        JSONObject result = new JSONObject();
        result.put("rows", depts);
        result.put("total", total);
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(result));
    }

    @RequestMapping("/dept/get2")
    public void getDepts(HttpServletRequest req, HttpServletResponse resp) {
        List<EnfordProductDepartment> depts = null;
        try {
            depts = deptService.get(null);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(depts));
    }

    @RequestMapping("/dept/add")
    public void addDept(HttpServletRequest req, HttpServletResponse resp, EnfordProductDepartment dept) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            String cityName = dept.getCityName();
            EnfordProductCity city = cityService.getCityByName(cityName);
            String compName = dept.getCompName();
            EnfordProductCompetitors comp = compService.getCompByName(compName);
            if (city == null) {
                ResponseUtil.sendFailed("必须填写已经录入系统的城市", respBody);
            } else if (comp == null) {
                ResponseUtil.sendFailed("必须填写已经录入系统的竞争门店", respBody);
            } else {
                dept.setCityId(city.getId());
                dept.setCompId(comp.getId());
                int ret = deptService.addDept(dept);
                ResponseUtil.checkResult(ret, "添加门店", respBody);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            ResponseUtil.sendFailed("新增门店失败:" + ex.getMessage(), respBody);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    @RequestMapping("/dept/update")
    public void updateDept(HttpServletRequest req, HttpServletResponse resp, EnfordProductDepartment dept) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            String cityName = dept.getCityName();
            EnfordProductCity city = cityService.getCityByName(cityName);
            String compName = dept.getCompName();
            EnfordProductCompetitors comp = compService.getCompByName(compName);
            if (city == null) {
                ResponseUtil.sendFailed("必须填写已经录入系统的城市", respBody);
            } else if (comp == null) {
                ResponseUtil.sendFailed("必须填写已经录入系统的竞争门店", respBody);
            } else {
                dept.setCityId(city.getId());
                dept.setCompId(comp.getId());
                int ret = deptService.updateDept(dept);
                ResponseUtil.checkResult(ret, "编辑门店", respBody);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            ResponseUtil.sendFailed("更新门店失败:" + ex.getMessage(), respBody);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    @RequestMapping("/dept/delete")
    public void deleteOrg(HttpServletRequest req, HttpServletResponse resp, int id) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("exeId", id);
            List<EnfordMarketResearchDept> deptList = marketResearchSerivce.getResearchDeptByParam(param);
            if (deptList != null && deptList.size() > 0) {
                ResponseUtil.sendFailed("该门店下已有" + deptList.size() + "条市调清单,无法删除!", respBody);
            } else {
                int ret = deptService.deleteDept(id);
                ResponseUtil.checkResult(ret, "删除门店", respBody);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            ResponseUtil.sendFailed("删除门店失败:" + ex.getMessage(), respBody);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    /**
     * 自动生成指定部门下用户账号
     *
     * @param req
     * @param resp
     * @param deptId
     * @param num
     */
    @RequestMapping("/dept/user/generate")
    public void generateDeptUsers(HttpServletRequest req, HttpServletResponse resp,
                                @RequestParam("deptId") int deptId,
                                @RequestParam("num") int num,
                                @RequestParam("roleId") int roleId) {
        //System.out.println("deptId=" + deptId + ",num=" + num + ",roleId=" + roleId);
        RespBody<String> respBody = new RespBody<String>();
        try {
            int ret = deptService.generate(deptId, num, roleId);
            ResponseUtil.checkResult(ret, "生成账号", respBody);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            ResponseUtil.sendFailed("生成账号失败:" + ex.getMessage(), respBody);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }
    
}
