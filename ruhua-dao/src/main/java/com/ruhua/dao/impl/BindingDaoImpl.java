package com.ruhua.dao.impl;

import com.ruhua.dao.BaseDao;
import com.ruhua.dao.BindingDao;
import com.ruhua.domain.binding.Binding;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 15-2-5
 * Time: 下午3:10
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class BindingDaoImpl extends BaseDao implements BindingDao {


    @Override
    public void deleteOpenid(String openid) {
        super.delete("BindingMapper.deleteOpenid",openid);
    }

    @Override
    public void insertBingding(String openid, String erp) {
        Binding binding = new Binding();
        binding.setErp(erp);
        binding.setOpenid(openid);
        super.insert("BindingMapper.insertBingding",binding);
    }

    @Override
    public List<String> query(String openid) {
        return super.queryForList("BindingMapper.query", openid);
    }

    @Override
    public boolean insertQ(String erp, String q) {
        Binding binding = new Binding();
        binding.setErp(erp);
        binding.setQ(q);
        return super.insert("BindingMapper.insertQ",binding);
    }
}
