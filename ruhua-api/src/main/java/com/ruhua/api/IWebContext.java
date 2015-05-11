package com.ruhua.api;

import com.ruhua.domain.user.BaseInfo;
import com.ruhua.domain.user.LoginAccount;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用于实现上下文连接 用于在过滤器中实现注入Request与Response
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-11-9
 * Time: 下午4:30
 * To change this template use File | Settings | File Templates.
 */
public interface IWebContext {
    /**
     * 设置请求与应答上下文
     * @param request 请求
     * @param response 应答
     * @param loginUrl 登录页面的URL
     */
    public void setWebContext(HttpServletRequest request, HttpServletResponse response, String loginUrl);
    /**
     * 获取登录帐号
     * @return 返回当前的登录帐号，如果没有登录则返回空
     */
    public BaseInfo getLoginAccount();
}