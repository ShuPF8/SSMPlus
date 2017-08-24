package com.spf.common.interceptor;

import com.spf.common.arithmetic.DES;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by IntelliJ IDEA
 * <p>〈〉 </p>
 * 〈〉
 *
 * @author linxm
 * @date 2017/5/4
 * @time 19:05
 * @version 1.0
 */
@Slf4j
@Component
public class HeadInterceptor implements HandlerInterceptor{
    private ThreadLocal<Long> requestTime = new ThreadLocal<Long>();

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        log.info("===================>请求地址"+request.getRequestURI()+"开始");
        requestTime.set(System.currentTimeMillis());
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
        long statTime = requestTime.get().longValue();
        long endTime = System.currentTimeMillis();
        log.info("===================>请求地址"+request.getRequestURI()+"结束,耗时===>"+(endTime - statTime)+"ms");
    }
}
