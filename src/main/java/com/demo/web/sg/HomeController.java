package com.demo.web.sg;

import com.demo.util.Consts;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xiads
 * @date 16/1/30
 */
@Controller
public class HomeController {

    @RequestMapping(value = "/home")
    public String home(Model model) {
        model.addAttribute("title", Consts.PRODUCT_NAME);
        model.addAttribute("product_name", Consts.PRODUCT_NAME);
        model.addAttribute("foot_info", Consts.FOOT_INFO);
        return "/home/home";
    }

    @RequestMapping(value = "/common/head")
    public String head() {
        return "/common/head";
    }

}
