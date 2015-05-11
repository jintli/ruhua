package com.ruhua.common;

/**
 * 字符集枚举类型.
 * User: gezhiqiang
 * Date: 14-5-13
 * Time: 上午11:09
 */
public enum CharSet {

    UTF8("UTF-8"),
    GBK("GBK");

    private String val;

    private CharSet(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}
