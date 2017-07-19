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
public class InterceptorController extends BaseUtils {

    @RequestMapping("inter")
    @ResponseBody
    public void inter() {
        System.out.println("执行了 controller ");
        ResultJson json = new ResultJson();
        json.success();
        backClient(toJSONString(json));
    }
}
