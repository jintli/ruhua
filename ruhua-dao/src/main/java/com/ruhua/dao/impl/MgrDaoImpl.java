package com.ruhua.dao.impl;

import com.ruhua.dao.BaseDao;
import com.ruhua.dao.MgrDao;
import com.ruhua.dao.UserDao;
import com.ruhua.domain.user.UserSetting;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-11-29
 * Time: 下午6:58
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class MgrDaoImpl extends BaseDao implements MgrDao {
    @Override
    public List<UserSetting> queryUserLoc() {
        return super.queryForList("MgrMapper.query",null);
    }
}
