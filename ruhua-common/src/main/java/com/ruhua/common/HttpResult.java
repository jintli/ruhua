package com.ruhua.common;

import java.io.InputStream;

/**
 * Http返回结果domain.
 * User: gezhiqiang
 * Date: 14-5-13
 * Time: 下午2:27
 */
public class HttpResult {

    private int code;
    private String msg;
    private String result;
    private InputStream is;

    public HttpResult() {
        this.code = HttpResultEnum.SUCCESS.getCode();
        this.msg = HttpResultEnum.SUCCESS.getMsg();
    }

    public HttpResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public InputStream getIs() {
        return is;
    }

    public void setIs(InputStream is) {
        this.is = is;
    }
}
