package com.spf.controller.timer;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;


/**
 * @Author SPF
 * @Date 2017/6/5
 */
public class MyJob implements Job{
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("------------->"+new Date() + ": doing something...");
    }
}
