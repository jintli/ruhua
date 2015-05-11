package com.ruhua.web.controller;

import com.ruhua.api.IWebContext;
import com.ruhua.domain.user.BaseInfo;
import com.ruhua.domain.user.LoginAccount;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * Date: 14-5-26
 * Time: 上午11:22
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/m/base")
public class BaseController implements IWebContext {
    private static final Logger log = Logger.getLogger(BaseController.class);
    /*********************获取Request与Response*******************/
    /**
     * 请求上下文
     */
    private HttpServletRequest request;
    /**
     * 应答上下文
     */
    private HttpServletResponse response;
    /**
     * 登录的页面  当访问需要登录的页面时，自动转到该页面
     */
    private String loginUrl;
    /**
     * 设置请求与应答的上下文
     */
    @Override
    public void setWebContext(HttpServletRequest request, HttpServletResponse response, String loginUrl) {
        this.request = request;
        this.response = response;
        this.loginUrl = loginUrl;
        //重置当前访问的数据
        this.baseInfo = null;
        this.remoteIp = null;
    }
    /**
     * 当前的请求对象
     * @return
     */
    protected HttpServletRequest getRequest() {
        //((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        return this.request;
    }
    /**
     * 获取当前的应答对象
     * @return
     */
    protected HttpServletResponse getResponse() {
        return this.response;
    }
    /*********************获取Request与Response*******************/

    /*********************用户登录相关*******************/
    /**
     * 当前登录的帐号
     */
    private BaseInfo baseInfo = null;
    /**
     * 该对象在调用isLogged方法后才有效
     * @return
     */
    @Override
    public BaseInfo getLoginAccount() {
        if (this.baseInfo == null){
            this.baseInfo = new BaseInfo();
            if (!this.getCookieObject(LoginAccount.USERCOOKINAME, this.baseInfo)){
                this.baseInfo = null;
                return null;
            }
        }
        return this.baseInfo;
    }
    /**
     * 判断用户是否已经登录
     * @return
     */
    protected boolean isLogged() {
        return this.getLoginAccount() != null;
    }
    /**
     * 跳转到登录页面
     * @return
     */
    protected ModelAndView toLoginView(){
        return new ModelAndView(new RedirectView(this.loginUrl), "tourl", this.getRequest().getRequestURI());
    }
    /*********************用户登录相关*******************/

    /*********************获取访问IP*******************/
    /**
     * 获取远程访问IP
     */
    private String remoteIp = null;
    /**
     * 获取远程访问IP
     * @return
     */
    protected String getRemoteIp(){
        HttpServletRequest request = this.getRequest();
        if (this.remoteIp==null || this.remoteIp.length()==0)
        {
            this.remoteIp = request.getHeader("x-forwarded-for");
            if (this.remoteIp == null || this.remoteIp.isEmpty() || "unknown".equalsIgnoreCase(this.remoteIp)) {
                this.remoteIp= request.getHeader("X-Real-IP");
            }
            if (this.remoteIp == null || this.remoteIp.isEmpty() || "unknown".equalsIgnoreCase(this.remoteIp)) {
                this.remoteIp= request.getHeader("Proxy-Client-IP");
            }
            if (this.remoteIp == null || this.remoteIp.isEmpty() || "unknown".equalsIgnoreCase(this.remoteIp)) {
                this.remoteIp= request.getHeader("WL-Proxy-Client-IP");
            }
            if (this.remoteIp == null || this.remoteIp.isEmpty() || "unknown".equalsIgnoreCase(this.remoteIp)) {
                this.remoteIp= request.getHeader("HTTP_CLIENT_IP");
            }
            if (this.remoteIp == null || this.remoteIp.isEmpty() || "unknown".equalsIgnoreCase(this.remoteIp)) {
                this.remoteIp= request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (this.remoteIp == null || this.remoteIp.isEmpty() || "unknown".equalsIgnoreCase(this.remoteIp)) {
                this.remoteIp= request.getRemoteAddr();
            }
            if (this.remoteIp == null || this.remoteIp.isEmpty() || "unknown".equalsIgnoreCase(this.remoteIp)) {
                this.remoteIp= request.getRemoteHost();
            }
        }
        return remoteIp;
    }
    /*********************获取访问IP*******************/
    /*********************获取访问参数*******************/
    /**
     * 获取所有参数
     * @return
     */
    protected Map<String,String[]> getParams(){
        HttpServletRequest request = this.getRequest();
        return request.getParameterMap();
    }
    /**
     * 获取指定的配置
     * @param name
     * @return
     */
    protected String getParam(String name){
        return getParam(name, "");
    }
    /**
     * 根据参数名称获取参数值，如果没有找到则以默认值返回
     * @param name
     * @param defaultValue
     * @return
     */
    protected String getParam(String name, String defaultValue){
        HttpServletRequest request = this.getRequest();
        String strValue = request.getParameter(name);
        return strValue == null ? defaultValue : strValue;
    }
    /**
     * 获取整形的参数值
     * @param name
     * @return
     */
    protected int getIntParam(String name){
        return getParam(name, 0);
    }
    /**
     * 获取整形的参数值
     * @param name
     * @param defaultValue
     * @return
     */
    protected int getParam(String name, Integer defaultValue){
        String strValue = getParam(name, defaultValue.toString());
        try{
            return Integer.valueOf(strValue);
        }
        catch(Exception e){
            return defaultValue;
        }
    }
    /**
     * 获取长整形的参数值
     * @param name
     * @return
     */
    protected long getLongParam(String name){
        return getParam(name, 0L);
    }
    /**
     * 获取长整形的参数值
     * @param name
     * @param defaultValue
     * @return
     */
    protected long getParam(String name, Long defaultValue){
        String strValue = getParam(name, defaultValue.toString());
        try{
            return Long.valueOf(strValue);
        }
        catch(Exception e){
            return defaultValue;
        }
    }
    /**
     * 获取单精度的参数值
     * @param name
     * @return
     */
    protected float getFloatParam(String name){
        return getParam(name, 0F);
    }
    /**
     * 获取单精度的参数值
     * @param name
     * @param defaultValue
     * @return
     */
    protected float getParam(String name, Float defaultValue){
        String strValue = getParam(name, defaultValue.toString());
        try{
            return Float.valueOf(strValue);
        }
        catch(Exception e){
            return defaultValue;
        }
    }
    /**
     * 获取双精度的参数值
     * @param name
     * @return
     */
    protected double getDoubleParam(String name){
        return getParam(name, 0D);
    }
    /**
     * 获取双精度的参数值
     * @param name
     * @param defaultValue
     * @return
     */
    protected double getParam(String name, Double defaultValue){
        String strValue = getParam(name, defaultValue.toString());
        try{
            return Double.valueOf(strValue);
        }
        catch(Exception e){
            return defaultValue;
        }
    }
    /**
     * 获取字节的参数值
     * @param name
     * @return
     */
    protected byte getByteParam(String name){
        return getParam(name, (byte)0);
    }
    /**
     * 获取字节的参数值
     * @param name
     * @param defaultValue
     * @return
     */
    protected byte getParam(String name, Byte defaultValue){
        String strValue = getParam(name, defaultValue.toString());
        try{
            return Byte.valueOf(strValue);
        }
        catch(Exception e){
            return defaultValue;
        }
    }
    /**
     * 获取字节的参数值
     * @param name
     * @return
     */
    protected short getShortParam(String name){
        return getParam(name, (short)0);
    }
    /**
     * 获取字节的参数值
     * @param name
     * @param defaultValue
     * @return
     */
    protected short getParam(String name, Short defaultValue){
        String strValue = getParam(name, defaultValue.toString());
        try{
            return Short.valueOf(strValue);
        }
        catch(Exception e){
            return defaultValue;
        }
    }
    /**
     * 获取布尔的参数值
     * @param name
     * @return
     */
    protected boolean getBooleanParam(String name){
        return getParam(name, false);
    }
    /**
     * 获取布尔的参数值
     * @param name
     * @param defaultValue
     * @return
     */
    protected boolean getParam(String name, Boolean defaultValue){
        String strValue = getParam(name, defaultValue.toString());
        try{
            return Boolean.valueOf(strValue);
        }
        catch(Exception e){
            return defaultValue;
        }
    }
    /**
     * 获取日期的参数值
     * @param name
     * @return
     */
    protected Date getDateParam(String name){
        return getParam(name, new Date());
    }
    /**
     * 获取日期的参数值
     * @param name
     * @param defaultValue
     * @return
     */
    protected Date getParam(String name, Date defaultValue){
        String strValue = getParam(name);
        if (strValue == null || strValue.length() == 0)
            return defaultValue;
        try{
            return DateUtil.parseDate(strValue);
        }
        catch(Exception e){
            return defaultValue;
        }
    }
    /*********************获取访问参数*******************/
    /*******************操作Cookie********************/
    /**
     * 获取指定键的Cookie
     * @param cookieName
     * @return 如果找到Cookie则返回 否则返回null
     */
    protected Cookie getCookie(String cookieName){
        if (StringUtils.isEmpty(cookieName) || this.getRequest().getCookies() == null)
            return null;
        for(Cookie cookie : this.getRequest().getCookies()){
            if (cookieName.equals(cookie.getName()))
                return cookie;
        }
        return null;
    }
    /**
     * 获取指定键的Cookie值
     * @param cookieName
     * @return 如果找到Cookie则返回 否则返回null
     */
    protected String getCookieValue(String cookieName){
        Cookie cookie = this.getCookie(cookieName);
        return cookie == null ? null : cookie.getValue();
    }
    /**
     * 删除指定的Cookie
     * @param cookieName
     */
    protected void removeCookie(String cookieName){
        HttpServletResponse response = this.getResponse();
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
    /**
     * 保存一个对象到Cookie里   Cookie只在会话内有效
     * @param cookieName
     * @param inst
     */
    protected void setCookie(String cookieName, Object inst){
        this.setCookie(cookieName, "/", inst);
    }
    /**
     * 保存一个对象到Cookie  Cookie只在会话内有效
     * @param cookieName
     * @param path
     * @param inst
     */
    protected void setCookie(String cookieName, String path, Object inst){
        if (StringUtils.isEmpty(cookieName) || inst == null)
            return;
        String strCookieString = this.object2CookieString(inst);
        this.setCookie(cookieName, path, strCookieString);
    }
    /**
     * 保存一个对象到Cookie
     * @param cookieName
     * @param inst
     * @param expiry (秒)设置Cookie的有效时长， 负数不保存，0删除该Cookie
     */
    protected void setCookie(String cookieName, Object inst, int expiry){
        this.setCookie(cookieName, "/", inst, expiry);
    }
    /**
     * 保存一个对象到Cookie
     * @param cookieName
     * @param path
     * @param inst
     * @param expiry (秒)设置Cookie的有效时长， 负数不保存，0删除该Cookie
     */
    protected void setCookie(String cookieName, String path, Object inst, int expiry){
        if (StringUtils.isEmpty(cookieName) || inst == null || expiry < 0)
            return;
        String strCookieString = this.object2CookieString(inst);
        this.setCookie(cookieName, path, strCookieString, expiry);
    }
    /**
     * 保存一个对象到Cookie里   Cookie只在会话内有效
     * @param cookieName
     * @param cookieValue
     */
    protected void setCookie(String cookieName, String cookieValue){
        this.setCookie(cookieName, "/", cookieValue);
    }
    /**
     * 保存一个对象到Cookie  Cookie只在会话内有效
     * @param cookieName
     * @param path
     * @param cookieValue
     */
    protected void setCookie(String cookieName, String path, String cookieValue){
        HttpServletResponse response = this.getResponse();
        if (StringUtils.isEmpty(cookieName) || cookieValue == null)
            return;
        Cookie cookie = new Cookie(cookieName, cookieValue);
        if (!StringUtils.isEmpty(path)){
            cookie.setPath(path);
        }
        response.addCookie(cookie);
    }
    /**
     * 保存一个对象到Cookie
     * @param cookieName
     * @param cookieValue
     * @param expiry (秒)设置Cookie的有效时长， 负数不保存，0删除该Cookie
     */
    protected void setCookie(String cookieName, String cookieValue, int expiry){
        this.setCookie(cookieName, "/", cookieValue, expiry);
    }
    /**
     * 保存一个对象到Cookie
     * @param cookieName
     * @param path
     * @param cookieValue
     * @param expiry (秒)设置Cookie的有效时长， 负数不保存，0删除该Cookie
     */
    protected void setCookie(String cookieName, String path, String cookieValue, int expiry){
        if (StringUtils.isEmpty(cookieName) || cookieValue == null || expiry < 0)
            return;
        HttpServletResponse response = this.getResponse();
        if (StringUtils.isEmpty(cookieName) || cookieValue == null)
            return;
        Cookie cookie = new Cookie(cookieName, cookieValue);
        if (!StringUtils.isEmpty(path)){
            cookie.setPath(path);
        }
        cookie.setMaxAge(expiry);
        response.addCookie(cookie);
    }
    /**
     * 把对象转换为Cookie存贮字串
     * @param inst
     * @return
     */
    private String object2CookieString(Object inst){
        if (inst == null)
            return "";
        StringBuilder strCookieValue = new StringBuilder();
        for(java.lang.reflect.Field field : inst.getClass().getDeclaredFields()){
            try{
                if (java.lang.reflect.Modifier.isStatic(field.getModifiers()) ||
                        java.lang.reflect.Modifier.isFinal(field.getModifiers())){
                    continue;
                }
                if (!this.isSimpleProperty(field.getType())) continue;//不是元数据
                field.setAccessible(true);// 提升权限
                Object objValue = field.get(inst);
                String strValue;
                if (field.getType().equals(Date.class)){
                    strValue = "" + ((Date)objValue).getTime();
                }else{
                    strValue = objValue == null ? "" : objValue.toString();
                }
                if (strCookieValue.length() > 0){
                    strCookieValue.append(String.format("&%s=%s", field.getName(), URLEncoder.encode(strValue, "UTF-8")));
                }
                else{
                    strCookieValue.append(String.format("%s=%s", field.getName(), URLEncoder.encode(strValue,"UTF-8")));
                }
            }
            catch(Exception e){
                log.fatal("object2CookieString faild", e);
                continue;
            }
        }
        return strCookieValue.toString();
    }
    /**
     * 从Cookie中获对对象
     * @param cookieName
     * @param inst
     * @return 如果获取转换成功，则返回true, 否则返回false
     */
    protected boolean getCookieObject(String cookieName, Object inst){
        if (inst == null){
            return false;
        }
        String cookieValue = this.getCookieValue(cookieName);
        if (cookieValue == null){
            return false;
        }
        for(java.lang.reflect.Field field : inst.getClass().getDeclaredFields()){
            try{
                if (java.lang.reflect.Modifier.isStatic(field.getModifiers()) ||
                        java.lang.reflect.Modifier.isFinal(field.getModifiers())){
                    continue;
                }
                if (!this.isSimpleProperty(field.getType())) continue;//不是元数据
                field.setAccessible(true);// 提升权限

                Pattern pattern = Pattern.compile(String.format("(^|&)%s=([^(&|$)]+)", field.getName()));
                Matcher matcher = pattern.matcher(cookieValue);
                String strValue = "";
                if (matcher.find()){
                    strValue = matcher.group(2);
                    strValue = URLDecoder.decode(strValue, "UTF-8");
                }
                field.set(inst, ConvertUtils.convert(strValue, field.getType()));
            }
            catch(Exception e){
                log.fatal("getCookieObject faild", e);
                return false;
            }
        }
        return true;
    }
    /**
     * 是否是简单的数据类型
     * @return
     */
    private boolean isSimpleProperty(Class<?> propType){
        if (!propType.isPrimitive() && !propType.isEnum()
                && (!propType.equals(String.class)
                && !propType.equals(Date.class))) {
            return false;
        }
        return true;
    }
    /*******************操作Cookie********************/
}
