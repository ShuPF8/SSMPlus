package com.spf.controller.mail;

import com.spf.service.mail.EmailService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @Author SPF
 * @Date 2017/4/27
 */
@Controller
public class MailController {
    private static final Logger logger = Logger.getLogger(MailController.class);
    @Resource
    EmailService emailService;

    @RequestMapping("/testMail")
    public void sendMail() {
        logger.info("-------------执行发送邮件START---------------");
        //发邮件
        String title = "大片上映";
        String content = "2017-4-3 海贼上映史上最牛逼的剧场版。";
        String to = "1215852831@qq.com,517292069@qq.com";
        String uel = "C:/Users/Administrator/Desktop/hzw.jpg";
        String name = "hzw.jpg";
        //emailService.sendMail(title, content, to); //纯文字邮件
        //emailService.sendPhotoMail(title,content,to,uel,name); //带图片邮件
        logger.info("-------------执行发送邮件END---------------");
    }
}
