package com.ruhua.common;

/**
 * Created with IntelliJ IDEA.
 * User: gezhiqiang
 * Date: 14-5-13
 * Time: 下午2:37
 */
public enum HttpResultEnum {
    SUCCESS(0, "请求成功"),
    FAILED(1, "请求失败"),
    PROTOCOLERROR(-1, "HTTP协议错误"),
    UNSUPPORTEDENCODING(-2,"编码不支持错误"),
    IOERROR(-3,"读取IO流错误"),
    CLOSEERROR(-4,"关闭HttpClient错误");

    private int code;
    private String msg;

    private HttpResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

