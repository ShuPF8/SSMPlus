package com.spf.common.verifyCode;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by SPF on 2017/8/24.
 */
public class AuthImageServlet extends HttpServlet implements Servlet {

    static final long serialVersionUID = 1L;

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        //生成随机字串
        String verifyCode = PhotoVerifyCodeUtil.generateVerifyCode(5);

        //生成表达式字符串
        String operator = PhotoVerifyCodeUtil.operatorVerifyCode(4);
        Integer result = PhotoVerifyCodeUtil.eval(operator);
        operator += "=?";

        //存入会话session
        HttpSession session = request.getSession(true);
        //删除以前的
        session.removeAttribute("verCode");
        session.setAttribute("verCode", result);
        //生成图片
        int w = 170, h = 60;
        PhotoVerifyCodeUtil.outputImage(w, h, response.getOutputStream(), operator);

    }

}
