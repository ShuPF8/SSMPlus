package com.spf.common;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class BaseUtils{
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	protected ServletContext application;
	private  static final ObjectMapper om = new ObjectMapper();
	public static Random random = new Random();
	private static final Logger logger = Logger.getLogger(BaseUtils.class);
	
	/**
     * 将字符串返回给前端
     * @param str
	 * @throws JSONException 
     */
	 public void backClient(String str){
		PrintWriter pw = null;
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/application;charset=utf-8");
		try {
			pw = response.getWriter();
			JSONObject json = new JSONObject(str);
			pw.write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	 }

	/**
      * 将对象转换成json字符串
      * @param data
      * @return
	  * @throws IOException 
      */
      public static String toJSONString(Object data) {
         try {
             String string = om.writeValueAsString(data);
             return string;
         } catch (IOException e) {
             e.printStackTrace();
         }
         return null;
      }


   /**
	 * 将json数据转化为对象
	 * @param jsonData
	 * @param beanType
	 * @return
	 */
     public static <T> T parseObject(String jsonData, Class<T> beanType) {
    	 try {
			T t = om.readValue(jsonData, beanType);
			return t;
		} catch (IOException e) {
			e.printStackTrace();
		}
    	 return null;
     }
     
     /**
      * 将json数据转化为List
      * @param jsonData
      * @param beanType
      * @return
      */
     public static <T> List<T> parseArray(String jsonData, Class<T> beanType) {
    	 JavaType javaType = om.getTypeFactory().constructParametricType(List.class, beanType);
    	 try {
			List<T> list = om.readValue(jsonData, javaType);
			 return list;
		}catch (IOException e) {
			e.printStackTrace();
		}
    	 return null;
     }

	/**
	 * 检测参数是否全不为空 是=true,否=false
	 * @param parms
	 * @return
	 */
	public static boolean checkNotNull(String...parms) {
		String [] strs = parms;
		for (String str : parms) {
			if (StringUtils.isEmpty(str)) {
				return false;
			}
		}
		return true;
	 }

	/**
	 * 检测空值并抛出 空指针 异常
	 * @param parms 参数
	 * @param errorMessage 提示消息
	 * @return
	 */
	public static void checkNullAndThrow(Object errorMessage, String...parms) {
		for (int i = 1; i<= parms.length; i++) {
			if (StringUtils.isEmpty(parms[i - 1])) {
				throw new NullPointerException(String.valueOf("第 "+ i + " 个"+errorMessage));
			}
		}
	}

	/**
	 * 获取request 中的参数，以map形式返回
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getParamMap(ServletRequest request) {
		//Assert.notNull(request,"参数不能为空");
		Map<String, Object> map = Maps.newHashMap();
		Enumeration<String> en = request.getParameterNames();
		while (en.hasMoreElements()) {
			String name = en.nextElement();
			String[] values = request.getParameterValues(name);
			if (values == null || values.length == 0) {
				continue;
			}
			String value = values[0];
			if (value != null) {
				map.put(name, value);
			}
		}
		return map;
	}

	/**
	 * 把数组所有元素，按字母排序，然后按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * @param params 需要签名的参数
	 * @return 签名的字符串
	 */
	public static String createLinkString(Map<String, Object> params) {
		List<String> keys = Lists.newArrayList(params.keySet().iterator());
		Collections.sort(keys);
		StringBuilder signStr = new StringBuilder();
		for (String key : keys) {
			if (!checkNotNull(params.get(key).toString())) {
				continue;
			}
			signStr.append(key).append("=").append(params.get(key)).append("&");
		}
		return signStr.deleteCharAt(signStr.length() - 1).toString();
	}

	public static String getRandom(int length) {
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < length; i++) {
			boolean isChar = (random.nextInt(2) % 2 == 0);// 输出字母还是数字
			if (isChar) { // 字符串
				int choice = (random.nextInt(2) % 2 == 0) ? 65 : 97; // 取得大写字母还是小写字母
				ret.append((char) (choice + random.nextInt(26)));
			} else { // 数字
				ret.append(Integer.toString(random.nextInt(10)));
			}
		}
		return ret.toString();
	}

	@ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){
       this.request = request;
       this.response = response;
       this.session = request.getSession();
       this.application = session.getServletContext();
    }
	
}
