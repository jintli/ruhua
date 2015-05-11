package com.ruhua.domain.constants;

/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-12-22
 * Time: 下午2:10
 * To change this template use File | Settings | File Templates.
 */
public class DanmuData {

    private static StringBuffer instance = new StringBuffer();


    public static StringBuffer getMsg(){
        return instance;
    }

    public static void setMsg(String msg){
        synchronized(DanmuData.class){
            if(instance.length() == 0) {
                instance.append(msg);
            } else {
                instance.append("###").append(msg);
            }
        }
    }

    private DanmuData(){}

    public static void clear() {
        synchronized(DanmuData.class){
            instance.delete(0,instance.length());
        }
    }
}
