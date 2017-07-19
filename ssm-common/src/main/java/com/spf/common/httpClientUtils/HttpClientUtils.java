package com.spf.common.httpClientUtils;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author SPF
 * @Date 2017/5/25
 */
@Component("httpClientUtils")
public class HttpClientUtils {
    private MediaType mediaType = MediaType.parse("application/json;charset=utf-8");

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected final OkHttpClient okHttpClient = new OkHttpClient().newBuilder().connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(10, TimeUnit.MINUTES)
            .build();

    public String doPostJson(String url,String jsonStr) throws IOException {
        RequestBody requestBody = FormBody.create(mediaType,jsonStr);
        return post(url,requestBody);
    }

    public String doPost(String url, Map<String,String> paramsMap) throws IOException {
        FormBody.Builder formBody = new FormBody.Builder();
        for (Map.Entry<String,String> map : paramsMap.entrySet()) {
            formBody.addEncoded(map.getKey(),map.getValue());
        }
        return post(url,formBody.build());
    }

    protected String post(String url, RequestBody requestBody) throws IOException {

        try {

            Request request = new Request.Builder()
                    .url(url).post(requestBody)
                    .build();
            Response response = okHttpClient.newCall(request).execute();

            String responseStr = response.body().string();

            logger.debug("返回 Response：" + responseStr);

            return responseStr;
        } catch (ConnectException ex) {
            logger.error("连接超时",ex);
            throw new RuntimeException("连接超时");
        } catch (Exception e) {
            logger.error("发起请求失败",e.getMessage());
            throw new RuntimeException("发起请求失败");
        }

    }

}
