package com.ruhua.domain.binding;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 15-2-5
 * Time: 下午4:33
 * To change this template use File | Settings | File Templates.
 */
public class Binding implements Serializable {

    private String openid;
    private String erp;
    private String q;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getErp() {
        return erp;
    }

    public void setErp(String erp) {
        this.erp = erp;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }
}
