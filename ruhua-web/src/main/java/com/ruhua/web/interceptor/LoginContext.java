package com.ruhua.web.interceptor;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: jiaohuaisheng
 * Date: 14-8-19
 * Time: 下午3:24
 * To change this template use File | Settings | File Templates.
 */
public class LoginContext {

    private final static HashMap<String,String> holder = new HashMap<String,String>();

    public static void put(String email,String sid){
        holder.put(email, sid);
    }

    public static String get(String email){
        return holder.get(email);
    }

    public static void remove(String email){
        holder.remove(email);
    }
}