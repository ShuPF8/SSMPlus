package com.spf.controller.payh5;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by SPF on 2017/7/14.
 */
@Controller
public class payh5 {

    @RequestMapping("/payh5")
    public String payh5(){
        return "payh5/index";
    }

}
