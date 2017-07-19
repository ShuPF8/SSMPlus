package com.spf.controller;


import com.alibaba.fastjson.JSON;
import com.spf.common.BaseUtils;
import com.spf.common.ResultJson;
import com.spf.common.result.BizResult;
import com.spf.entity.SfUser;
import com.spf.service.ISfUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 * @since 2017-07-06
 */
@Slf4j
@Controller
@RequestMapping("/sfUser")
public class SfUserController extends BaseUtils {

    @Autowired private ISfUserService iSfUserService;

    @RequestMapping("/findAll")
    public void findAll(){
        log.info("查询所有数据");
        BizResult< List<SfUser>> resultJson = iSfUserService.findAll();
        log.info("查询所有数据---》"+resultJson.getData());
       backClient(JSON.toJSONString(resultJson));
    }

    @RequestMapping("/save")
    public void save(){
        SfUser s = new SfUser();
        s.setSex("女");
        s.setAge(22);
        s.setPwd("250250");;
        s.setPhone("561565646556");
        s.setName("露露狗");
        ResultJson result = iSfUserService.save(s);
        backClient(JSON.toJSONString(result));
    }

}
