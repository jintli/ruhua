package com.ruhua.common.response;

import com.ruhua.domain.constants.ServiceResponseConstants;

import java.io.Serializable;

/**
 * Created by quyang on 15/4/15.
 */
public class JDResult implements Serializable{
    private String retCode;
    private String retMsg;
    private String detail;


    public JDResult() {
    }

    public JDResult(String code, String msg) {
        this.retCode = code;
        this.retMsg = msg;
    }

    public JDResult(String code, String msg, String detail) {
        this.retCode = code;
        this.retMsg = msg;
        this.detail = detail;
    }

    public JDResult(boolean bool,String code, String msg) {
        this.retCode = code;
        this.retMsg = msg;
    }

    public JDResult(String errorCodeEnum) {
        this.retCode = errorCodeEnum;
        this.retMsg = errorCodeEnum;
    }

    public JDResult(ServiceResponseConstants errorCodeEnum, String detail) {
        this.retCode = errorCodeEnum.getCode();
        this.retMsg = errorCodeEnum.getMsg();
        this.detail = detail;
    }

    public void setErrorCodeEnum(ServiceResponseConstants errorCodeEnum) {
        this.retCode = errorCodeEnum.getCode();
        this.retMsg = errorCodeEnum.getMsg();
    }

    public String getRetCode()
    {
        return this.retCode;
    }

    public void setRetCode(String code)
    {
        this.retCode = code;
    }

    public String getRetMsg()
    {
        return this.retMsg;
    }

    public void setRetMsg(String msg)
    {
        this.retMsg = msg;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public <T extends JDResult> JDResult addDetail(String detail) {
        setDetail(detail);
        return this;
    }
}
