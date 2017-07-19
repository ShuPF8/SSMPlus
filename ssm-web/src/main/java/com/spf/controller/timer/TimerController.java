package com.spf.controller.timer;

import com.spf.common.BaseUtils;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * @Author SPF
 * @Date 2017/5/27
 */
@Component
public class TimerController extends BaseUtils {

    public void job() {
        //task();
    }

    public void task() {
        System.out.println("timer:"+new Date());
    }

}
