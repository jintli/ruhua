package com.ruhua.web.interceptor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created with IntelliJ IDEA.
 * User: jiaohuaisheng
 * Date: 14-8-21
 * Time: 上午9:28
 * To change this template use File | Settings | File Templates.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Login {
    /**
     * true 代表需要验证登陆
     * false
     */
    public boolean required();

}