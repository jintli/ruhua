package com.ruhua.domain.user.dto;

/**
 * 用户信息
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-11-23
 * Time: 下午5:48
 * To change this template use File | Settings | File Templates.
 */
public class UserInfoDto {

    /**
     * 头像
     */
    private String headPic;
    /**
     * 姓名
     */
    private String userName;
    /**
     * 加入标识
     */
    private String enterAlert;
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
     * 爱好
     */
    private int love;

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEnterAlert() {
        return enterAlert;
    }

    public void setEnterAlert(String enterAlert) {
        this.enterAlert = enterAlert;
    }

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
