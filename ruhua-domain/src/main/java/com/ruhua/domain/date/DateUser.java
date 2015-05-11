package com.ruhua.domain.date;

/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-12-6
 * Time: 上午7:50
 * To change this template use File | Settings | File Templates.
 */
public class DateUser extends DateInfo {
    private int id;
    private String userName;
    private int age;
    private String what;
    private String pic;
    private String headPic;

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

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
