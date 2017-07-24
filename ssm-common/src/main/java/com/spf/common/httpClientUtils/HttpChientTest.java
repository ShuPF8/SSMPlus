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
        Map<String, Object> map = new HashMap<String, Object>();

        String response = httpClientUtils.post("http://localhost:9404/findAll", map);
        System.out.println(response);
    }
}
