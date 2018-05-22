package com.demo.web.sg;

import com.demo.model.*;
import com.demo.model.EnfordProductImportHistory;
import com.demo.model.EnfordSystemUser;
import com.demo.service.MarketResearchSerivce;
import com.demo.service.UploadService;
import com.demo.util.*;
import com.google.gson.Gson;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.aspectj.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author xiads
 * @date 16/2/1
 */
@Controller
public class DataImportController {

    private static final Logger logger = Logger.getLogger(DataImportController.class);

    @Resource
    UploadService uploadService;

    @Resource
    MarketResearchSerivce marketService;

    @RequestMapping("/import")
    public String importCommodity() {
        return "import/cod_import";
    }

    @RequestMapping("/import/dept")
    public String importDept() {
        return "import/dep_import";
    }

    @RequestMapping("/import/area")
    public String importArea() {
        return "import/area_import";
    }

    @RequestMapping("/import/employee")
    public String importEmployee() {
        return "import/employee_import";
    }

    @RequestMapping("/import/get")
    public void getImport(HttpServletRequest req, HttpServletResponse resp, int type) {
        List<EnfordProductImportHistory> importHistory = null;
        try {
            importHistory = uploadService.getImportHistory(type);
        }
        catch (Exception ex) {
            logger.error("exception occured when getImport:" + ex);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(importHistory));
    }

    @RequestMapping("/import/delete")
    public void deleteImport(HttpServletRequest req, HttpServletResponse resp, int id) {
        RespBody<String> respBody = new RespBody<String>();
        try {
            uploadService.deleteImportHistoryAndFile(id);
            respBody.setCode(Consts.SUCCESS);
            respBody.setMsg("删除记录成功！");
        }
        catch (Exception ex) {
            logger.error("exception occured when deleteImport:" + ex);
            respBody.setCode(Consts.FAILED);
            respBody.setMsg("删除记录出错:" + ex.getMessage());
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    /**
     * 上传Excel文件到服务器
     *
     * @param req
     * @param resp
     * @param type
     */
    @RequestMapping("/import/excel")
    public void importExcel(HttpServletRequest req, HttpServletResponse resp, int type) {
        uploadExcelFile(req, resp, type);
    }

    private void createFolder(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /*
    private void uploadExcelFile2() {
        //获取临时目录
        String tempPath = Config.getTempPath();
        //文件目录名
        String filePath = Config.getUploadFilePath();
        createFolder(tempPath);
        createFolder(filePath);
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //设置最大缓存
        factory.setSizeThreshold(5 * 1024);
        //设置临时文件目录
        factory.setRepository(new File(tempPath));
        ServletFileUpload upload = new ServletFileUpload(factory);
    }
    */

    private void uploadExcelFile(HttpServletRequest req, HttpServletResponse resp, int type) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        RespBody<String> respBody = new RespBody<String>();
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(4 * 1024);
            File uploadDir = new File(Config.getUploadFilePath());
            File tempDir = new File(Config.getTempPath());
            //factory.setRepository(uploadDir);
            factory.setRepository(tempDir);
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax(100 * 1024 * 1024);
            List items = upload.parseRequest(req);
            EnfordProductImportHistory importHistory = new EnfordProductImportHistory();
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (!item.isFormField()) {
                    String fileName = item.getName();
                    String randomStr = StringUtil.getRandomUUID();
                    String storeFileName = randomStr;
                    String fileType = "";

                    if (!fileName.endsWith(".xls") &&
                            !fileName.endsWith(".xlsx")) {
                        respBody.setCode(Consts.FAILED);
                        respBody.setMsg("不支持该格式");
                        throw new Exception(respBody.getMsg());
                    }
                    String endPrex = "";
                    if (fileName.endsWith(".xlsx")) {
                        endPrex = ".xlsx";
                        fileType = "xlsx";
                        fileName = fileName.substring(0, fileName.lastIndexOf(".xlsx"));
                    } else if (fileName.endsWith(".xls")) {
                        endPrex = ".xls";
                        fileType = "xls";
                        fileName = fileName.substring(0, fileName.lastIndexOf(".xls"));
                    }
                    File storeFile = new File(Config.getUploadFilePath() + storeFileName + endPrex);
                    // System.out.println("upload file: " + storeFile.toString());
                    if (!storeFile.exists()) {
                        storeFile.createNewFile();
                    }
                    item.write(storeFile);

                    importHistory.setFilePath(randomStr);
                    importHistory.setFileName(fileName);
                    importHistory.setSize(new DecimalFormat("#.00").format(item.getSize() / 1024.00));
                    importHistory.setState(0);
                    int userId = SessionUtil.getUserId(req);
                    importHistory.setCreateBy(userId);
                    importHistory.setCreateDt(new Date());
                    importHistory.setFileType(fileType);
                    importHistory.setType(type);
                }
            }
            uploadService.addImportHistory(importHistory);
            respBody.setCode(Consts.SUCCESS);
            respBody.setMsg("上传成功");
        }
        catch (Exception ex) {
            ex.printStackTrace();
            logger.error("error occured when importExcel:" + ex);
            respBody.setCode(Consts.FAILED);
            respBody.setCode("上传失败");
        }
        finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            }
            catch (Exception ex) {
                logger.error("error occured when importExcel:" + ex);
            }
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    /**
     * 将Excel的数据导入数据库
     *
     * @param req
     * @param resp
     */
    @RequestMapping("/import/do")
    public void doImportCod(HttpServletRequest req, HttpServletResponse resp,
                         String data, int ifCover) {
        doImport(req, resp, data, ifCover, 1);
    }

    @RequestMapping("/import/test")
    public void importTest(HttpServletRequest req, HttpServletResponse resp) {
        try {
            ResponseUtil.writeStringResponse(resp, "max id: " + 0);
        } catch (Exception ex) {
            ex.printStackTrace();
            ResponseUtil.writeStringResponse(resp, "出错:" + ex.getMessage());
        }
    }

    @RequestMapping("/import/dept/do")
    public void doImportDept(HttpServletRequest req, HttpServletResponse resp,
                         String data, int ifCover) {
        doImport(req, resp, data, ifCover, 2);
    }

    @RequestMapping("/import/area/do")
    public void doImportArea(HttpServletRequest req, HttpServletResponse resp,
                             String data, int ifCover) {
        doImport(req, resp, data, ifCover, 3);
    }

    @RequestMapping("/import/employee/do")
    public void doImportEmployee(HttpServletRequest req, HttpServletResponse resp,
                             String data, int ifCover) {
        doImport(req, resp, data, ifCover, 4);
    }

    private void doImport(HttpServletRequest req, HttpServletResponse resp,
                          String data, int ifCover, int type) {
        boolean ifCoverData;
        if (ifCover == 1) {
            ifCoverData = true;
        } else {
            ifCoverData = false;
        }
        //将json转换为文件对象
        EnfordProductImportHistory importHistory =
                new Gson().fromJson(data, EnfordProductImportHistory.class);
        RespBody<String> respBody = new RespBody<String>();
        try {
            //获取Session中的用户信息
            EnfordSystemUser user = (EnfordSystemUser) req.getSession().getAttribute("user");
            //将Excel数据导入数据库
            if (type == 1) {
                if (marketService.importMarketResearchData(
                        importHistory, user, ifCoverData)) {
                    importHistory.setState(Consts.IMPORT_STATE_HAVE_IMPORT);
                    respBody.setCode(Consts.SUCCESS);
                    respBody.setMsg("导入成功");
                } else {
                    importHistory.setState(Consts.IMPORT_STATE_IMPORT_FAILED);
                    respBody.setCode(Consts.FAILED);
                    respBody.setMsg("导入出错:未找到Excel文件或Excel解析失败!");
                }
                uploadService.updateImportHistory(importHistory);
            } else if (type == 2) {
                if (marketService.importDeptData(
                        importHistory, ifCoverData)) {
                    importHistory.setState(Consts.IMPORT_STATE_HAVE_IMPORT);
                    respBody.setCode(Consts.SUCCESS);
                    respBody.setMsg("导入成功");
                } else {
                    importHistory.setState(Consts.IMPORT_STATE_IMPORT_FAILED);
                    respBody.setCode(Consts.FAILED);
                    respBody.setMsg("导入出错:未找到Excel文件或Excel解析失败!");
                }
                uploadService.updateImportHistory(importHistory);
            } else if (type == 3) {
                if (marketService.importAreaData(
                        importHistory, ifCoverData)) {
                    importHistory.setState(Consts.IMPORT_STATE_HAVE_IMPORT);
                    respBody.setCode(Consts.SUCCESS);
                    respBody.setMsg("导入成功");
                } else {
                    importHistory.setState(Consts.IMPORT_STATE_IMPORT_FAILED);
                    respBody.setCode(Consts.FAILED);
                    respBody.setMsg("导入出错:未找到Excel文件或Excel解析失败!");
                }
                uploadService.updateImportHistory(importHistory);
            } else if (type == 4) {
                if (marketService.importEmployeeData(
                        importHistory, ifCoverData)) {
                    importHistory.setState(Consts.IMPORT_STATE_HAVE_IMPORT);
                    respBody.setCode(Consts.SUCCESS);
                    respBody.setMsg("导入成功");
                } else {
                    importHistory.setState(Consts.IMPORT_STATE_IMPORT_FAILED);
                    respBody.setCode(Consts.FAILED);
                    respBody.setMsg("导入出错:未找到Excel文件或Excel解析失败!");
                }
                uploadService.updateImportHistory(importHistory);
            }
        }
        catch (Exception ex) {
            logger.error("exception occured when doImport:" + ex);
            respBody.setCode(Consts.FAILED);
            respBody.setMsg("导入出错:" + ex.getMessage());
            importHistory.setState(Consts.IMPORT_STATE_IMPORT_FAILED);
            uploadService.updateImportHistory(importHistory);

            ex.printStackTrace();
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }
}
