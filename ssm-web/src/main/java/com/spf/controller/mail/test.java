package com.spf.controller.mail;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Date;
import java.util.Properties;

/** POP3/SMTP服务 ynoxjllzzbakcaai
 * @Author SPF
 * @Date 2017/4/27
 */
public class test {

    // 发件人的 邮箱 和 密码（替换为自己的邮箱和密码）
    // PS: 某些邮箱服务器为了增加邮箱本身密码的安全性，给 SMTP 客户端设置了独立密码（有的邮箱称为“授权码”）,
    //     对于开启了独立密码的邮箱, 这里的邮箱密码必需使用这个独立密码（授权码）。
    public static String myEmailAccount = "spfylf@163.com";
    public static String myEmailPassword = "zhiai13";
    public static String myEmailName = "苏菲";

    // 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
    // 网易163邮箱的 SMTP 服务器地址为: smtp.163.com
    public static String myEmailSMTPHost = "smtp.163.com";

    // 收件人邮箱（替换为自己知道的有效邮箱）
    public static String receiveMailAccount = "517292069@qq.com";

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

        // SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,
        //                  需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助,
        //                  QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)
        final String smtpPort = "465";
        prop.setProperty("mail.smtp.port", smtpPort);
        prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.setProperty("mail.smtp.socketFactory.fallback", "false");
        prop.setProperty("mail.smtp.socketFactory.port", smtpPort);
    }

    public static void main(String[] args) throws Exception {
        // 1. 创建邮件会话
        Session session = Session.getDefaultInstance(prop);
        // 2. 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 3. 设置参数：标题、发件人、收件人、发送时间、内容
        message.setSubject("带图片附件邮件");
        message.setSender(new InternetAddress(myEmailAccount));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress("517292069@qq.com.com"));
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
        String attr_path = "D:/SPF/文档/JVV项目结构.docx";
        DataSource attr_ds = new FileDataSource(new File(attr_path));
        DataHandler attr_handler = new DataHandler(attr_ds);
        right.setDataHandler(attr_handler);
        right.setFileName("JVV项目结构.docx");


        /***************设置邮件内容: 多功能用户邮件 (related)*******************/
        // 4.1 构建一个多功能邮件块
        MimeMultipart related = new MimeMultipart("related");
        // ----> 设置到总邮件快的左侧中
        left.setContent(related);

        // 4.2 构建多功能邮件块内容 = 左侧文本 + 右侧图片资源
        MimeBodyPart content = new MimeBodyPart();
        MimeBodyPart resource = new MimeBodyPart();

        // 设置具体内容: a.资源(图片)
        String filePath = "C:/Users/Administrator/Desktop/hzw.jpg";
        DataSource ds = new FileDataSource(new File(filePath));
        DataHandler handler = new DataHandler(ds);
        resource.setDataHandler(handler);
        resource.setContentID("hzw.jpg");  // 设置资源名称，给外键引用

        // 设置具体内容: b.文本
        content.setContent("<img src='cid:hzw.jpg'/> 好哈哈！", "text/html;charset=UTF-8");

        related.addBodyPart(content);
        related.addBodyPart(resource);

        // 5. 发送
        Transport trans = session.getTransport();
        trans.connect(myEmailAccount, myEmailPassword);
        trans.sendMessage(message, message.getAllRecipients());
        trans.close();
        System.out.println("成功");
    }
}

