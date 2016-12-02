package com.demo.web.sg;

import com.alibaba.fastjson.JSONObject;
import com.demo.model.*;
import com.demo.service.*;
import com.demo.sync.SyncHandler;
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
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.*;
import java.text.SimpleDateFormat;
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

    @Resource
    AreaService areaService;

    //显示进行中的市调清单
    @RequestMapping("/market_research/manage")
    public String marketResearchList() {
        return "/market_research/market_research";
    }

    //显示已经结束的市调清单
    @RequestMapping("/market_research/manage2")
    public String marketResearchList2() {
        return "/market_research/market_research2";
    }

    @RequestMapping("/market_research/export2")
    public String marketResearchExport() {
        return "/market_research/market_research_export";
    }

    @RequestMapping("/market_research/get")
    public void getMarketResearch(HttpServletRequest req, HttpServletResponse resp,
                             @RequestParam("page") int page,
                             @RequestParam("rows") int pageSize) {
        //市调清单的状态
        int status = -1;
        if (req.getParameter("status") != null) {
            status = Integer.parseInt(req.getParameter("status"));
        }
        String billNumber = null;
        if (req.getParameter("billNumber") != null) {
            billNumber = req.getParameter("billNumber");
        }
        List<EnfordMarketResearch> marketResearchList = null;
        int total = 0;
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            //设置页数信息
            param.put("page", (page - 1) * pageSize);
            param.put("pageSize", pageSize);
            //如果status为0,则只显示进行中的市调清单,如果status为1,则只显示已结束的市调清单,如果status为2,则显示全部的市调清单
            param.put("status", status);
            if (billNumber != null && !billNumber.trim().equals("")) {
                param.put("billNumber", billNumber);
            }
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

    @RequestMapping("/market_research/export_area")
    public void exportArea(HttpServletRequest req, HttpServletResponse resp,
                           @RequestParam("resId") int resId) {
        List<EnfordProductDepartment> deptList = null;
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            //获取参数
            String areaName = new String(req.getParameter("areaName").getBytes("ISO-8859-1"), "UTF-8");
            System.out.println("resId=" + resId);
            System.out.println("areaName=" + areaName);
            //获取所有的部门列表
            deptList = areaService.getAreaDept(resId, areaName);
            System.out.println("deptList.size()=" + deptList.size());
            //在上传目录下新建一个区域目录
            String dirPath = Config.getExportPath();
            //查询是否存在区域目录,如存在,则直接删除
            dirPath = dirPath + areaName + "/";
            System.out.println("dir path=" + dirPath);
            File dir = new File(dirPath);
            if (dir.exists()) {
                File[] files = dir.listFiles();
                for (File f : files) {
                    f.delete();
                }
                dir.delete();
            }
            //重新创建文件夹
            dir.mkdirs();

            //将市调清单复制到文件夹下
            //获取市调清单
            EnfordMarketResearch research = marketService.getMarketResearchById(resId);
            //获取导入文件id
            int importId = research.getImportId();
            //获取市调清单的Excel文件
            EnfordProductImportHistory importHistory = uploadService.getImportHistoryById(importId);
            File originFile = Config.getUploadFile(importHistory);
            System.out.println("origin file: " + originFile.getAbsolutePath());
            //获取市调清单名称
            String researchName = research.getName();
            //根据部门id生成唯一的导出文件名
            String exportFileName = researchName + "_" + System.currentTimeMillis() + "." + importHistory.getFileType();
            File exportFile = new File(dir, exportFileName);
            if (exportFile.exists()) {
                exportFile.delete();
            }
            System.out.println(exportFile.getAbsolutePath());
            //将市调清单文件复制到导出文件夹
            fis = new FileInputStream(originFile);
            fos = new FileOutputStream(exportFile);

            //依次导出所有文件
            Workbook book = ExcelUtil.read(fis);
            for (int i = 0; i < deptList.size(); i++) {
            //for (int i = 0; i < 2; i++) {
                System.out.println("正在执行第" + (i + 1) + "次导出");
                exportExcel(deptList.get(i), resId, book, i + 1);
                System.out.println("第" + (i + 1) + "次导出执行完毕");
            }
            book.write(fos);
            resp.setContentType("text/html;charset=UTF-8");
            req.setCharacterEncoding("UTF-8");
            resp.setContentType("application/x-excel");
            //resp.setHeader("Content-Disposition", "attachment; filename=" + Config.getFileName(importHistory));
            resp.setHeader("Content-Disposition", "attachment; filename=" + exportFileName);
            resp.setHeader("Content-Length", String.valueOf(exportFile.length()));
            in = new BufferedInputStream(new FileInputStream(exportFile));
            out = new BufferedOutputStream(resp.getOutputStream());
            byte[] data = new byte[1024];
            int len = 0;
            while (-1 != (len = in.read(data, 0, data.length)))
            {
                out.write(data, 0, len);
            }
            out.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                in.close();
            }
            catch (Exception ex) {
                logger.error("exception occurred when exportExcel: " + ex);
            }
            try {
                out.close();
            }
            catch (Exception ex) {
                logger.error("exception occurred when exportExcel: " + ex);
            }
            try {
                fis.close();
            }
            catch (Exception ex) {
                logger.error("exception occurred when exportExcel: " + ex);
            }
            try {
                fos.close();
            }
            catch (Exception ex) {
                logger.error("exception occurred when exportExcel: " + ex);
            }
        }
    }

    /*@RequestMapping("/market_research/export_area")
    public void exportArea(HttpServletRequest req, HttpServletResponse resp,
                           @RequestParam("resId") int resId) {
        List<EnfordProductDepartment> deptList = null;
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            //获取参数
            String areaName = new String(req.getParameter("areaName").getBytes("ISO-8859-1"), "UTF-8");
            System.out.println("resId=" + resId);
            System.out.println("areaName=" + areaName);
            //获取所有的部门列表
            deptList = areaService.getAreaDept(resId, areaName);
            System.out.println("deptList.size()=" + deptList.size());
            //在上传目录下新建一个区域目录
            String dirPath = Config.getExportPath();
            //查询是否存在区域目录,如存在,则直接删除
            dirPath = dirPath + areaName + "/";
            System.out.println("dir path=" + dirPath);
            File dir = new File(dirPath);
            if (dir.exists()) {
                File[] files = dir.listFiles();
                for (File f : files) {
                    f.delete();
                }
                dir.delete();
            }
            //重新创建文件夹
            dir.mkdirs();
            //依次导出所有文件
            for (int i = 0; i < deptList.size(); i++) {
                System.out.println("正在执行第" + (i + 1) + "次导出");
                exportExcel(deptList.get(i), dir, resId);
                System.out.println("第" + (i + 1) + "次导出执行完毕");
            }
            System.out.println("开始压缩...");
            File zipFile = new File(Config.getExportPath(), areaName + ".zip");

            if (zipFile.exists()) {
                zipFile.delete();
            }
            //压缩文件夹
            boolean r = FileHelper.fileToZip(dir.getPath(), Config.getExportPath(), areaName);
            if (r) {
                resp.setContentType("text/html;charset=UTF-8");
                req.setCharacterEncoding("UTF-8");
                resp.setContentType("application/zip");
                resp.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(zipFile.getName(), "UTF-8"));
                resp.setHeader("Content-Length", String.valueOf(zipFile.length()));
                in = new BufferedInputStream(new FileInputStream(zipFile));
                out = new BufferedOutputStream(resp.getOutputStream());
                byte[] data = new byte[1024];
                int len = 0;
                while (-1 != (len = in.read(data, 0, data.length))) {
                    out.write(data, 0, len);
                }
                out.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                in.close();
            }
            catch (Exception ex) {
                logger.error("exception occurred when exportExcel: " + ex);
            }
            try {
                out.close();
            }
            catch (Exception ex) {
                logger.error("exception occurred when exportExcel: " + ex);
            }
        }
    }*/

    private void exportExcel(EnfordProductDepartment department, int resId,
                             Workbook book, int position) {
        try {
            //回写Excel数据
            Sheet sheet = book.getSheetAt(0);
            //获取总行数
            int rowNum = sheet.getLastRowNum() + 1;
            //去除标题和列名
            int firstRow = sheet.getFirstRowNum() + 2;
            int number = rowNum - firstRow;
            int lastNum = rowNum + 1 + number;

            //解析市调数据,回写价格数据
            for (int index = rowNum + 1; index < lastNum; index++, firstRow++) {
                System.out.println("position=" + position + ",index=" + index);
                //获取商品信息
                Row row = sheet.createRow(index);
                Row orgRow = sheet.getRow(firstRow);
                //获取商品四级分类编码
                Cell categoryCodeCell = ExcelUtil.getCell(orgRow, 2);
                //获取商品名称
                Cell codNameCell = ExcelUtil.getCell(orgRow, 6);
                //获取商品条形码
                Cell barcodeCell = ExcelUtil.getCell(orgRow, 9);
                //获取竞争门店促销价
                Cell promptPriceCell = ExcelUtil.createCell(row, 19);
                //获取竞争门店零售价
                Cell retailPriceCell = ExcelUtil.createCell(row, 18);

                Cell deptName = ExcelUtil.createCell(row, 21);

                ExcelUtil.copyCell(orgRow, row, 20);

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
                    param.put("deptId", department.getId());
                    List<EnfordProductPrice> priceList = priceService.getPrice(param);
                    if (priceList != null && priceList.size() > 0) {
                        EnfordProductPrice price = priceList.get(0);
                        if (price != null && price.getPromptPrice() != null) {
                            promptPriceCell.setCellValue(price.getPromptPrice());
                        }
                        if (price != null && price.getRetailPrice() != null) {
                            retailPriceCell.setCellValue(price.getRetailPrice());
                        }
                    }
                }
                deptName.setCellValue(department.getName());
            }
        }
        catch (Exception ex) {
            logger.error("exception occurred when exportExcel: " + ex);
            ex.printStackTrace();
        }
    }

    /**
     * 二期新增功能
     * 导出百年系统需要的txt文本
     *
     * @param request
     * @param response
     * @param resId
     */
    @RequestMapping("/market_research/export")
    public void exportMarketResearch(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam("resId") int resId) {
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            //获取市调清单
            EnfordMarketResearch research = marketService.getMarketResearchById(resId);
            //获取导出文件夹
            String exportFilePath = Config.getExportPath();
            //根据部门id获取部门信息
            EnfordProductDepartment department;
            String deptIdStr = request.getParameter("deptId");
            int deptId;
            Integer compId = null;
            if (!StringUtil.isEmpty(deptIdStr)) {
                deptId = Integer.parseInt(deptIdStr);
                department = deptService.getDepartmentByDeptId(deptId);
            } else {
                String deptCode = request.getParameter("deptCode");
                department = deptService.getDepartmentByDeptCode(deptCode);
                deptId = department.getId();
            }
            if (department != null) {
                compId = department.getCompId();
            }
            //根据市调清单名称创建专属的文件夹
            String researchName = research.getName();
            File exportDir = new File(exportFilePath + researchName);
            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            //根据部门id生成唯一的导出文件名
            String exportFileName = research.getId() + "_" +
                    df.format(research.getStartDt()) + "_" +
                    df.format(research.getEndDt()) + ".txt";
            File exportFile = new File(exportDir, exportFileName);
            if (exportFile.exists()) {
                exportFile.delete();
            }
            System.out.println(exportFile.getAbsolutePath());
            fos = new FileOutputStream(exportFile);
            //获取市调清单下的所有商品信息
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("resId", resId);
            List<EnfordMarketResearchCommodity> commodityList = marketService.getResearchCodByParam(param);
            System.out.println(research.toString());
            String mrBeginDate;
            String mrEndDate;
            if (research.getMrBeginDate() == null) {
                mrBeginDate = df.format(research.getStartDt());
            } else {
                mrBeginDate = df.format(research.getMrBeginDate());
            }
            if (research.getMrEndDate() == null) {
                mrEndDate = df.format(research.getEndDt());
            } else {
                mrEndDate = df.format(research.getMrEndDate());
            }
            if (commodityList != null) {
                for (int i = 0; i < commodityList.size(); i++) {
                    EnfordMarketResearchCommodity commodity = commodityList.get(i);
                    //循环读取所有商品信息,获取商品的价格
                    //查询价格并插入价格
                    param.clear();
                    param.put("comId", commodity.getCodId());
                    param.put("resId", commodity.getResId());
                    //根据竞争对手ID查询价格信息
                    if (compId != null) {
                        param.put("compId", compId);
                    } else {
                        //如果没有竞争对手信息,则根据部门ID查询价格信息
                        param.put("deptId", deptId);
                    }
                    List<EnfordProductPrice> priceList = priceService.getPrice(param);
                    if (priceList != null && priceList.size() > 0) {
                        EnfordProductPrice price = priceList.get(0);
                        if (price != null && price.getPromptPrice() != null) {
                            commodity.setPurchasePrice(price.getPromptPrice());
                        } else {
                            commodity.setPurchasePrice(0.0f);
                        }
                        if (price != null && price.getRetailPrice() != null) {
                            commodity.setRetailPrice(price.getRetailPrice());
                        } else {
                            commodity.setRetailPrice(0.0f);
                        }
                    } else {
                        commodity.setPurchasePrice(0.0f);
                        commodity.setRetailPrice(0.0f);
                    }
                    if (commodity.getMrBeginDate() != null) {
                        mrBeginDate = df.format(commodity.getMrBeginDate());
                    }
                    if (commodity.getMrEndDate() != null) {
                        mrEndDate = df.format(commodity.getMrEndDate());
                    }
                    System.out.println("commodity.getCodCode()=" + commodity.getCodCode());
                    System.out.println("commodity.getRetailPrice()=" + commodity.getRetailPrice());
                    System.out.println("commodity.getPurchasePrice()=" + commodity.getPurchasePrice());
                    System.out.println("commodity.getMrBeginDate()=" + commodity.getMrBeginDate());
                    System.out.println("commodity.getMrEndDate()=" + commodity.getMrEndDate());
                    System.out.println("commodity.getRemark()=" + commodity.getRemark());
                    System.out.println("resId=" + resId);
                    System.out.println("research.toString()=" + research.toString());
                    //循环写入商品信息
                    String str = commodity.getCodCode() +
                            //"," + commodity.getCodName() +
                            "," + commodity.getRetailPrice() +
                            "," + commodity.getPurchasePrice() +
                            "," + mrBeginDate +
                            "," + mrEndDate + ",";
                    if (!StringUtil.isEmpty(commodity.getRemark())) {
                        str += commodity.getRemark();
                    }
                    str += "\r\n";
                    fos.write(str.getBytes());
                }
            }
            fos.flush();
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/plain");
            response.setHeader("Content-Disposition", "attachment; filename=" + exportFileName);
            response.setHeader("Content-Length", String.valueOf(exportFile.length()));
            in = new BufferedInputStream(new FileInputStream(exportFile));
            out = new BufferedOutputStream(response.getOutputStream());
            byte[] data = new byte[1024];
            int len = 0;
            while (-1 != (len = in.read(data, 0, data.length))) {
                out.write(data, 0, len);
            }
            out.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
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
        /*BufferedInputStream in = null;
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
            EnfordProductDepartment department;
            String deptIdStr = request.getParameter("deptId");
            int deptId;
            if (!StringUtil.isEmpty(deptIdStr)) {
                deptId = Integer.parseInt(deptIdStr);
                department = deptService.getDepartmentByDeptId(deptId);
            } else {
                String deptCode = request.getParameter("deptCode");
                department = deptService.getDepartmentByDeptCode(deptCode);
                deptId = department.getId();
            }
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
            *//*byte[] data = new byte[1024];
            int len = 0;
            while (-1 != (len = fis.read(data, 0, data.length))) {
                fos.write(data, 0, len);
            }
            fos.flush();*//*

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
                        if (price != null && price.getPromptPrice() != null) {
                            promptPriceCell.setCellValue(price.getPromptPrice());
                        }
                        if (price != null && price.getRetailPrice() != null) {
                            retailPriceCell.setCellValue(price.getRetailPrice());
                        }
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
        }*/
    }

    @RequestMapping("/market_research/sync")
    public void syncMarketResearch(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("开始同步市调清单...");
        RespBody respBody = new RespBody();
        try {
            SyncHandler syncHandler = new SyncHandler();
            syncHandler.syncData();

            respBody.setCode(SUCCESS);
            respBody.setMsg("同步成功!");
        } catch (Exception ex) {
            respBody.setCode(FAILED);
            respBody.setMsg("同步失败!");
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

}
