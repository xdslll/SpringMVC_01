package com.demo.web.sg;

import com.demo.model.EnfordProductArea;
import com.demo.model.EnfordProductDepartment;
import com.demo.model.EnfordSystemUser;
import com.demo.model.RespBody;
import com.demo.service.AreaService;
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
public class AreaController {

    private static final Logger logger = Logger.getLogger(AreaController.class);

    @Resource
    AreaService areaService;

    @RequestMapping("/area/get")
    public void getAreas(HttpServletRequest req, HttpServletResponse resp) {
        List<EnfordProductArea> areaList = null;
        try {
            EnfordSystemUser user = (EnfordSystemUser) req.getSession().getAttribute("user");
            String resId = req.getParameter("resId");
            areaList = areaService.getAreaTree(Integer.parseInt(resId));
        }
        catch (Exception ex) {
            logger.error("exception occurred when getAreas:" + ex);
        }
        ResponseUtil.writeStringResponse(resp, FastJSONHelper.serialize(areaList));
    }

}
