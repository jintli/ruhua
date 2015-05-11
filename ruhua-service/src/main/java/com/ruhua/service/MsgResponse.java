package com.ruhua.service;

import org.dom4j.Document;

/**
 * 微信客户端各种消息的处理，包括文字、图片、声音、地理位置、事件响应
 * 按照微信封装的xml信息的msgtype字段暂时分为：text、voice、image、location、event等
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-4-2
 * Time: 下午4:30
 * To change this template use File | Settings | File Templates.
 */
public interface MsgResponse {
    /**
     * 处理文本信息
     * @return
     */
    String settleText(Document doc, String toUser, String fromUser);
    /**
     * 处理语音信息
     * @return
     */
    String settleVoice(Document doc, String toUser, String fromUser);

    /**
     * 处理event事件包括click等
     * @return
     */
    String settleEvent(Document doc, String toUser, String fromUser);

}
