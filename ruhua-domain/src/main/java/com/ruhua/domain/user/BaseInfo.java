package com.ruhua.domain.user;
import com.ruhua.domain.Validatable;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-11-8
 * Time: 下午2:07
 * To change this template use File | Settings | File Templates.
 */
public class BaseInfo implements Serializable,Validatable {
    private String userName;
    private String email;
    private String pass2Md5;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass2Md5() {
        return pass2Md5;
    }

    public void setPass2Md5(String pass2Md5) {
        this.pass2Md5 = pass2Md5;
    }

    @Override
    public boolean val() {
        if(userName == null || pass2Md5 == null || email == null) {
            return false;
        }
        return true;
    }
}
