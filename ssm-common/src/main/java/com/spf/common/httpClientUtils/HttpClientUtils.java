package com.spf.common.httpClientUtils;


import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author SPF
 * @Date 2017/5/25
 */
public class HttpClientUtils {

    public static String post(String url, Map<String, Object> map) {
        CloseableHttpClient client =null;
        HttpPost post = null;
        CloseableHttpResponse response = null;
        String result = null;
        try {
            client = HttpClients.createDefault();
            post = new HttpPost(url);

            //设置超时时间
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(3000) //设置链接超时时间
                    .setSocketTimeout(1000)//设置读取超时时间
                    .build();
            post.setConfig(requestConfig);

            //设置请求头信息，模拟浏览器请求
            post.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.86 Safari/537.36");

            //设置参数
            if(map != null && !map.isEmpty()) {
                List<NameValuePair> list = new ArrayList<NameValuePair>();
                Iterator iterator = map.entrySet().iterator();
                while(iterator.hasNext()){
                    Map.Entry<String,Object> elem = (Map.Entry<String, Object>) iterator.next();
                    list.add(new BasicNameValuePair(elem.getKey(),elem.getValue().toString()));
                }
                if(list.size() > 0){
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,"UTF-8");
                    post.setEntity(entity);
                }
            }
            //执行请求
            response = client.execute(post);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,"UTF-8");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
