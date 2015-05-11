package com.ruhua.domain.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 各种常量
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-4-8
 * Time: 上午9:18
 * To change this template use File | Settings | File Templates.
 */
public class WechatConstants {
    private WechatConstants() {}

    //换行
    public static final String CRLF = "\n";

    //自动上报位置后cache的key的后缀：open_id+后缀
    public static final String CACHE_COOR_AUTO_SUFFIX = "_auto";

    //手动上报位置后cache的key的后缀：open_id+后缀
    public static final String CACHE_COOR_MANUAL_SUFFIX = "_manual";

    public static final String CACHE_LAST_SEND_TIME = "_cache_last_send_time";

    //将第三方图片转为本地url
    public static final String EXCHANGE_RROM_OUT2INNER_URL = "/image/exchangeFromOutToInner?image=";

    //M端图片url前缀
    public static final String WARE_IMAGE_URL_PREFIX = "http://img10.360buyimg.com/n1/";

//    public static final String indexUrl = "http://jdone.nat123.net";
    public static final String indexUrl = "http://wechat.o2o.jd.com";

    public static final String oneUrl = "http://one.jd.com";

    public static final String SUBSCRIBE_CONFIG = "JDONE_WECHAT_SUBSCRIBE_CONFIG";
    public static final String AUTOREPLY_CONFIG = "JDONE_WECHAT_AUTO_REPLY_CONFIG";

    //欢迎语
    public static final String JD_O2O_WECHAT_WELCOME_MSG = "感谢您的关注，京东快点将为您提供更便利的生活！\n" +
            "客服热线：4006223268";

    //帮助语
    public static final String JD_O2O_WECHAT_HELP_MSG = "9:00-20:30下单\n两小时速达！！满59元免运费！\n\n订购热线：\n4006101360转113780";

    //商品搜索未果提示
    public static final String JD_O2O_WECHAT_WARE_SEARCH_NULL = "抱歉，未能在附近搜到与[{GOODS}]相关的商品，去逛逛！";

    //位置信息检查失败提示
    public static final String JD_O2O_WECHAT_LOCATION_SEND_NULL = "抱歉还不知道您住哪里，请确认已开启GPS定位功能。\n<a href='"+WechatConstants.oneUrl+"'>戳我去手选地址！</a>" ;
    public static final Map<String,String> venderShopIdMap = new HashMap<String,String>(4);
    static {
        venderShopIdMap.put("99498","97771");
        venderShopIdMap.put("99767","97767");
        venderShopIdMap.put("99768","97747");
        venderShopIdMap.put("79103","76020");
    }
    //TODO目前通过vender和zone的对应关系来映射
    public static final Map<String,String> venderZoneIdMap = new HashMap<String,String>(4);
    static {
        venderZoneIdMap.put("99498","201");
        venderZoneIdMap.put("99767","202");
        venderZoneIdMap.put("99768","203");
        venderZoneIdMap.put("79103","101");
    }
}
