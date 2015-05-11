package com.ruhua.dao;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 15-2-5
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
public interface BindingDao {

    void deleteOpenid(String openid);

    void insertBingding(String openid,String erp);

    List<String> query(String openid);

    boolean insertQ(String erp,String q);
}
