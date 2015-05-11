package com.ruhua.dao.impl;

import com.ruhua.dao.BaseDao;
import com.ruhua.dao.UserDao;
import com.ruhua.domain.user.BaseInfo;
import com.ruhua.domain.user.UserInfo;
import com.ruhua.domain.user.UserProfile;
import com.ruhua.domain.user.UserSetting;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-11-8
 * Time: 下午2:26
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class UserDaoImpl extends BaseDao implements UserDao {
    /**
     * 保存用户注册基本信息
     *
     * @param baseInfo
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public boolean saveUserBaseInfo(BaseInfo baseInfo) {
        UserSetting userSetting = new UserSetting();
        UserProfile userProfile = new UserProfile();
        try {
            BeanUtils.copyProperties(userSetting,baseInfo);
            BeanUtils.copyProperties(userProfile,baseInfo);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return super.insert("UserMapper.saveUserBaseInfo", baseInfo)
                && initUserSetting(userSetting)
                && initUserProfile(userProfile);
    }

    /**
     * 校验用户是否登录
     *
     * @param baseInfo
     * @return
     */
    @Override
    public boolean login(BaseInfo baseInfo) {
        return super.queryCountForObject("UserMapper.login", baseInfo) > 0;
    }

    /**
     * 检查email个数 校验用
     *
     * @param baseInfo
     * @return
     */
    @Override
    public boolean emailCount(BaseInfo baseInfo) {
        return super.queryCountForObject("UserMapper.emailCount", baseInfo) > 0;
    }

    /**
     * 初始化用户设置
     *
     * @param userSetting
     * @return
     */
    @Override
    public boolean initUserSetting(UserSetting userSetting) {
        return super.insert("UserMapper.initUserSetting", userSetting);
    }

    /**
     * 初始化用户资料
     *
     * @param userProfile
     * @return
     */
    @Override
    public boolean initUserProfile(UserProfile userProfile) {
        return super.insert("UserMapper.initUserProfile", userProfile);
    }

    /**
     * 查询email下的用户所有信息
     *
     * @param email
     * @return
     */
    @Override
    public UserInfo queryUserInfoByEmail(String email) {
        return (UserInfo) super.queryForObject("UserMapper.queryUserInfoByEmail",email);
    }

    /**
     * 保存用户设置
     *
     * @param userSetting
     * @return
     */
    @Override
    public boolean updateUserSetting(UserSetting userSetting) {
        return super.update("UserMapper.updateUserSetting",userSetting);
    }

    /**
     * 查询用户相片
     *
     * @param userProfile
     * @return
     */
    @Override
    public String queryUserPic(UserProfile userProfile) {
        return (String) super.queryForObject("UserMapper.queryUserPic",userProfile);
    }

    /**
     * 保存用户相片
     *
     * @param userProfile
     * @return
     */
    @Override
    public boolean updateUserPic(UserProfile userProfile) {
        return super.update("UserMapper.updateUserPic",userProfile);
    }

    /**
     * 保存用户标签
     *
     * @param userProfile
     * @return
     */
    @Override
    public boolean updateUserLable(UserProfile userProfile) {
        return super.update("UserMapper.updateUserLable",userProfile);
    }
}
