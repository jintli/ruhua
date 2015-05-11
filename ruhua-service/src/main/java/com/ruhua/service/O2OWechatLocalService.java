package com.ruhua.service;

/**
 * O2O微信公众平台基础支持
 * User: lijing3
 * Date: 14-4-2
 * Time: 下午3:06
 * To change this template use File | Settings | File Templates.
 *
 */
public interface O2OWechatLocalService {

    /**
     * 填写微信公众平台url及token时进行token校验
     * @param signature          微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * @param timestamp      时间戳
     * @param nonce          随机数
     * @param echostr        随机字符串
     * @return
     */
    boolean validateEntranceURL(String signature, String timestamp, String nonce, String echostr);

    /**
     * 处理粉丝通过微信发送的消息，包括文本、语音、图片、地理位置、菜单点击事件种种
     * @param recievedXML   微信接收到的xml信息封装
     * @return
     */
    String settleRecieveResponse(String recievedXML);

}
