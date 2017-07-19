package com.spf.controller.autocomplete;

import com.spf.common.BaseUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Auther ShuPF
 * @Create 2017/7/7 0007
 */

@RestController
public class autocomplete extends BaseUtils{

    @RequestMapping("/autocomplete/index")
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("autocomplete/index");
        return mav;
    }

}
