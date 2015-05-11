package com.ruhua.domain.user.dto;

import com.ruhua.domain.user.UserProfile;

/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-11-25
 * Time: 下午9:21
 * To change this template use File | Settings | File Templates.
 */
public class UserProfileDto extends UserProfile {
    /**
     * 姓名
     */
    private String userName;
    /**
     * 年龄
     */
    private int age;
    /**
     * 社区
     */
    private String community;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
