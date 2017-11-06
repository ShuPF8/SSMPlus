package com.spf.common.httpClientUtils;

import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/** HttpClient 连接工具类
 * Created by ShuPF on 2017/11/5.
 */
public class HttpClientUtil {

    private static String CHARSET = "UTF-8";

    protected static final String User_Agent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36";

    protected static RequestConfig config;

    protected static PoolingHttpClientConnectionManager connManager;

    private static HttpClientUtil hcu = null;

    /**
     * 获取 HttpClientUtil 实例
     * @return
     */
    public static HttpClientUtil getInstance() {
        if (hcu == null) {
            hcu = new HttpClientUtil();
        }
        config = RequestConfig.custom()
                // .setProxy(new HttpHost("myotherproxy", 8080)) //
                .setSocketTimeout(10000) //
                .setConnectTimeout(10000) //
                .setConnectionRequestTimeout(30000).build();

        SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
        try {
            sslContextBuilder.loadTrustMaterial(null, new TrustSelfSignedStrategy());

            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContextBuilder.build());
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("https", socketFactory).register("http", new PlainConnectionSocketFactory()).build();

            connManager = new PoolingHttpClientConnectionManager( //
                    socketFactoryRegistry, null, null, null, 300L, TimeUnit.SECONDS);

            connManager.setMaxTotal(200);
            connManager.setDefaultMaxPerRoute(20);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hcu;
    }

    /**
     * 获取连接
     * @return
     */
    public CloseableHttpClient getConnect() {
        CloseableHttpClient client = HttpClients.custom()
                // .setDefaultCredentialsProvider(credentialsProvider) //
                //.setProxy(new HttpHost("182.129.248.215",4346))
                .setDefaultRequestConfig(config).setConnectionManager(connManager).build();
        return client;
    }

    /**
     *  post 请求
     * @param client 连接
     * @param url 地址
     * @param paramsMap 参数
     * @param vars 需要接受的数据 Map
     * @param connectTimeoutMillSec 设置连接超时时间
     * @param readTimeoutMillSec 设置读取超时时间
     * @return
     * @throws Exception
     */
    public String post(CloseableHttpClient client, String url, Map<String, String> paramsMap, Map<String, Object> vars, int connectTimeoutMillSec, int readTimeoutMillSec) throws Exception {
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            method.addHeader("User-Agent", User_Agent);
            if (vars.get("Referer") != null) {
                method.addHeader("Referer",String.valueOf(vars.remove("Referer")));
            }
            if (connectTimeoutMillSec > 10000 && readTimeoutMillSec > 10000) {
                RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(connectTimeoutMillSec).setConnectTimeout(readTimeoutMillSec).build();// 设置请求和传输超时时间
                method.setConfig(requestConfig);
            }

            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, CHARSET));
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                throw new Exception(e);
            }
        }
        vars.put("data", responseText);
        return responseText;
    }

    /**
     *  get 请求
     * @param client 连接
     * @param url 地址
     * @param vars 需要接受的数据 Map
     * @param connectTimeoutMillSec 设置连接超时时间
     * @param readTimeoutMillSec 设置读取超时时间
     * @return
     * @throws Exception
     */
    public String get(CloseableHttpClient client , String url, Map<String, Object> vars, int connectTimeoutMillSec, int readTimeoutMillSec) throws Exception {
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
            if (vars.get("Referer") != null) {
                httpGet.addHeader("Referer",String.valueOf(vars.remove("Referer")));
            }
            if (connectTimeoutMillSec > 10000 && readTimeoutMillSec > 10000) {
                RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(connectTimeoutMillSec).setConnectTimeout(readTimeoutMillSec).build();// 设置请求和传输超时时间
                httpGet.setConfig(requestConfig);
            }

            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String charset = "UTF-8";
            if (entity != null) {
                charset = getContentCharSet(entity);
                responseText =  EntityUtils.toString(entity, charset);
            }
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        vars.put("data",responseText);
        return responseText;
    }

    /**
     * 设置编码
     * @param entity
     * @return
     * @throws org.apache.http.ParseException
     */
    private String getContentCharSet(final HttpEntity entity)
            throws org.apache.http.ParseException {
        if (entity == null) {
            throw new IllegalArgumentException("HTTP entity may not be null");
        }
        String charset = null;
        if (entity.getContentType() != null) {
            HeaderElement values[] = entity.getContentType().getElements();
            if (values.length > 0) {
                NameValuePair param = values[0].getParameterByName("charset" );
                if (param != null) {
                    charset = param.getValue();
                }
            }
        }
        if("".equals(charset) || null == charset){
            charset = "UTF-8";
        }
        return charset;
    }
}
