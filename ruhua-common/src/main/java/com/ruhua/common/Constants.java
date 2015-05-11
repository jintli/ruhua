package com.ruhua.common;

/**
 * 各种常量
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-4-8
 * Time: 上午9:18
 * To change this template use File | Settings | File Templates.
 */
public class Constants {
    private Constants() {}

    //换行
    public static final String CRLF = "\n";

    //自动上报位置后cache的key的后缀：open_id+后缀
    public static final String CACHE_COOR_AUTO_SUFFIX = "_auto";

    //手动上报位置后cache的key的后缀：open_id+后缀
    public static final String CACHE_COOR_MANUAL_SUFFIX = "_manual";

    //将第三方图片转为本地url
    public static final String EXCHANGE_RROM_OUT2INNER_URL = "/image/exchangeFromOutToInner?image=";

    //M端图片url前缀
    public static final String WARE_IMAGE_URL_PREFIX = "http://img10.360buyimg.com/n1/";

    //欢迎语
    public static final String JD_O2O_WECHAT_WELCOME_MSG = "Hi，欢迎来到京东O2O平台！\n\ue10f 点击【周边门店】,快速查找您周边的商家；\n\ue10f 需要什么直接对我说”商品名称“吧；\n\ue10f 渴了饿了？想下厨房？...别急，点击【一小时达】,要啥有啥。";

    //帮助语
    public static final String JD_O2O_WECHAT_HELP_MSG = "再次欢迎来到京东O2O 平台，文字或者语音输入您要的商品，发现更多惊喜哦。\n客服目前尚未就位，还请耐心等待...";

    //商品搜索未果提示
    public static final String JD_O2O_WECHAT_WARE_SEARCH_NULL = "抱歉，未能搜到附近与[{GOODS}]相关的商品！";

    //位置信息检查失败提示
    public static final String JD_O2O_WECHAT_LOCATION_SEND_NULL = "未检测到您的位置信息，暂无法提供周围商品服务！您可以手动选择位置后发送！" ;

}
