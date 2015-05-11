package com.ruhua.domain.date;

import com.ruhua.domain.user.UserInfo;

/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-12-6
 * Time: 下午5:19
 * To change this template use File | Settings | File Templates.
 */
public class DateAllInfo extends DateUser {
    private double shopLng;
    private double shopLat;
    private UserInfo meInfo;
    private UserInfo youInfo;

    public double getShopLng() {
        return shopLng;
    }

    public void setShopLng(double shopLng) {
        this.shopLng = shopLng;
    }

    public double getShopLat() {
        return shopLat;
    }

    public void setShopLat(double shopLat) {
        this.shopLat = shopLat;
    }

    public UserInfo getMeInfo() {
        return meInfo;
    }

    public void setMeInfo(UserInfo meInfo) {
        this.meInfo = meInfo;
    }

    public UserInfo getYouInfo() {
        return youInfo;
    }

    public void setYouInfo(UserInfo youInfo) {
        this.youInfo = youInfo;
    }
}
