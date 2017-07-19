package com.spf.common.interceptor;

import com.spf.common.BaseUtils;
import com.spf.common.arithmetic.AES;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/16 0016.
 */
@Slf4j
public class SignInterceptor implements HandlerInterceptor {


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        Map<String, Object> map = BaseUtils.getParamMap(request);
        Object osign = map.get("sign");
        if (osign == null) {
            return false;
        }
        map.put("xm-name","SSM");
        map.put("dev-name","spf");
        map.remove("sign");
        String str = BaseUtils.createLinkString(map);
        String key = "abcdefgabcdefg12";
        String sign = AES.encryptToBase64(str,key);

        if (!sign.equals(osign.toString())) {
            log.info("===================>签名验证失败<========================");
            return false;
        }
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
