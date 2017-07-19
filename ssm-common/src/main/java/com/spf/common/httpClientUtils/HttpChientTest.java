package com.spf.common.httpClientUtils;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author SPF
 * @Date 2017/5/25
 */
public class HttpChientTest {

    private  static final ObjectMapper om = new ObjectMapper();

    @Test
    public void test() throws IOException {
        HttpClientUtils httpClientUtils = new HttpClientUtils();
        Map<String, String> map = new HashMap<String, String>();

        String response = httpClientUtils.doPost("http://localhost:9966/testfindAll", map);
        System.out.println(response);
    }
}
