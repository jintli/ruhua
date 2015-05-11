package com.ruhua.dao;

import com.ruhua.domain.user.BaseInfo;
import com.ruhua.domain.user.UserInfo;
import com.ruhua.domain.user.UserProfile;
import com.ruhua.domain.user.UserSetting;

/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-11-8
 * Time: 下午2:25
 * To change this template use File | Settings | File Templates.
 */
public interface UserDao {
    /**
     * 保存用户注册基本信息
     * @param baseInfo
     * @return
     */
    boolean saveUserBaseInfo(BaseInfo baseInfo);

    /**
     * 校验用户是否登录(查询是否有该用户)
     * @param baseInfo
     * @return
     */
    boolean login(BaseInfo baseInfo);

    /**
     * 检查email个数 校验用
     * @param baseInfo
     * @return
     */
    boolean emailCount(BaseInfo baseInfo);

    /**
     * 初始化用户设置
     * @param userSetting
     * @return
     */
    boolean initUserSetting(UserSetting userSetting);

    /**
     * 初始化用户资料
     * @param userProfile
     * @return
     */
    boolean initUserProfile(UserProfile userProfile);

    /**
     * 查询email下的用户所有信息
     * @param email
     * @return
     */
    UserInfo queryUserInfoByEmail(String email);

    /**
     * 保存用户设置
     * @param userSetting
     * @return
     */
    boolean updateUserSetting(UserSetting userSetting);

    /**
     * 查询用户相片
     *
     * @param userProfile
     * @return
     */
    String queryUserPic(UserProfile userProfile);

    /**
     * 保存用户相片
     * @param userProfile
     * @return
     */
    boolean updateUserPic(UserProfile userProfile);

    /**
     * 保存用户标签
     * @param userProfile
     * @return
     */
    boolean updateUserLable(UserProfile userProfile);
}
