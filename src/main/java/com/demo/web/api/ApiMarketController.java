package com.demo.web.api;

import com.demo.model.*;
import com.demo.model.EnfordApiMarketResearch;
import com.demo.model.EnfordProductCategory;
import com.demo.model.EnfordProductCommodity;
import com.demo.model.EnfordProductPrice;
import com.demo.service.CommodityPriceService;
import com.demo.service.api.ApiMarketResearchService;
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
import java.util.List;

/**
 * @author xiads
 * @date 16/2/11
 */
@Controller
public class ApiMarketController implements Consts {

    private static final Logger logger = Logger.getLogger(ApiMarketController.class);

    @Resource
    ApiMarketResearchService apiMarketService;

    @Resource
    CommodityPriceService priceService;

    /**
     * 获取门店对应的竞争对手和市调清单信息
     *
     * @param req       HTTP请求对象
     * @param resp      HTTP响应对象
     * @param exeId     门店id
     */
    @RequestMapping("/api/market/dept/get")
    public void getResearchDept(HttpServletRequest req, HttpServletResponse resp,
                                  @RequestParam("exeId") String exeId) {
        RespBody<EnfordApiMarketResearch> respBody = new RespBody<EnfordApiMarketResearch>();
        try {
            List<EnfordApiMarketResearch> researchDeptList = apiMarketService
                    .getMarketResearch(Integer.parseInt(exeId));
            if (researchDeptList != null) {
                respBody.setCode(SUCCESS);
                respBody.setMsg("获取市调清单成功!");
                respBody.setDatas(researchDeptList);
                respBody.setTotalnum(researchDeptList.size());
            } else {
                respBody.setCode(FAILED);
                respBody.setMsg("获取市调清单失败!");
            }
        } catch (Exception ex) {
            respBody.setCode(FAILED);
            respBody.setMsg("获取市调清单失败:" + ex.getMessage());
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    /**
     * 获取分类下的商品
     *
     * @param req
     * @param resp
     * @param resId
     * @param deptId
     * @param code
     */
    @RequestMapping("/api/category/cod/get")
    public void getResearchCommodity(HttpServletRequest req, HttpServletResponse resp,
                                     @RequestParam("resId") String resId,
                                     @RequestParam("deptId") String deptId,
                                     @RequestParam("code") String code) {
        RespBody<EnfordProductCategory> respBody = new RespBody<EnfordProductCategory>();
        try {
            List<EnfordProductCategory> researchCommodityList = apiMarketService
                    .getResearchCategory(Integer.parseInt(resId),
                            Integer.parseInt(deptId),
                            Integer.parseInt(code));
            if (researchCommodityList != null) {
                respBody.setCode(SUCCESS);
                respBody.setMsg("获取市调商品成功!");
                respBody.setDatas(researchCommodityList);
                respBody.setTotalnum(researchCommodityList.size());
            } else {
                respBody.setCode(FAILED);
                respBody.setMsg("获取市调商品失败!");
            }
        } catch (Exception ex) {
            respBody.setCode(FAILED);
            respBody.setMsg("获取市调商品失败:" + ex.getMessage());
        }
        /*SimplePropertyPreFilter filter = new SimplePropertyPreFilter(EnfordProductCommodity.class,
                "pName", "pSize", "unit", "barCode", "id", "price");*/
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    @RequestMapping("/api/price/add")
    public void addCommodityPrice(HttpServletRequest req, HttpServletResponse resp,
                                  @RequestParam("json") String json) {
        RespBody respBody = new RespBody();
        try {
            EnfordProductPrice price = FastJSONHelper.deserialize(json, EnfordProductPrice.class);
            int count = priceService.addPrice(price);
            if (count > 0) {
                respBody.setCode(SUCCESS);
                respBody.setMsg("新增价格成功");
                priceService.addPriceToSQLServer(price);
            } else {
                respBody.setCode(FAILED);
                respBody.setMsg("新增价格失败");
            }
        } catch (Exception ex) {
            respBody.setCode(FAILED);
            respBody.setMsg("新增价格失败:" + ex.getMessage());
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    @RequestMapping("/api/price/update")
    public void updateCommodityPrice(HttpServletRequest req, HttpServletResponse resp,
                                     @RequestParam("json") String json) {
        RespBody respBody = new RespBody();
        try {
            EnfordProductPrice price = FastJSONHelper.deserialize(json, EnfordProductPrice.class);
            int count = priceService.updatePrice(price);
            if (count > 0) {
                respBody.setCode(SUCCESS);
                respBody.setMsg("修改价格成功");
                priceService.addPriceToSQLServer(price);
            } else {
                respBody.setCode(FAILED);
                respBody.setMsg("修改价格失败");
            }
        } catch (Exception ex) {
            respBody.setCode(FAILED);
            respBody.setMsg("修改价格失败:" + ex.getMessage());
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    @RequestMapping("/api/cod/get")
    public void getCommodityByBarcode(HttpServletRequest req, HttpServletResponse resp,
                                     @RequestParam("resId") int resId,
                                     @RequestParam("deptId") int deptId,
                                     @RequestParam("barcode") String barcode) {
        RespBody<EnfordProductCommodity> respBody = new RespBody<EnfordProductCommodity>();
        try {
            String pageStr = req.getParameter("page");
            String pageSizeStr = req.getParameter("pageSize");
            int page = 0;
            int pageSize = 10;
            if (pageStr != null) {
                page = Integer.parseInt(pageStr) - 1;
            }
            if (pageSizeStr != null) {
                pageSize = Integer.parseInt(pageSizeStr);
            }
            List<EnfordProductCommodity> commodityList = apiMarketService
                    .getCommodityByBarcode(resId, deptId, barcode, page * pageSize, pageSize);
            int total = apiMarketService
                    .countCommodityByBarcode(resId, deptId, barcode);
            if (commodityList != null) {
                respBody.setCode(SUCCESS);
                respBody.setMsg("获取市调商品成功!");
                respBody.setDatas(commodityList);
                respBody.setTotalnum(total);
            } else {
                respBody.setCode(FAILED);
                respBody.setMsg("获取市调商品失败!");
            }
        } catch (Exception ex) {
            respBody.setCode(FAILED);
            respBody.setMsg("获取市调商品失败:" + ex.getMessage());
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    @RequestMapping("/api/location/add")
    public void addResearchLocation(HttpServletRequest req, HttpServletResponse resp,
                                  @RequestParam("json") String json) {
        System.out.println("json=" + json);
        RespBody respBody = new RespBody();
        try {
            EnfordMarketLocation location = FastJSONHelper.deserialize(json, EnfordMarketLocation.class);
            location.setAddress(URLDecoder.decode(location.getAddress(), "utf-8"));
            int count = apiMarketService.addLocation(location);
            if (count > 0) {
                respBody.setCode(SUCCESS);
                respBody.setMsg("新增签到成功");
            } else {
                respBody.setCode(FAILED);
                respBody.setMsg("新增签到失败");
            }
        } catch (Exception ex) {
            respBody.setCode(FAILED);
            respBody.setMsg("新增签到失败:" + ex.getMessage());
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    @RequestMapping("/api/location/get")
    public void getCommodityByBarcode(HttpServletRequest req, HttpServletResponse resp,
                                      @RequestParam("resId") int resId,
                                      @RequestParam("userId") int userId) {
        RespBody<EnfordMarketLocation> respBody = new RespBody<EnfordMarketLocation>();
        try {
            String pageStr = req.getParameter("page");
            String pageSizeStr = req.getParameter("pageSize");
            int page = -1;
            int pageSize = -1;
            if (pageStr != null) {
                page = Integer.parseInt(pageStr) - 1;
            }
            if (pageSizeStr != null) {
                pageSize = Integer.parseInt(pageSizeStr);
            }
            List<EnfordMarketLocation> locationList = apiMarketService
                    .getLocation(resId, userId, page * pageSize, pageSize);
            int total = apiMarketService
                    .countLocation(resId, userId);
            if (locationList != null) {
                respBody.setCode(SUCCESS);
                respBody.setMsg("获取签到数据成功!");
                respBody.setDatas(locationList);
                respBody.setTotalnum(total);
            } else {
                respBody.setCode(FAILED);
                respBody.setMsg("获取签到数据失败!");
            }
        } catch (Exception ex) {
            respBody.setCode(FAILED);
            respBody.setMsg("获取签到数据失败:" + ex.getMessage());
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }
}
