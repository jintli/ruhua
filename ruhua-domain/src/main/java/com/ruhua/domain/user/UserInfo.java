package com.ruhua.domain.user;

import java.util.Date;

/**
 * 包含用户的所有信息
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-11-23
 * Time: 下午6:20
 * To change this template use File | Settings | File Templates.
 */
public class UserInfo extends UserSetting {

    /**
     * 用户创建时间
     */
    private Date createtime;
    /**
     * 用户创建时间
     */
    private Date lastmodified;
    /**
     * 用户照片信息
     */
    private String pic;
    /**
     * 用户标签
     */
    private String lable;

    /**
     * 状态
     * @return
     */
    private int status;

    /**
     * 搭讪时间转换
     */
    private String likeCreateTime;

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

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getLastmodified() {
        return lastmodified;
    }

    public void setLastmodified(Date lastmodified) {
        this.lastmodified = lastmodified;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLikeCreateTime() {
        return likeCreateTime;
    }

    public void setLikeCreateTime(String likeCreateTime) {
        this.likeCreateTime = likeCreateTime;
    }
}
