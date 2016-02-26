package com.demo.web.sg;

import com.demo.util.Config;
import com.demo.util.HttpHelper;
import com.demo.util.ResponseUtil;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xiads
 * @date 16/2/24
 */
@Controller
public class CheckController {

    @RequestMapping("/check/enable")
    public void checkEnable(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String checkResult = HttpHelper.get("http://localhost:8081/product/check?product_code=" + Config.getProductCode());
            ResponseUtil.writeStringResponse(resp, checkResult);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
