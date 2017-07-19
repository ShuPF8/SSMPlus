package com.spf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyController {

	@RequestMapping("/dec/index")
	public ModelAndView index(){
	    ModelAndView mav = new ModelAndView();
	    mav.addObject("hello", "Hello world!");
	    mav.setViewName("test/index");
	    return mav;
	}

	@RequestMapping("/index")
	public ModelAndView index2(){
		ModelAndView mav = new ModelAndView();
		mav.addObject("hello", "Hello SF hello!");
		mav.setViewName("test/index");
		return mav;
	}
	
}
