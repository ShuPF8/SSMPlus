package com.spf.controller.timer;


import com.spf.common.quartz.QuartzManager;

/**
 * @Author SPF
 * @Date 2017/6/5
 */
public class DynamicAddQuartz {

    public static String JOB_NAME = "动态任务调度";
    public static String TRIGGER_NAME = "动态任务触发器";
    public static String JOB_GROUP_NAME = "XLXXCC_JOB_GROUP";
    public static String TRIGGER_GROUP_NAME = "XLXXCC_JOB_GROUP";

    public static void main(String[] args) {
        try {
            System.out.println("【系统启动】开始(每1秒输出一次)...");
            QuartzManager.addJob("myjob", "myjob_group", "trigger_myjob", "trigger_myjob_group", MyJob.class, "0/1 * * * * ?");
            QuartzManager.addJob("myjob2", "myjob2_group", "trigger_myjob2", "trigger_myjob2_group", MyJob2.class, "0/1 * * * * ?");

            Thread.sleep(3000);
//            System.out.println("【修改时间】开始(每5秒输出一次)...");
//            QuartzManager.modifyJobTime(JOB_NAME, JOB_GROUP_NAME, TRIGGER_NAME, TRIGGER_GROUP_NAME, "0/5 * * * * ?");

            //暂停任务
            System.out.println("【暂停任务】开始......");
            QuartzManager.pauseJob("myjob","myjob_group");
            Thread.sleep(3000);
            //恢复任务
            System.out.println("【恢复任务】......");
            QuartzManager.resumeJob("myjob","myjob_group");
//            System.out.println("【移除定时】开始...");
//            QuartzManager.removeJob("myjob", "myjob_group", "trigger_myjob", "trigger_myjob_group");
//            System.out.println("【移除定时】成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
