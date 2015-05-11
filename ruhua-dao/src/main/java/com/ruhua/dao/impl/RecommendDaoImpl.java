package com.ruhua.dao.impl;

import com.ruhua.dao.BaseDao;
import com.ruhua.dao.RecommendDao;
import com.ruhua.domain.user.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-11-30
 * Time: 下午2:00
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class RecommendDaoImpl extends BaseDao implements RecommendDao {
    /**
     * 获取推荐
     *
     * @param parameter
     * @return
     */
    @Override
    public List<String> getRecommendList(Map parameter) {
        return super.queryForList("RecommendMapper.queryRecommendEmail",parameter);
    }

    /**
     * 喜欢
     *
     * @param parameter
     * @return
     */
    @Override
    public boolean insertLikePerson(Map parameter) {
        return super.insert("RecommendMapper.insertLikePerson",parameter);
    }

    /**
     * 不喜欢
     *
     * @param parameter
     * @return
     */
    @Override
    public boolean insertDislikePerson(Map parameter) {
        return super.insert("RecommendMapper.insertDislikePerson",parameter);
    }

    /**
     * 查询喜欢email的用户信息
     *
     * @param email
     * @return
     */
    @Override
    public List<UserInfo> queryLikeMeUserInfo(String email) {
        return super.queryForList("RecommendMapper.queryLikeMeUserInfo",email);
    }
}
