package com.ruhua.dao;

import com.ruhua.domain.user.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-11-30
 * Time: 下午1:23
 * To change this template use File | Settings | File Templates.
 */
public interface RecommendDao {

    /**
     * 获取推荐
     * @param parameter
     * @return
     */
     List<String> getRecommendList(Map parameter);

    /**
     * 喜欢
     * @param parameter
     * @return
     */
    boolean insertLikePerson(Map parameter);

    /**
     * 不喜欢
     * @param parameter
     * @return
     */
    boolean insertDislikePerson(Map parameter);

    /**
     * 查询喜欢email的用户信息
     * @param email
     * @return
     */
    List<UserInfo> queryLikeMeUserInfo(String email);

}
