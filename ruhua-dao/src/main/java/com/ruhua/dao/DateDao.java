package com.ruhua.dao;

import com.ruhua.domain.date.DateAllInfo;
import com.ruhua.domain.date.DateInfo;
import com.ruhua.domain.date.DateUser;
import com.ruhua.domain.user.UserInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 约会dao
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-12-2
 * Time: 下午9:17
 * To change this template use File | Settings | File Templates.
 */
public interface DateDao {

    /**
     * 查询能够约会的用户信息
     * @param email
     * @return
     */
    List<UserInfo> queryCanDateUserInfo(String email);

    /**
     * 插入约会对象
     * @param dateInfo
     * @return
     */
    boolean insertDatePerson(DateInfo dateInfo);

    /**
     * 查询信息
     * @param parameter
     * @return
     */
    DateInfo selectDateInfo(Map parameter);

    /**
     * 查询约我的人
     * @param parameter
     * @return
     */
    List<DateUser> queryDateMePerson(Map parameter);

    /**
     * 查询特定约我的人
     * @param parameter
     * @return
     */
    DateAllInfo queryDateMePersonOne(Map parameter);

    /**
     * 保存状态
     * @param parameter
     * @return
     */
    boolean savaDateStatus(HashMap<String,Integer> parameter);
}
