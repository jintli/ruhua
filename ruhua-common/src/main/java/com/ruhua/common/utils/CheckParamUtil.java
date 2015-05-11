package com.ruhua.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: jiaohuaisheng
 * Date: 14-9-22
 * Time: 下午1:45
 * To change this template use File | Settings | File Templates.
 */
public class CheckParamUtil {
    /**
     * 校验是否包含html代码
     */
    public static boolean isIllegalWord(String word){
        String regExp = "<([^>]*)>";
        Pattern p=Pattern.compile(regExp);
        Matcher m=p.matcher(word);
        while(m.find()){
            return true;
        }
        return false;
    }
}
