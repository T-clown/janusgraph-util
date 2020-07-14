package com.janusgraph.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

@Slf4j
public class HttpUtil {

    private static PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
    private static String EMPTY_STR = "";
    private static String UTF_8 = "UTF-8";

    static {
        // 整个连接池最大连接数
        connectionManager.setMaxTotal(50);
        // 每路由最大连接数，默认值是2
        connectionManager.setDefaultMaxPerRoute(5);

    }

    /**
     * 通过连接池获取HttpClient
     *
     * @return
     */
    public static CloseableHttpClient getHttpClient() {
        return HttpClients.custom().setConnectionManager(connectionManager).build();
    }

    public static String httpGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        return getResult(httpGet);
    }

    public static String httpGet(String url, Map<String, String> headers, Map<String, Object> params)
        throws URISyntaxException {
        URIBuilder ub = new URIBuilder();
        ub.setPath(url);

        List<NameValuePair> pairs = covertParams2NVPS(params);
        ub.setParameters(pairs);

        HttpGet httpGet = new HttpGet(ub.build());
        if (MapUtils.isNotEmpty(headers)) {
            headers.forEach(httpGet::addHeader);
        }
        return getResult(httpGet);
    }

    public static String httpPost(String url) {
        HttpPost httpPost = new HttpPost(url);
        return getResult(httpPost);
    }

    public static String httpPost(String url, Map<String, Object> params) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> pairs = covertParams2NVPS(params);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));
        return getResult(httpPost);
    }

    public static String httpPostRequest(String url, Map<String, String> headers, Map<String, Object> params)
        throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);
        if (MapUtils.isNotEmpty(headers)) {
            headers.forEach(httpPost::addHeader);
        }

        List<NameValuePair> pairs = covertParams2NVPS(params);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));

        return getResult(httpPost);
    }

    public static String httpPostRequest(String url, Map<String, String> headers, String json)
        throws Exception {
        HttpPost httpPost = new HttpPost(url);
        if (MapUtils.isNotEmpty(headers)) {
            headers.forEach(httpPost::addHeader);
        }
        httpPost.setEntity(new StringEntity(json, UTF_8));
        return getResult(httpPost);
    }

    private static List<NameValuePair> covertParams2NVPS(Map<String, Object> params) {
        List<NameValuePair> pairs = Lists.newArrayList();
        params.forEach((k, v) -> pairs.add(new BasicNameValuePair((String)k, (String)v)));
        return pairs;
    }

    /**
     * 处理Http请求
     *
     * setConnectTimeout：设置连接超时时间，单位毫秒。
     * setConnectionRequestTimeout：设置从connect Manager获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
     * setSocketTimeout：请求获取数据的超时时间，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
     *
     * @param request
     * @return
     */
    private static String getResult(HttpRequestBase request) {

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(60000)
            .setConnectionRequestTimeout(5000).setSocketTimeout(60000).build();
        request.setConfig(requestConfig);// 设置请求和传输超时时间

        // CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpClient httpClient = getHttpClient();
        try {
            CloseableHttpResponse response = httpClient.execute(request); //执行请求
            // response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // long len = entity.getContentLength();// -1 表示长度未知
                String result = EntityUtils.toString(entity);
                response.close();
                // httpClient.close();
                return result;
            }
        } catch (ClientProtocolException e) {
            log.error("[maperror] HttpClientUtil ClientProtocolException : {}", e.getMessage());
        } catch (IOException e) {
            log.error("[maperror] HttpClientUtil IOException :{} ", e.getMessage());
        } finally {

        }
        return EMPTY_STR;
    }

}