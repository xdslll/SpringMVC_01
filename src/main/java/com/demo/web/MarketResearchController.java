package com.demo.web;

import com.alibaba.fastjson.JSONObject;
import com.demo.model.EnfordMarketResearch;
import com.demo.model.RespBody;
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
}
