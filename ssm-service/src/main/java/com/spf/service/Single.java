package com.spf.service;

import com.alibaba.fastjson.JSON;
import com.spf.common.result.BizResult;
import com.spf.entity.SfUser;
import com.spf.mapper.SfUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.List;

/**
 * Created by SPF on 2017/8/3.
 */
public class Single extends SpringBeanAutowiringSupport {

    private static Single single;

    private @Autowired ISfUserService iSfUserService;
    private @Autowired SfUserMapper sfUserMapper;

    private Single(){
        System.out.println("single构造器");
        BizResult<SfUser> result = iSfUserService.findByID("2");
        System.out.println(JSON.toJSONString(result));
        SfUser s = sfUserMapper.selectById(1);
        System.out.println(JSON.toJSONString(s));
    }

    public static Single getInstance(){
        if (single == null) {
            single = new Single();
        }
        return single;
    }

    public void getTO(){
        System.out.println("single getTO 方法");
    }

}
