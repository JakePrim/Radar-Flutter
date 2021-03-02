package com.sfl.http;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.util.UriBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @program: edu-web
 * @Description: 请求网络的工具类
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-27 17:39
 * @PackageName: com.sfl.http
 * @ClassName: HttpClientUtils.java
 **/
public class HttpClientUtils {

    public static String doGet(String url) {
        return doGet(url, null);
    }

    /**
     * get请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String doGet(String url, Map<String, String> params) {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;

        try {
            URIBuilder builder = new URIBuilder(url);
            if (params != null) {
                // 在url后面拼接请求参数
                for (String key : params.keySet()) {
                    builder.addParameter(key, params.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http get请求
            HttpGet httpGet = new HttpGet(uri);
// 执行请求
            response = httpClient.execute(httpGet);
// 从响应对象中获取状态码(成功或失败的状态)
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("响应的状态 = " + statusCode);
// 200表示响应成功
            if (statusCode == 200) {
                // 响应的内容字符串
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }
}
