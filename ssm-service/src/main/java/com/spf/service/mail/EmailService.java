package com.spf.service.mail;


import com.spf.common.mail.MailAuthenticatorUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

/**
 * 邮箱发送服务
 * PS: 注意，如果是web项目，需要删除javaee-api中的mail包
 * @Author SPF
 * @Date 2017/4/27
 */
@Service
public class EmailService {

    // 发件人的 邮箱 和 密码（替换为自己的邮箱和密码）
    // PS: 某些邮箱服务器为了增加邮箱本身密码的安全性，给 SMTP 客户端设置了独立密码（有的邮箱称为“授权码”）,
    //     对于开启了独立密码的邮箱, 这里的邮箱密码必需使用这个独立密码（授权码）。
    public static String myEmailAccount = "spfylf@163.com";
    public static String myEmailPassword = "zhiai13";
    public static String myEmailName = "苏菲";

    // 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
    // 网易163邮箱的 SMTP 服务器地址为: smtp.163.com
    public static String myEmailSMTPHost = "smtp.163.com";

    // SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,
    //                  需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助,
    //                  QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)
    public static final String smtpPort = "465";

    public static Properties prop;
    static {
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        prop = new Properties();                    // 参数配置
        prop.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        prop.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        prop.setProperty("mail.smtp.auth", "true");            // 需要请求认证

        // PS: 某些邮箱服务器要求 SMTP 连接需要使用 SSL 安全认证 (为了提高安全性, 邮箱支持SSL连接, 也可以自己开启),
        //     如果无法连接邮件服务器, 仔细查看控制台打印的 log, 如果有有类似 “连接失败, 要求 SSL 安全连接” 等错误,
        //     打开下面 /* ... */ 之间的注释代码, 开启 SSL 安全连接。

        prop.setProperty("mail.smtp.port", smtpPort);
        prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.setProperty("mail.smtp.socketFactory.fallback", "false");
        prop.setProperty("mail.smtp.socketFactory.port", smtpPort);
    }

    /**
     * 发送文字邮件-多个收件人以英文逗号隔开“,”
     * @param title 标题
     * @param content 内容
     * @param to 收件人
     * @return
     */
    public boolean sendMail(String title, String content, String to) {
        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getDefaultInstance(prop, new MailAuthenticatorUtils(myEmailAccount, myEmailPassword));
        session.setDebug(true);                                 // 设置为debug模式, 可以查看详细的发送 log

        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        try {
            // 2. From: 发件人
            message.setFrom(new InternetAddress(myEmailAccount, myEmailName, "UTF-8"));

            // 3. To: 收件人（可以增加多个收件人、抄送、密送） RecipientType.CC 抄送 RecipientType.BCC 密送
           // message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to, to + "用户", "UTF-8"));
            setRecipient(message, to);

            // 4. Subject: 邮件主题
            message.setSubject(title, "UTF-8");

            // 5. Content: 邮件正文（可以使用html标签）
            message.setContent(content, "text/html;charset=UTF-8");

            // 6. 设置发件时间
            message.setSentDate(new Date());

            // 7. 保存设置
            message.saveChanges();

            // 8. 根据 Session 获取邮件传输对象
            Transport transport = session.getTransport();

            // 9. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
            //
            //    PS_01: 成败的判断关键在此一句, 如果连接服务器失败, 都会在控制台输出相应失败原因的 log,
            //           仔细查看失败原因, 有些邮箱服务器会返回错误码或查看错误类型的链接, 根据给出的错误
            //           类型到对应邮件服务器的帮助网站上查看具体失败原因。
            //
            //    PS_02: 连接失败的原因通常为以下几点, 仔细检查代码:
            //           (1) 邮箱没有开启 SMTP 服务;
            //           (2) 邮箱密码错误, 例如某些邮箱开启了独立密码;
            //           (3) 邮箱服务器要求必须要使用 SSL 安全连接;
            //           (4) 请求过于频繁或其他原因, 被邮件服务器拒绝服务;
            //           (5) 如果以上几点都确定无误, 到邮件服务器网站查找帮助。
            //
            //    PS_03: 仔细看log, 认真看log, 看懂log, 错误原因都在log已说明。
            transport.connect(myEmailAccount, myEmailPassword);

            // 10. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
            transport.sendMessage(message, message.getAllRecipients());

            // 11. 关闭连接
            transport.close();
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 发送带图片的邮件
     * @param title 标题
     * @param cont 内容
     * @param to 收件人-多个收件人以英文逗号隔开“,”
     * @param photoUrl 图片路径
     * @param phototName 图片名称
     * @return 布尔值
     */
    public boolean sendPhotoMail(String title, String cont, String to, String photoUrl, String phototName) {
        // 1. 创建邮件会话
        Session session = Session.getDefaultInstance(prop, new MailAuthenticatorUtils(myEmailAccount, myEmailPassword));
        // 2. 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        try {
            // 3. 设置参数：标题、发件人、收件人、发送时间、内容
            message.setSubject(title);
            message.setSender(new InternetAddress(myEmailAccount));

            setRecipient(message, to);

            message.setSentDate(new Date());

            /***************设置邮件内容: 多功能用户邮件 (related)*******************/
            // 4.1 构建一个多功能邮件块
            MimeMultipart related = new MimeMultipart("related");
            // 4.2 构建多功能邮件块内容 = 左侧文本 + 右侧图片资源
            MimeBodyPart content = new MimeBodyPart();
            MimeBodyPart resource = new MimeBodyPart();

            // 设置具体内容: a.资源(图片)
            DataSource ds = new FileDataSource(new File(photoUrl));
            DataHandler handler = new DataHandler(ds);
            resource.setDataHandler(handler);
            resource.setContentID(phototName);  // 设置资源名称，给外键引用

            // 设置具体内容: b.文本
            content.setContent("<img src='cid:"+phototName+"'/> </br>"+cont, "text/html;charset=UTF-8");

            related.addBodyPart(content);
            related.addBodyPart(resource);

            /*******4.3 把构建的复杂邮件快，添加到邮件中********/
            message.setContent(related);

            // 5. 发送
            Transport trans = session.getTransport();
            trans.connect(myEmailAccount, myEmailPassword);
            trans.sendMessage(message, message.getAllRecipients());
            trans.close();
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 发送带图片和附件的邮件
     * @param title 标题
     * @param cont 内容
     * @param to 收件人
     * @param photoUrl 图片路径
     * @param phototName  图片名称
     * @param encUrl 附件路径
     * @param encName 附件名称
     * @return
     */
    public boolean sendPhotoEnclosureMail(String title, String cont, String to, String photoUrl, String phototName, String encUrl, String encName) {
        // 1. 创建邮件会话
        Session session = Session.getDefaultInstance(prop, new MailAuthenticatorUtils(myEmailAccount, myEmailPassword));
        // 2. 创建邮件对象
        MimeMessage message = new MimeMessage(session);

        try {
            // 3. 设置参数：标题、发件人、收件人、发送时间、内容
            message.setSubject(title);
            message.setSender(new InternetAddress(myEmailAccount));
            this.setRecipient(message, to);
            message.setSentDate(new Date());

            /*
             * 带附件(图片)邮件开发
             */
            // 构建一个总的邮件块
            MimeMultipart mixed = new MimeMultipart("mixed");
            // ---> 总邮件快，设置到邮件对象中
            message.setContent(mixed);
            // 左侧： （文本+图片资源）
            MimeBodyPart left = new MimeBodyPart();
            // 右侧： 附件
            MimeBodyPart right = new MimeBodyPart();
            // 设置到总邮件块
            mixed.addBodyPart(left);
            mixed.addBodyPart(right);

            /******附件********/
            DataSource attr_ds = new FileDataSource(new File(encUrl));
            DataHandler attr_handler = new DataHandler(attr_ds);
            right.setDataHandler(attr_handler);
            right.setFileName(encName);

            /***************设置邮件内容: 多功能用户邮件 (related)*******************/
            // 4.1 构建一个多功能邮件块
            MimeMultipart related = new MimeMultipart("related");
            // ----> 设置到总邮件快的左侧中
            left.setContent(related);

            // 4.2 构建多功能邮件块内容 = 左侧文本 + 右侧图片资源
            MimeBodyPart content = new MimeBodyPart();
            MimeBodyPart resource = new MimeBodyPart();

            // 设置具体内容: a.资源(图片)
            DataSource ds = new FileDataSource(new File(photoUrl));
            DataHandler handler = new DataHandler(ds);
            resource.setDataHandler(handler);
            resource.setContentID(phototName);  // 设置资源名称，给外键引用

            // 设置具体内容: b.文本
            content.setContent("<img src='cid:"+phototName+"'/> </br>" + cont, "text/html;charset=UTF-8");

            related.addBodyPart(content);
            related.addBodyPart(resource);

            // 5. 发送
            Transport trans = session.getTransport();
            trans.connect(myEmailAccount, myEmailPassword);
            trans.sendMessage(message, message.getAllRecipients());
            trans.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 设置收件人
     * @param message 创建一封邮件
     * @param to 收件人，多个人以英文逗号隔开“,”
     */
    private void setRecipient(MimeMessage message, String to) {
        String[] recipients = to.split(",");
        try{
            if (recipients.length == 1) {
                message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to, to + "用户", "UTF-8"));
            } else {
                message.setRecipient(MimeMessage.RecipientType.BCC, new InternetAddress(recipients[0], recipients[0] + "用户", "UTF-8"));
                for (int i = 1; i < recipients.length; i++) {
                    message.addRecipient(MimeMessage.RecipientType.BCC, new InternetAddress(recipients[i], recipients[i] + "用户", "UTF-8"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        URL xmlpath = EmailService.class.getClassLoader().getResource("spf.png");
        System.out.println(EmailService.class.getClassLoader().getResource("spf.png"));
    }

}
