package com.ruhua.common;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * HttpClient工具类.
 * User: gezhiqiang
 * Date: 14-5-13
 * Time: 下午12:01
 */
public class HttpClientUtils {

    /**
     * 返回HttpClient单例.
     *
     * @return HttpClient单例
     */
    public static CloseableHttpClient getHttpClient() {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        //CloseableHttpClient closeableHttpClient;
        return httpClientBuilder.build();

    }

    public static RequestConfig getDefaultRequestConfig() {
        return RequestConfig.custom()
                .setCookieSpec(CookieSpecs.BEST_MATCH)
                .setExpectContinueEnabled(true)
                .setStaleConnectionCheckEnabled(true)
                .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
                .build();
    }

    /**
     * 返回默认自定义的RequestConfig单例.
     *
     * @return RequestConfig单例
     */
    public static RequestConfig getCustomRequestConfig() {
        RequestConfig defaultRequestConfig = getDefaultRequestConfig();

        return RequestConfig.copy(defaultRequestConfig)
                .setSocketTimeout(15000)//Socket读数据的超时时间，即从服务器获取响应数据需要等待的时间
                .setConnectTimeout(15000)//网络与服务器建立连接的超时时间
                .setConnectionRequestTimeout(15000)
                .build();
    }

    /**
     * 返回自定义的RequestConfig单例.
     *
     * @param config HttpConfig
     * @return 自定义的RequestConfig单例
     */
    public static RequestConfig getCustomRequestConfig(HttpConfig config) {
        RequestConfig defaultRequestConfig = getCustomRequestConfig();

        RequestConfig.Builder builder = RequestConfig.copy(defaultRequestConfig);

        if (config != null) {
            if (config.getSocketTimeout() != 0) {
                builder.setSocketTimeout(config.getSocketTimeout());
            }
            if (config.getConnectTimeout() != 0) {
                builder.setConnectTimeout(config.getConnectTimeout());
            }
            if (config.getConnectionRequestTimeout() != 0) {
                builder.setConnectionRequestTimeout(config.getConnectionRequestTimeout());
            }
        }

        return builder.build();
    }


    /**
     * 获取自定义配置
     *
     * @param config 自定义配置
     * @return 自定义配置对象
     */
    private static RequestConfig setRequestConfig(HttpConfig config) {
        RequestConfig requestConfig;
        if (config != null) {
            requestConfig = getCustomRequestConfig(config);
        } else {
            requestConfig = getCustomRequestConfig();
        }
        return requestConfig;
    }

    /**
     * 设置cookie信息.
     *
     * @param cookies  cookie数组
     * @param httpPost post请求
     */
    private static void setCookies(Cookie[] cookies, HttpPost httpPost) {
        //增加cookie信息
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                httpPost.addHeader("Cookie", cookie.getValue());
            }
        }
    }

    /**
     * 设置cookie信息.
     *
     * @param cookies cookie数组
     * @param httpGet post请求
     */
    private static void setCookies(Cookie[] cookies, HttpGet httpGet) {
        //增加cookie信息
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                httpGet.addHeader("Cookie", cookie.getValue());
            }
        }
    }

    /**
     * 获取持久化cookie对象.
     *
     * @return 获取持久化cookie对象
     */
    private static HttpClientContext getHttpClientContext() {
        CookieStore cookieStore = new BasicCookieStore();
        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);
        return context;
    }

    /**
     * Http post请求.
     *
     * @param url        url地址
     * @param formparams 传递参数
     * @param encoding   编码
     * @param config     HttpConfig配置
     * @return 返回结果
     */
    public static HttpResult post(String url, List<NameValuePair> formparams, String encoding, HttpConfig config) {
        return post(url, formparams, encoding, config, null);
    }

    /**
     * Http post请求.
     *
     * @param url        url地址
     * @param formparams 传递参数
     * @param encoding   编码
     * @param config     HttpConfig配置
     * @param cookies    cookie数组
     * @return 返回结果
     */
    public static HttpResult post(String url, List<NameValuePair> formparams, String encoding, HttpConfig config, Cookie[] cookies) {
        HttpResult result = new HttpResult();

        CloseableHttpClient closeableHttpClient = getHttpClient();
        RequestConfig requestConfig = setRequestConfig(config);//自定义配置

        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);//增加自定义配置
        setCookies(cookies, httpPost);//设置cookie信息

        HttpClientContext context = getHttpClientContext();//创建cookie持久化

        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, encoding);
            httpPost.setEntity(entity);

            //计算HTTP请求时长
            long startTime = System.currentTimeMillis();
            HttpResponse httpResponse = closeableHttpClient.execute(httpPost, context);
            long endTime = System.currentTimeMillis();

            if (HttpStatus.SC_OK != httpResponse.getStatusLine().getStatusCode()) {//请求失败
                result.setCode(HttpResultEnum.FAILED.getCode());
                result.setMsg(HttpResultEnum.FAILED.getMsg());
                return result;
            }

//            Header[] header1 = httpResponse.getHeaders("Set-Cookie");
//            for (Header header : header1) {
//                header.getName();
//            }
//            List<Cookie> returnCookies = context.getCookieStore().getCookies();

            //获得返回数据
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                result.setResult(EntityUtils.toString(httpEntity, encoding));
            }

        } catch (ClientProtocolException e) {
            result.setCode(HttpResultEnum.PROTOCOLERROR.getCode());
            result.setMsg(HttpResultEnum.PROTOCOLERROR.getMsg());
        } catch (UnsupportedEncodingException e) {
            result.setCode(HttpResultEnum.UNSUPPORTEDENCODING.getCode());
            result.setMsg(HttpResultEnum.UNSUPPORTEDENCODING.getMsg());
        } catch (IOException e) {
            result.setCode(HttpResultEnum.IOERROR.getCode());
            result.setMsg(HttpResultEnum.IOERROR.getMsg());
        } finally {
            try {
                if (closeableHttpClient != null) {
                    closeableHttpClient.close();
                }
            } catch (IOException e) {
                result.setCode(HttpResultEnum.CLOSEERROR.getCode());
                result.setMsg(HttpResultEnum.CLOSEERROR.getMsg());
            }
        }
        return result;
    }

    /**
     * Http get请求.
     *
     * @param url      url地址
     * @param encoding 编码
     * @param config   HttpConfig配置
     * @param cookies  cookie数组
     * @return 返回结果
     */
    public static HttpResult get(String url, String encoding, HttpConfig config, Cookie[] cookies) {
        HttpResult result = new HttpResult();

        CloseableHttpClient closeableHttpClient = getHttpClient();
        RequestConfig requestConfig = setRequestConfig(config);//自定义配置

        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);//增加自定义配置
        setCookies(cookies, httpGet);//设置cookie信息

        HttpClientContext context = getHttpClientContext();//创建cookie持久化

        try {
            //计算HTTP请求时长
            long startTime = System.currentTimeMillis();
            HttpResponse httpResponse = closeableHttpClient.execute(httpGet, context);
            long endTime = System.currentTimeMillis();

            if (HttpStatus.SC_OK != httpResponse.getStatusLine().getStatusCode()) {//请求失败
                result.setCode(HttpResultEnum.FAILED.getCode());
                result.setMsg(HttpResultEnum.FAILED.getMsg());
                return result;
            }

//            Header[] header1 = httpResponse.getHeaders("Set-Cookie");
//            for (Header header : header1) {
//                header.getName();
//            }
//            List<Cookie> returnCookies = context.getCookieStore().getCookies();

            //获得返回数据
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                result.setResult(EntityUtils.toString(httpEntity, encoding));
            }

        } catch (ClientProtocolException e) {
            result.setCode(HttpResultEnum.PROTOCOLERROR.getCode());
            result.setMsg(HttpResultEnum.PROTOCOLERROR.getMsg());
        } catch (UnsupportedEncodingException e) {
            result.setCode(HttpResultEnum.UNSUPPORTEDENCODING.getCode());
            result.setMsg(HttpResultEnum.UNSUPPORTEDENCODING.getMsg());
        } catch (IOException e) {
            result.setCode(HttpResultEnum.IOERROR.getCode());
            result.setMsg(HttpResultEnum.IOERROR.getMsg());
        } catch(Exception e){
            result.setCode(-1);
            result.setMsg("未知异常");
            e.printStackTrace();
        }finally {
            try {
                if (closeableHttpClient != null) {
                    closeableHttpClient.close();
                }
            } catch (Exception e) {
                result.setCode(HttpResultEnum.CLOSEERROR.getCode());
                result.setMsg(HttpResultEnum.CLOSEERROR.getMsg());
            }
        }
        return result;
    }

    public static HttpResult postJson(String url, String param, HashMap<String, String> map){
        HttpResult result = new HttpResult();
        CloseableHttpClient client = getHttpClient();
        try{
            HttpPost httpPost = new HttpPost(url);
            for(String key : map.keySet()){
                httpPost.addHeader(key, map.get(key));
            }
            StringEntity entity = new StringEntity(param, HTTP.UTF_8);
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            HttpResponse httpResponse = client.execute(httpPost);
            if (HttpStatus.SC_OK != httpResponse.getStatusLine().getStatusCode()) {//请求失败
                result.setCode(HttpResultEnum.FAILED.getCode());
                result.setMsg(HttpResultEnum.FAILED.getMsg());
                return result;
            }
            //获得返回数据
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                result.setResult(EntityUtils.toString(httpEntity, HTTP.UTF_8));
            }
        } catch (Exception e){
            result.setCode(HttpResultEnum.IOERROR.getCode());
            result.setMsg(HttpResultEnum.IOERROR.getMsg());
        } finally {
            try {
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
                result.setCode(HttpResultEnum.CLOSEERROR.getCode());
                result.setMsg(HttpResultEnum.CLOSEERROR.getMsg());
            }
        }
        return result;
    }

    public static HttpResult postJson(String url, String param){
        HttpResult result = new HttpResult();
        CloseableHttpClient client = getHttpClient();
        try{
            HttpPost httpPost = new HttpPost(url);
            StringEntity entity = new StringEntity(param, HTTP.UTF_8);
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            HttpResponse httpResponse = client.execute(httpPost);
            if (HttpStatus.SC_OK != httpResponse.getStatusLine().getStatusCode()) {//请求失败
                result.setCode(HttpResultEnum.FAILED.getCode());
                result.setMsg(HttpResultEnum.FAILED.getMsg());
                return result;
            }
            //获得返回数据
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                result.setResult(EntityUtils.toString(httpEntity, HTTP.UTF_8));
            }
        } catch (Exception e){
            result.setCode(HttpResultEnum.IOERROR.getCode());
            result.setMsg(HttpResultEnum.IOERROR.getMsg());
        } finally {
            try {
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
                result.setCode(HttpResultEnum.CLOSEERROR.getCode());
                result.setMsg(HttpResultEnum.CLOSEERROR.getMsg());
            }
        }
        return result;
    }
}
