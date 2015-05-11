package com.ruhua.common.response;

import com.ruhua.domain.constants.ServiceResponseConstants;

/**
 * Created by quyang on 15/4/15.
 */
public class EntityJDResult<T> extends JDResult
{
    private T data;

    public EntityJDResult()
    {
    }

    public EntityJDResult(T result)
    {
        this.data = result;
    }

    public EntityJDResult(String code, String msg) {
        super(code, msg);
    }


    public EntityJDResult(boolean bool ,String code, String msg) {
        super(code, msg);
    }

    public EntityJDResult(String code, String msg, String detail) {
        super(code, msg, detail);
    }

    public EntityJDResult(String errorCodeEnum) {
        super(errorCodeEnum);
    }

    public EntityJDResult(ServiceResponseConstants errorCodeEnum, String detail) {
        super(errorCodeEnum, detail);
    }

    public EntityJDResult(String code, String msg, T result) {
        super(code, msg);
        this.data = result;
    }

    public EntityJDResult(String errorCodeEnum, T result) {
        super(errorCodeEnum);
        this.data = result;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T result) {
        this.data = result;
    }
}