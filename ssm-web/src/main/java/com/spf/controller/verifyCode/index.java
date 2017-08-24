package com.spf.controller.verifyCode;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by SPF on 2017/8/24.
 */
@Controller
public class index {

    @RequestMapping("verity/index")
    public String index(){
        return "verity/index";
    }

    @RequestMapping("verity/login")
    public String login(){
        return "verity/login";
    }
}
