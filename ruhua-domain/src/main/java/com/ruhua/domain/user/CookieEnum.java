package com.ruhua.domain.user;

/**
 * Created with IntelliJ IDEA.
 * User: jiaohuaisheng
 * Date: 14-8-26
 * Time: 下午1:24
 * To change this template use File | Settings | File Templates.
 */
public enum CookieEnum {
    //cookie 里面 sid的key
    SIDKEY("ruhua_sid");
    private String key;
    CookieEnum(String key) {
      this.key=key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
