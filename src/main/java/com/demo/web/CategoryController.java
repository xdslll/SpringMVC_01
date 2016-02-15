package com.demo.web;

import com.demo.model.EnfordProductCategory;
import com.demo.model.RespBody;
import com.demo.service.CategoryService;
import com.demo.util.Config;
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
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiads
 * @date 16/2/1
 */
@Controller
public class CategoryController implements Consts {

    private static final Logger logger = Logger.getLogger(CategoryController.class);

    @Resource
    CategoryService categoryService;

    @RequestMapping("/category/manage")
    public String manage() {
        return "/category/category";
    }

    @RequestMapping("/category/get")
    public void getCagetory(HttpServletRequest req, HttpServletResponse resp) {
        List<EnfordProductCategory> cats = null;
        try {
            cats = categoryService.getAllCategory();
        }
        catch (Exception ex) {
            logger.error("exception occurred when getCagetory:" + ex);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(cats));
    }

    @RequestMapping("/api/category/root")
    public void getRootCategory(HttpServletRequest req, HttpServletResponse resp,
                                @RequestParam("resId") int resId,
                                @RequestParam("deptId") int deptId) {
        RespBody<EnfordProductCategory> respBody = new RespBody<EnfordProductCategory>();
        try {
            List<EnfordProductCategory> rootCategories = categoryService.getRootCategory(resId, deptId);
            if (rootCategories != null) {
                respBody.setCode(SUCCESS);
                respBody.setMsg("获取分类成功!");
                respBody.setDatas(rootCategories);
                respBody.setTotalnum(rootCategories.size());
            } else {
                respBody.setCode(FAILED);
                respBody.setMsg("获取分类失败!");
            }
        } catch (Exception ex) {
            respBody.setCode(FAILED);
            respBody.setMsg("获取分类失败:" + ex.getMessage());
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }

    @RequestMapping("/api/category/image")
    public void getCategoryImage(HttpServletRequest req, HttpServletResponse resp,
                                 @RequestParam("code") String code) {
        String fileDir = Config.getCategoryPath();
        String fileName = code + ".png";
        OutputStream os = null;
        FileInputStream in = null;
        try {
            os = resp.getOutputStream();
            in = new FileInputStream(new File(fileDir, fileName));
            byte[] b = new byte[1024];
            int length = 0;
            while ((length = in.read(b)) > 0) {
                os.write(b, 0, length);
            }
            os.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @RequestMapping("/api/category/get")
    public void getRootCategory(HttpServletRequest req, HttpServletResponse resp,
                                @RequestParam("code") int code) {
        RespBody<List<EnfordProductCategory>> respBody = new RespBody<List<EnfordProductCategory>>();
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("parent", code);
            List<EnfordProductCategory> categoryList = categoryService.getCategoryByParam(param);
            if (categoryList != null) {
                respBody.setCode(SUCCESS);
                respBody.setMsg("获取分类成功!");
                respBody.setData(categoryList);
                respBody.setTotalnum(categoryList.size());
            } else {
                respBody.setCode(FAILED);
                respBody.setMsg("获取分类失败!");
            }
        } catch (Exception ex) {
            respBody.setCode(FAILED);
            respBody.setMsg("获取分类失败:" + ex.getMessage());
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(respBody));
    }
}
