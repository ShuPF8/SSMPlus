package com.spf.controller.captcha;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.touclick.captcha.exception.TouclickException;
import com.touclick.captcha.model.Status;
import com.touclick.captcha.service.TouClick;

/**
* @ClassName: TouclickController
* @Description: 请求二次验证的服务端
* @author zhanwei
* @date 2016年5月17日 下午4:32:42
* @version 1.0
 */
@Controller
public class TouclickController {
    
    private TouClick touclick = new TouClick(); 
    
    private static final String PUBKEY = "602b0eb2-d829-4b73-ae78-89c2adeff123";//公钥(从点触官网获取)
    private static final String PRIKEY = "597eb415-c374-4202-985f-76ebe275f882";//私钥(从点触官网获取)

    @RequestMapping(value = "/captcha/index")
    public String index() {
        return "captcha/index";
    }

    /**
     * @throws TouclickException 
    * @Title: verify
    * @Description: 服务端请求TouClick二次验证
    * @param @param request
    * @param @param response    设定文件
    * @return void    返回类型
    * @throws
    * 0, 验证正确
    * 1, 该验证已过期
    * 2, 公钥不可为空
    * 3, 一次验证返回的token为必需参数,不可为空
    * 4, 公钥不正确
    * 5, CheckCode有误,请确认CheckCode是否和一次验证传递一致
    * 6, sign加密错误,请检查参数是否正确
    * 7, 一次验证错误
    * 8, 点触服务器异常
    * 9, http请求异常
    * 10, json转换异常,可能是请求地址有误,请检查请求地址(http://[checkAddress].touclick.com/sverify.touclick?参数)
    * 11, 二次验证地址不合法
    * 12, 签名校验失败,数据可能被篡改
     */
    @RequestMapping(value = "/postdata",method = RequestMethod.POST)
    public void  verify(final HttpServletRequest request,HttpServletResponse response) throws TouclickException{
    	/*
    	*  token 二次验证口令，单次有效
    	*  checkAddress 二次验证地址，二级域名
    	*  checkCode 校验码，开发者自定义，一般采用手机号或者用户ID，用来更细致的频次控制
    	*/
        String checkAddress = request.getParameter("checkAddress");
        String token = request.getParameter("token");
        String sid = request.getParameter("sid");
        Status status = touclick.check(checkAddress,sid,token,PUBKEY,PRIKEY);
        System.out.println("checkAddress :"+checkAddress + ",token:" + token);
        System.out.println("code :"+status.getCode() + ",message:" + status.getMessage());
        if(status != null && status.getCode()==0){
            //执行自己的程序逻辑
            System.out.println("验证成功");
        }
        
    }
    
}
