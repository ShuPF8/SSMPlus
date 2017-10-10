package com.spf.common.httpClientUtils;

import okhttp3.*;

import java.io.IOException;

/**
 * Created by Administrator on 2017/10/9.
 */
public class OKHttpClient {

    public static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");

    public static String httpGet(String url) throws IOException {
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = httpClient.newCall(request).execute();
        return response.body().string(); // 返回的是string 类型，json的mapper可以直接处理
    }

    public static String httpPost(String url, String json) throws IOException {
        OkHttpClient httpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Response response = httpClient.newCall(request).execute();
        return response.body().string();
    }

}
