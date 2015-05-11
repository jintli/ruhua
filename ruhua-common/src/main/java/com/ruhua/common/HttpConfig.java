package com.ruhua.common;

/**
 * Created with IntelliJ IDEA.
 * User: gezhiqiang
 * Date: 14-5-13
 * Time: 下午2:24
 */
public class HttpConfig {
    private int socketTimeout;//Socket读数据的超时时间，即从服务器获取响应数据需要等待的时间
    private int connectTimeout;//网络与服务器建立连接的超时时间
    private int connectionRequestTimeout;

    public HttpConfig() {
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }
}
