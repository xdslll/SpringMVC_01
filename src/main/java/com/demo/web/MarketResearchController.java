package com.demo.web;

import com.alibaba.fastjson.JSONObject;
import com.demo.model.*;
import com.demo.service.*;
import com.demo.util.*;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/2/6
 */
@Controller
public class MarketResearchController implements Consts {

    private static final Logger logger = Logger.getLogger(MarketResearchController.class);

    @Resource
    MarketResearchSerivce marketService;

    @Resource
    UploadService uploadService;

    @Resource
    DeptService deptService;

    @Resource
    CommodityService commodityService;

    @Resource
    CommodityPriceService priceService;

    @RequestMapping("/market_research/manage")
    public String marketResearchList() {
        return "/market_research/market_research";
    }

    @RequestMapping("/market_research/get")
    public void getMarketResearch(HttpServletRequest req, HttpServletResponse resp,
                             @RequestParam("page") int page,
                             @RequestParam("rows") int pageSize) {
        List<EnfordMarketResearch> marketResearchList = null;
        int total = 0;
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            //设置页数信息
            param.put("page", (page - 1) * pageSize);
            param.put("pageSize", pageSize);
            //查询市调清单信息
            marketResearchList = marketService.getMarketResearchByParam(param);
            //查询总页数
            total = marketService.countMarketResearchByParam(param);
        }
        catch (Exception ex) {
            logger.error("exception occurred when getMarketResearch:" + ex);
        }
        JSONObject result = new JSONObject();
        result.put("rows", marketResearchList);
        result.put("total", total);
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(result));
    }

    @RequestMapping("/market_research/publish")
    public void publishMarketResearch(HttpServletRequest req, HttpServletResponse resp,
                                      EnfordMarketResearch research) {
        RespBody<EnfordMarketResearch> respBody = new RespBody<EnfordMarketResearch>();
        try {
            //市调结束时间不能小于开始时间
            Date startDt = research.getStartDt();
            Date endDt = research.getEndDt();
            if (startDt.compareTo(endDt) > 0) {
                respBody.setMsg("结束时间需要大于等于开始时间!");
                respBody.setCode(FAILED);
            } else {
                research.setState(RESEARCH_STATE_HAVE_PUBLISHED);
                //更新市调清单信息
                marketService.updateResearch(research);
                //创建发布信息
                marketService.publicResearch(research);
                respBody.setCode(SUCCESS);
                respBody.setMsg("发布成功!");
            }
        } catch (Exception ex) {
            logger.error("exception occurred when publishMarketResearch:" + ex);
            respBody.setMsg("发布失败:" + ex.getMessage());
            respBody.setCode(FAILED);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    @RequestMapping("/market_research/callback")
    public void callbackMarketResearch(HttpServletRequest req, HttpServletResponse resp,
                                       @RequestParam("data") String data) {
        RespBody<EnfordMarketResearch> respBody = new RespBody<EnfordMarketResearch>();
        try {
            EnfordMarketResearch research = FastJSONHelper.deserialize(data, EnfordMarketResearch.class);
            if (research.getState() == RESEARCH_STATE_HAVE_STARTED) {
                respBody.setMsg("市调已经开始,无法撤回!");
                respBody.setCode(FAILED);
            } else if (research.getState() == RESEARCH_STATE_NOT_PUBLISH) {
                respBody.setMsg("市调还未发布,无需撤回!");
                respBody.setCode(FAILED);
            } else if (research.getState() == RESEARCH_STATE_HAVE_FINISHED) {
                respBody.setMsg("市调已经结束,无法撤回!");
                respBody.setCode(FAILED);
            } else {
                research.setState(RESEARCH_STATE_NOT_PUBLISH);
                research.setStartDt(null);
                research.setEndDt(null);
                //更新市调信息
                marketService.updateResearch(research);
                //撤回所有市调信息
                marketService.callbackResearch(research);
                respBody.setCode(SUCCESS);
                respBody.setMsg("撤回成功!");
            }
        } catch (Exception ex) {
            logger.error("exception occurred when callbackMarketResearch:" + ex);
            respBody.setMsg("撤回失败:" + ex.getMessage());
            respBody.setCode(FAILED);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    @RequestMapping("/market_research/export")
    public void exportMarketResearch(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam("resId") int resId,
                                  @RequestParam("deptId") int deptId) {
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            //获取市调清单
            EnfordMarketResearch research = marketService.getMarketResearchById(resId);
            //获取导入文件id
            int importId = research.getImportId();
            //获取市调清单的Excel文件
            EnfordProductImportHistory importHistory = uploadService.getImportHistoryById(importId);
            //根据部门id获取部门信息
            EnfordProductDepartment department = deptService.getDepartmentByDeptId(deptId);
            File originFile = Config.getUploadFile(importHistory);
            System.out.println("origin file: " + originFile.getAbsolutePath());
            //获取导出文件夹
            String exportFilePath = Config.getExportPath();
            //根据市调清单名称创建专属的文件夹
            String researchName = research.getName();
            File exportDir = new File(exportFilePath + researchName);
            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }
            //根据部门id生成唯一的导出文件名
            String exportFileName = researchName + "_" + department.getName() + "." + importHistory.getFileType();
            File exportFile = new File(exportDir, exportFileName);
            if (exportFile.exists()) {
                exportFile.delete();
            }
            System.out.println(exportFile.getAbsolutePath());
            //将市调清单文件复制到导出文件夹
            fis = new FileInputStream(originFile);
            fos = new FileOutputStream(exportFile);
            /*byte[] data = new byte[1024];
            int len = 0;
            while (-1 != (len = fis.read(data, 0, data.length))) {
                fos.write(data, 0, len);
            }
            fos.flush();*/

            //回写Excel数据
            Workbook book = ExcelUtil.read(fis);
            Sheet sheet = book.getSheetAt(0);
            //获取总行数
            int rowNum = sheet.getLastRowNum() + 1;
            //去除标题和列名
            int firstRow = sheet.getFirstRowNum() + 2;
            //解析市调数据,回写价格数据
            for (int index = firstRow; index < rowNum; index++) {
                //获取商品信息
                Row row = sheet.getRow(index);
                //获取商品四级分类编码
                Cell categoryCodeCell = ExcelUtil.getCell(row, 2);
                //获取商品名称
                Cell codNameCell = ExcelUtil.getCell(row, 6);
                //获取商品条形码
                Cell barcodeCell = ExcelUtil.getCell(row, 9);
                //获取竞争门店促销价
                Cell promptPriceCell = ExcelUtil.getCell(row, 19);
                //获取竞争门店零售价
                Cell retailPriceCell = ExcelUtil.getCell(row, 18);

                int categoryCode = Integer.parseInt(categoryCodeCell.getStringCellValue());
                String barcode = barcodeCell.getStringCellValue();
                String pName = codNameCell.getStringCellValue();
                //根据条形码,分类码和品名获取商品信息
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("categoryCode", categoryCode);
                param.put("barCode", barcode);
                param.put("pName", pName);
                List<EnfordProductCommodity> commodityList = commodityService.selectByParam(param);
                if (commodityList != null && commodityList.size() > 0) {
                    EnfordProductCommodity commodity = commodityList.get(0);
                    //根据商品信息,市调清单id,所属部门id查询商品价格
                    param.clear();
                    param.put("comId", commodity.getId());
                    param.put("resId", resId);
                    param.put("deptId", deptId);
                    List<EnfordProductPrice> priceList = priceService.getPrice(param);
                    if (priceList != null && priceList.size() > 0) {
                        EnfordProductPrice price = priceList.get(0);
                        promptPriceCell.setCellValue(price.getPromptPrice());
                        retailPriceCell.setCellValue(price.getRetailPrice());
                    }
                }
            }
            book.write(fos);

            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + Config.getFileName(importHistory));
            response.setHeader("Content-Length", String.valueOf(exportFile.length()));
            in = new BufferedInputStream(new FileInputStream(exportFile));
            out = new BufferedOutputStream(response.getOutputStream());
            byte[] data = new byte[1024];
            int len = 0;
            while (-1 != (len = in.read(data, 0, data.length)))
            {
                out.write(data, 0, len);
            }
            out.flush();
        }
        catch (Exception ex) {
            logger.error("exception occurred when exportMarketResearch: " + ex);
            ex.printStackTrace();
        }
        finally {
            try {
                in.close();
            }
            catch (Exception ex) {
                logger.error("exception occurred when exportMarketResearch: " + ex);
            }
            try {
                out.close();
            }
            catch (Exception ex) {
                logger.error("exception occurred when exportMarketResearch: " + ex);
            }
            try {
                fis.close();
            }
            catch (Exception ex) {
                logger.error("exception occurred when downloadTemplate: " + ex);
            }
            try {
                fos.close();
            }
            catch (Exception ex) {
                logger.error("exception occurred when downloadTemplate: " + ex);
            }
        }
    }
}
