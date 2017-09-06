package com.spf.common.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/** 多线程框架
 * @Auther SPF
 * @Create 2017/9/6
 */
public class ExecutorTest {

    private static Integer pages=1; // 网页数

    private static boolean exeFlag=true; // 执行标识

    public static void main(String[] args) {
        final ExecutorService executorService= Executors.newFixedThreadPool(10); // 创建ExecutorService 连接池默认连接10个
        while(exeFlag){
            if(pages<=100){
                executorService.execute(new Runnable() {

                    public void run() {
                        // TODO Auto-generated method stub
                        System.out.println("爬取了第"+pages+"网页...");
                        pages++;
                    }
                });
            }else{
                if(((ThreadPoolExecutor)executorService).getActiveCount()==0){ // 活动线程个数是0
                    executorService.shutdown(); // 结束所有线程
                    exeFlag=false;
                    System.out.println("爬虫任务已经完成");
                }
            }
            try {
                Thread.sleep(1); // 线程休息1毫秒
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
