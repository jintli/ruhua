package com.ruhua.dao;

import com.ruhua.domain.user.UserSetting;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-11-29
 * Time: 下午6:56
 * To change this template use File | Settings | File Templates.
 */
public interface MgrDao {

    List<UserSetting> queryUserLoc();
}
