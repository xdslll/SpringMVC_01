package com.demo.web.sg;

import com.alibaba.fastjson.JSONObject;
import com.demo.model.*;
import com.demo.service.AreaService;
import com.demo.service.DeptService;
import com.demo.service.MarketResearchSerivce;
import com.demo.service.RoleService;
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
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/2/1
 */
@Controller
public class AreaController {

    private static final Logger logger = Logger.getLogger(AreaController.class);

    @Resource
    AreaService areaService;

    @Resource
    DeptService deptService;

    @Resource
    RoleService roleService;

    @Resource
    MarketResearchSerivce marketService;

    @RequestMapping("/area/get")
    public void getAreas(HttpServletRequest req, HttpServletResponse resp) {
        List<EnfordProductArea> areaList = null;
        try {
            EnfordSystemUser user = (EnfordSystemUser) req.getSession().getAttribute("user");
            String resId = req.getParameter("resId");
            areaList = areaService.getAreaTree(Integer.parseInt(resId));
        } catch (Exception ex) {
            logger.error("exception occurred when getAreas:" + ex);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(areaList));
    }

    /**
     * 获取该用户所在区域的部门下的所有市调
     *
     * @param req
     * @param resp
     */
    @RequestMapping("/area/dept/list")
    public void getAreas2(HttpServletRequest req, HttpServletResponse resp,
                          @RequestParam("page") int page,
                          @RequestParam("rows") int pageSize) {
        List<EnfordProductDepartment> deptList = null;
        JSONObject result = new JSONObject();
        int count = 0;
        try {
            int state = 4;
            if (req.getParameter("state") != null) {
                state = Integer.parseInt(req.getParameter("state"));
            }
            String keyword = null;
            if (req.getParameter("keyword") == null) {
                keyword = "";
            } else {
                keyword = URLDecoder.decode(req.getParameter("keyword"), "UTF-8");
            }
            EnfordSystemUser user = (EnfordSystemUser) req.getSession().getAttribute("user");
            int roleId = user.getRoleId();
            EnfordSystemRole role = roleService.getRole(roleId);
            int areaId = -1;
            String areaIds = "";
            EnfordProductArea area = null;
            if (role != null) {
                areaIds = role.getAreaId();
                area = areaService.getAreaDeptTreeByKeywords(areaIds, keyword, page, pageSize);
            } else if (roleId == 0) {
                areaId = 0;
                area = areaService.getAreaDeptTreeByKeyword(areaId, keyword, page, pageSize);
            }
            if (area != null) {
                deptList = area.getChildren();
                for (EnfordProductDepartment dept : deptList) {
                    Map<String, Object> param = new HashMap<String, Object>();
                    param.put("dept", dept);
                    param.put("state", state);
                    List<EnfordMarketResearch> resList = marketService
                            .getMarketResearchByParam2(param);
                    dept.setChildren(resList);
                }
            }
            if (areaId == 0) {
                count = areaService.countByKeyword(null, null);
            } else {
                count = areaService.countByKeyword(areaId, keyword);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        result.put("rows", deptList);
        result.put("total", count);
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(result));
    }

    @RequestMapping("/area/list")
    public String getAreaList(HttpServletRequest req, HttpServletResponse resp) {
        return "/area/area";
    }

    @RequestMapping("/area/get2")
    public void getAreaList2(HttpServletRequest req, HttpServletResponse resp,
                             @RequestParam("page") int page,
                             @RequestParam("rows") int pageSize) {
        List<EnfordProductArea> areaList = null;
        JSONObject result = new JSONObject();
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            //设置页数信息
            param.put("page", (page - 1) * pageSize);
            param.put("pageSize", pageSize);
            String areaName = req.getParameter("name");
            System.out.println("areaName=" + areaName);
            if (areaName != null && !areaName.trim().equals("")) {
                param.put("name", URLDecoder.decode(areaName, "UTF-8"));
            }
            areaList = areaService.getArea(param);
            result.put("rows", areaList);
            result.put("total", areaList.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(result));
    }

    @RequestMapping("/area/get3")
    public void getAreaList2(HttpServletRequest req, HttpServletResponse resp) {
        List<EnfordProductArea> areaList = null;
        JSONObject result = new JSONObject();
        try {
            areaList = areaService.getArea(null);
            EnfordProductArea area = new EnfordProductArea();
            area.setId(0);
            area.setName("全部区域");
            areaList.add(area);
            result.put("rows", areaList);
            result.put("total", areaList.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(result));
    }

    /**
     * 获取已经绑定在该区域下的部门列表
     *
     * @param req
     * @param resp
     * @param areaId
     */
    @RequestMapping("/area/dept/bindList")
    public void getAreaDeptList(HttpServletRequest req, HttpServletResponse resp,
                             @RequestParam("areaId") int areaId) {
        EnfordProductArea area = null;
        JSONObject result = new JSONObject();
        try {
            area = areaService.getAreaDeptTree(areaId);
            result.put("rows", area.getChildren());
            result.put("total", area.getChildren().size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(result));
    }

    /**
     * 获取尚未绑定在该区域下的部门列表
     * @param req
     * @param resp
     * @param areaId
     */
    @RequestMapping("/area/dept/unbindList")
    public void unbindAreaDeptList(HttpServletRequest req, HttpServletResponse resp,
                                   @RequestParam("areaId") int areaId) {
        List<EnfordProductDepartment> deptList = null;
        JSONObject result = new JSONObject();
        try {
            deptList = deptService.getNotInArea(areaId);
            result.put("rows", deptList);
            result.put("total", deptList.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(result));
    }

    /**
     * 执行绑定操作
     *
     * @param req
     * @param resp
     * @param deptId
     */
    @RequestMapping("/area/dept/bind")
    public void unbindAreaDept(HttpServletRequest req, HttpServletResponse resp,
                               @RequestParam("deptId") int deptId,
                               @RequestParam("areaId") int areaId) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            EnfordProductDepartment dept = deptService.getDepartmentByDeptId(deptId);
            dept.setAreaId(areaId);
            int ret = deptService.updateDept(dept);
            ResponseUtil.checkResult(ret, "绑定区域", respBody);
        } catch (Exception ex) {
            ex.printStackTrace();
            ResponseUtil.sendFailed("绑定区域出错:" + ex.getMessage(), respBody);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    /**
     * 执行解绑操作
     *
     * @param req
     * @param resp
     * @param deptId
     */
    @RequestMapping("/area/dept/unbind")
    public void unbindAreaDept(HttpServletRequest req, HttpServletResponse resp,
                                @RequestParam("deptId") int deptId) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            EnfordProductDepartment dept = deptService.getDepartmentByDeptId(deptId);
            dept.setAreaId(-1);
            int ret = deptService.updateDept(dept);
            ResponseUtil.checkResult(ret, "解绑区域", respBody);
        } catch (Exception ex) {
            ex.printStackTrace();
            ResponseUtil.sendFailed("解绑区域出错:" + ex.getMessage(), respBody);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    @RequestMapping("/area/add")
    public void add(HttpServletRequest req, HttpServletResponse resp, EnfordProductArea area) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            int ret = areaService.addArea(area);
            ResponseUtil.checkResult(ret, "添加区域", respBody);
        } catch (Exception ex) {
            ex.printStackTrace();
            respBody.setCode(Consts.FAILED);
            respBody.setMsg("添加区域出错:" + ex.getMessage());
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    @RequestMapping("/area/update")
    public void update(HttpServletRequest req, HttpServletResponse resp, EnfordProductArea area) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            int ret = areaService.updateArea(area);
            ResponseUtil.checkResult(ret, "更新区域", respBody);
        } catch (Exception ex) {
            ex.printStackTrace();
            respBody.setCode(Consts.FAILED);
            respBody.setMsg("更新区域出错:" + ex.getMessage());
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    @RequestMapping("/area/delete")
    public void delete(HttpServletRequest req, HttpServletResponse resp, int id) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            //判断该区域下是否有门店,如果有问题,则不允许删除
            EnfordProductArea area = areaService.getAreaDeptTree(id);
            if (area.getChildren().size() > 0) {
                ResponseUtil.sendFailed("该区域下已有" + area.getChildren().size() + "家门店,请删除所有门店后重试!", respBody);
            } else {
                int ret = areaService.deleteArea(id);
                ResponseUtil.checkResult(ret, "删除区域", respBody);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ResponseUtil.sendFailed("删除区域出错:" + ex.getMessage(), respBody);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    /**
     * 三期功能:获取当前登录用户下的所有区域
     * @param req
     * @return
     */
    private List<Integer> getAreaIds(HttpServletRequest req) {
        EnfordSystemUser user = (EnfordSystemUser) req.getSession().getAttribute("user");
        int roleId = user.getRoleId();
        EnfordSystemRole role = roleService.getRole(roleId);
        String areaIds = role.getAreaId();
        List<Integer> areaIdsList = new ArrayList<Integer>();
        if (areaIds != null) {
            if (areaIds.contains(",")) {
                String[] areaIdsStr = areaIds.split(",");
                for (String areaId : areaIdsStr) {
                    areaIdsList.add(Integer.valueOf(areaId));
                }
            } else {
                areaIdsList.add(Integer.valueOf(areaIds));
            }
        }
        return areaIdsList;
    }

    private void check(long start, long end, String msg) {
        double during = (double) (end - start) / 1000;
        System.out.println("[" + msg + "]过程花费时间:" + during + "秒");
    }

    /**
     * 三期功能:区域统计功能
     * @param req
     * @param resp
     */
    @RequestMapping("/area/stats")
    public void getAreaStats(HttpServletRequest req, HttpServletResponse resp) {
        List<EnfordProductArea> areaList = null;
        String year = req.getParameter("year");
        String month = req.getParameter("month");
        System.out.println("year=" + year + ",month=" + month);
        JSONObject result = new JSONObject();
        try {
            List<Integer> areaIds = getAreaIds(req);
            if (!StringUtil.isEmpty(year) && !StringUtil.isEmpty(month)) {
                areaList = areaService.getAreaStatsByYearAndMonth(areaIds, year, month);
            } else {
                areaList = areaService.getAreaStats(areaIds);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        result.put("rows", areaList);
        result.put("total", areaList.size());
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(result));
    }

    /**
     * 三期功能:部门统计功能
     * @param req
     * @param resp
     */
    @RequestMapping("/area/stats/dept")
    public void getDeptStats(HttpServletRequest req, HttpServletResponse resp,
                             @RequestParam("areaId") int areaId) {
        JSONObject result = new JSONObject();
        List<EnfordProductDepartment> deptList = null;
        try {
            deptList = areaService.getDeptStats(areaId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        result.put("rows", deptList);
        result.put("total", deptList.size());
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(result));
    }

    /**
     * 三期功能:分类统计功能
     * @param req
     * @param resp
     */
    @RequestMapping("/area/stats/cat")
    public void getCatStats(HttpServletRequest req, HttpServletResponse resp,
                             @RequestParam("areaId") int areaId) {
        JSONObject result = new JSONObject();
        List<EnfordProductCategory> catList = null;
        try {
            catList = areaService.getCatStats(areaId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        result.put("rows", catList);
        result.put("total", catList.size());
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(result));
    }
}
