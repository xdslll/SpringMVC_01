package com.demo.web;

import com.demo.model.EnfordProductImportHistory;
import com.demo.model.EnfordProductOrganization;
import com.demo.model.RespBody;
import com.demo.service.UploadService;
import com.demo.util.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

/**
 * @author xiads
 * @date 16/2/1
 */
@Controller
public class DataImportController {

    private static final Logger logger = Logger.getLogger(DataImportController.class);

    @Resource
    UploadService uploadService;

    @RequestMapping("/import")
    public String importHome() {
        return "/import/import";
    }

    @RequestMapping("/import/get")
    public void getImport(HttpServletRequest req, HttpServletResponse resp) {
        List<EnfordProductImportHistory> importHistory = null;
        try {
            importHistory = uploadService.getImportHistory();
        }
        catch (Exception ex) {
            logger.error("exception occuried when getImport:" + ex);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(importHistory));
    }


    @RequestMapping("/import/excel")
    public void importExcel(HttpServletRequest req, HttpServletResponse resp) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        RespBody<String> respBody = new RespBody<String>();
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(4 * 1024);
            factory.setRepository(new File(Config.getProperty("uploadpath")));
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax(100 * 1024 * 1024);
            List items = upload.parseRequest(req);
            EnfordProductImportHistory bookAttach = new EnfordProductImportHistory();
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (!item.isFormField()) {
                    String fileName = item.getName();
                    String randomStr = StringUtil.getRandomUUID();
                    String storeFileName = randomStr;

                    if (!fileName.endsWith(".xls")) {
                        respBody.setCode(Consts.FAILED);
                        respBody.setMsg("不支持该格式");
                        throw new Exception(respBody.getMsg());
                    }
                    String endPrex = "";
                    if (fileName.endsWith(".xlsx")) {
                        endPrex = ".xlsx";
                    }
                    else {
                        endPrex = ".xls";
                    }
                    File storeFile = new File(Config.getProperty("uploadpath") + storeFileName + endPrex);
                    item.write(storeFile);

                    bookAttach.setFilePath(randomStr);
                    bookAttach.setFileName(fileName);
                    bookAttach.setSize(new DecimalFormat("#.00").format(item.getSize() / 1024.00));
                    bookAttach.setState(0);
                    int userId = SessionUtil.getUserId(req);
                    bookAttach.setCreateBy(userId);
                }
            }
            uploadService.addImportHistory(bookAttach);
            respBody.setCode(Consts.SUCCESS);
            respBody.setMsg("上传成功");
        }
        catch (Exception ex) {
            logger.error("error occuried when importExcel:" + ex);
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
                logger.error("error occuried when importExcel:" + ex);
            }
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

}
