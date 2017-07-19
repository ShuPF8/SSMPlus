package com.spf.controller.test;

import com.spf.common.BaseUtils;
import com.spf.common.ResultJson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author SPF
 * @Date 2017/5/3
 */
@Controller
public class ApiSignController extends BaseUtils {

    @RequestMapping("/signindex")
    public String index() {
        return "test/apisign";
    }

    @RequestMapping("/sign")
    @ResponseBody
    public void inter() {
        ResultJson json = new ResultJson();
        json.setCode(200);
        backClient(toJSONString(json));
    }
}
