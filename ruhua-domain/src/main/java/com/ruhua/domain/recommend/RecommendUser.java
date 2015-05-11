package com.ruhua.domain.recommend;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-11-30
 * Time: 下午6:02
 * To change this template use File | Settings | File Templates.
 */
public class RecommendUser implements Serializable {
    private String email;
    private String userName;
    private String headPic;
    private double lng;
    private double lat;
    private int age;
    private int status; //对方的回应状态
    /**
     * 搭讪时间转换
     */
    private String likeCreateTime;
    private String community;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
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

    public String getLikeCreateTime() {
        return likeCreateTime;
    }

    public void setLikeCreateTime(String likeCreateTime) {
        this.likeCreateTime = likeCreateTime;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
