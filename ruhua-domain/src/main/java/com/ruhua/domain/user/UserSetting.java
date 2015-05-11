package com.ruhua.domain.user;

/**
 * 用户设置信息
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-11-23
 * Time: 下午4:17
 * To change this template use File | Settings | File Templates.
 */
public class UserSetting extends BaseInfo {
    /**
     * 年龄
     */
    private int age;
    /**
     * 所在小区
     */
    private String community;
    /**
     * 所在小区经度
     */
    private double lng;
    /**
     * 所在小区纬度
    */
    private double lat;
    /**
     * 性别
     */
    private int sex;
    /**
     * 性取向
     */
    private int love;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
