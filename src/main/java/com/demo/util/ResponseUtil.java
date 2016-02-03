package com.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by sh on 2015/9/18.
 */
public class ResponseUtil {
	
	private static final Logger logger = Logger.getLogger(ResponseUtil.class);
    /**
     * 返回Json報文
     * @param obj
     * @throws IOException
     */
    public static String returnJsonResponse(Object obj){
    	
    	
        String result = null ;
        if(obj!=null){
            result = JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue);
        }else{
            result = "{}";
        }
        return result ;
    }

    /**
     * 返回String
     * @param response
     * @param result
     * @throws IOException
     */
    public static void writeStringResponse(HttpServletResponse response, String result){
        if(response == null){
            return ;
        }
        response.setContentType("text/plain;charset=UTF-8");
        try
        {
	        PrintWriter out = response.getWriter();
	        out.print(result);
	        out.flush();
	        out.close();
        }catch(IOException ex)
        {
        	logger.error("writeStringResponse error:"+ex);
        }
    }
}
