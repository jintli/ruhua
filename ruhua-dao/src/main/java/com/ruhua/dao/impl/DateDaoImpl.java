package com.ruhua.dao.impl;

import com.ruhua.dao.BaseDao;
import com.ruhua.dao.DateDao;
import com.ruhua.domain.date.DateAllInfo;
import com.ruhua.domain.date.DateInfo;
import com.ruhua.domain.date.DateUser;
import com.ruhua.domain.user.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-12-2
 * Time: 下午9:17
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class DateDaoImpl extends BaseDao implements DateDao {
    /**
     * 查询能够约会的用户信息
     *
     * @param email
     * @return
     */
    @Override
    public List<UserInfo> queryCanDateUserInfo(String email) {
        return super.queryForList("DateMapper.queryCanDateUserInfo", email);
    }

    /**
     * 插入约会对象
     *
     * @param dateInfo
     * @return
     */
    @Override
    public boolean insertDatePerson(DateInfo dateInfo) {
        return super.insert("DateMapper.insertDatePerson",dateInfo);
    }

    /**
     * 查询信息
     *
     * @param parameter
     * @return
     */
    @Override
    public DateInfo selectDateInfo(Map parameter) {
        return (DateInfo) super.queryForObject("DateMapper.selectDatePerson", parameter);
    }

    /**
     * 查询约我的人
     *
     * @param parameter
     * @return
     */
    @Override
    public List<DateUser> queryDateMePerson(Map parameter) {
        return super.queryForList("DateMapper.queryDateMeUserInfo", parameter);
    }

    @Override
    public DateAllInfo queryDateMePersonOne(Map parameter) {
        return (DateAllInfo) super.queryForObject("DateMapper.queryDateMeUserInfoOne", parameter);
    }

    /**
     * 保存状态
     *
     * @param parameter
     * @return
     */
    @Override
    public boolean savaDateStatus(HashMap<String, Integer> parameter) {
        return super.update("DateMapper.savaDateStatus",parameter);
    }
}