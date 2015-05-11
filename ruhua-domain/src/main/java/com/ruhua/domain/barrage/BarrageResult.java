package com.ruhua.domain.barrage;

/**
 * Created with IntelliJ IDEA.
 * User: tangyawen
 * Date: 2014/12/24
 * Time: 16:55
 * To change this template use File | Settings | File Templates.
 */
public class BarrageResult<T> implements java.io.Serializable {
    private static final long serialVersionUID = -3008571493355733566L;
    private boolean isRet;
    private String retCode;
    private String retMsg;
    private T data;

    public BarrageResult() {
    }

    public BarrageResult(boolean isRet, String retCode, String retMsg) {
        this.isRet = isRet;
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public boolean isRet() {
        return isRet;
    }

    public void setRet(boolean isRet) {
        this.isRet = isRet;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BarrageResult{" +
                "isRet=" + isRet +
                ", retCode='" + retCode + '\'' +
                ", retMsg='" + retMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
