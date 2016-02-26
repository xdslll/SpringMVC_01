package com.demo.web.sg;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xiads
 * @date 16/2/1
 */
@Controller
public class TestController {

    @RequestMapping("/")
    public String index() {
        return "/test/test";
    }

}
