package com.ruhua.common.response;

import com.ruhua.domain.constants.ServiceResponseConstants;

import java.util.Collection;

/**
 * Created by quyang on 15/4/15.
 */
public class SearchJDResult<T> extends JDResult
{
    private Collection<T> result;

    public SearchJDResult()
    {
    }

    public SearchJDResult(String code, String msg)
    {
        super(code, msg);
    }

    public SearchJDResult(String code, String msg, String detail) {
        super(code, msg, detail);
    }

    public SearchJDResult(String errorCodeEnum) {
        super(errorCodeEnum);
    }

    public SearchJDResult(ServiceResponseConstants errorCodeEnum, String detail) {
        super(errorCodeEnum, detail);
    }

    public SearchJDResult(Collection<T> result) {
        this.result = result;
    }

    public SearchJDResult(String code, String msg, Collection<T> result) {
        super(code, msg);
        this.result = result;
    }

    public SearchJDResult(String errorCodeEnum, Collection<T> result) {
        super(errorCodeEnum);
        this.result = result;
    }

    public <C extends Collection<T>> C getResult() {
        return (this.result == null) ? null : (C) this.result;
    }

    public void setResult(Collection<T> result) {
        this.result = result;
    }
}
