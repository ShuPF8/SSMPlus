package com.spf.controller.test;

import com.spf.common.BaseUtils;
import com.spf.common.ResultJson;
import com.spf.entity.UserRecordEntity;
import com.spf.service.UserRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @Author SPF
 * @Date 2017/5/2
 */
@Controller
public class UserRecordController extends BaseUtils {

    @Autowired private UserRecordService userRecordService;

    @RequestMapping("gener")
    @ResponseBody
    public void save(){
        ResultJson json = new ResultJson();
        UserRecordEntity ur = new UserRecordEntity();
        ur.setName("spf222");
        ur.setTime(new Date());
        int n = userRecordService.insert(ur);
        if (n > 0) {
            json.setCode(200);
            json.setMaseege("成功");
        } else {
            json.setCode(400);
            json.setMaseege("失败");
        }
        backClient(toJSONString(json));
    }
}
