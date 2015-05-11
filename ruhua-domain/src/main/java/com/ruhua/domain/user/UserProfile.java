package com.ruhua.domain.user;

/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-11-23
 * Time: 下午4:37
 * To change this template use File | Settings | File Templates.
 */
public class UserProfile extends BaseInfo {
    /**
     * 用户照片信息
     */
    private String pic;
    /**
     * 用户标签
     */
    private String lable;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }
}
