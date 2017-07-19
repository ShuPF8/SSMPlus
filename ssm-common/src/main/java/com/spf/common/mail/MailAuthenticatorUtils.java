package com.spf.common.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**服务器邮箱登录验证
 * @Author SPF
 * @Date 2017/4/27
 */
public class MailAuthenticatorUtils extends Authenticator {
    /**
     * 用户名（登录邮箱）
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * 初始化用户名和密码
     * @param password 用户名
     * @param username 密码
     */
    public MailAuthenticatorUtils(String password, String username) {
        this.password = password;
        this.username = username;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }

    /**
     * 获取用户名（登录邮箱）
     * @return username 用户名（登录邮箱）
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * 设置用户名（登录邮箱）
     * @param username 用户名（登录邮箱）
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码
     * @return password 密码
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * 设置密码
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
