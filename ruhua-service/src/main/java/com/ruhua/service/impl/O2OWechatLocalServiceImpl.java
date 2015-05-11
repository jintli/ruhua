package com.ruhua.service.impl;

import com.ruhua.common.encrypt.SHA1;
import com.ruhua.service.BaseService;
import com.ruhua.service.MsgResponse;
import com.ruhua.service.O2OWechatLocalService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.TreeSet;

/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-4-2
 * Time: 下午3:20
 * To change this template use File | Settings | File Templates.
 */
@Service
public class O2OWechatLocalServiceImpl extends BaseService implements O2OWechatLocalService {

    private static final Logger logger = Logger.getLogger(O2OWechatLocalServiceImpl.class);
    @Value("${JD_O2O_WECHAT_TOKEN}")
    private String token;
    @Value("${wechat.impl.template.send}")
    private String templateSendUrl;
    @Value("${wechat.impl.wechat_app_id}")
    private String appId4Request;
    @Value("${wechat.impl.wechat_app_secret}")
    private String secret4Request;
    @Value("${wechat.impl.access_token_url}")
    private String access_token4Request;

    @Resource
    private MsgResponse msgResponse;


    @Override
    public boolean validateEntranceURL(String signature, String timestamp, String nonce, String echostr) {
        if(StringUtils.isEmpty(signature) || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(nonce)) {
            return false;
        }
        TreeSet<String> set = new TreeSet<String>();
        set.add(token);
        set.add(timestamp);
        set.add(nonce);
        StringBuilder sb = new StringBuilder("");
        for(String value : set) {
            sb.append(value);
        }
        String result = new SHA1().getDigestOfString(sb.toString().getBytes()).toLowerCase();
        logger.info(result+"______"+signature);
        if(signature.equals(result)){
            return true;
        }
        return false;
    }

    @Override
    public String settleRecieveResponse(String recievedXML) {
        if (!StringUtils.isNotEmpty(recievedXML))
            return "";
        String replyContent = "";
        logger.info("----------post result"+recievedXML+"----------");

        try {
            Document doc = DocumentHelper.parseText(recievedXML);
            Element rootElt = doc.getRootElement();
            String toUser = rootElt.elementTextTrim("ToUserName");
            String fromUser = rootElt.elementTextTrim("FromUserName");
            String createTime = rootElt.elementTextTrim("CreateTime");
            logger.info("doc:" + doc);
            String msgType = rootElt.elementTextTrim("MsgType");
            if("text".equals(msgType)) {
                replyContent =  msgResponse.settleText(doc,toUser,fromUser);
            } else if("voice".equals(msgType)) {
                replyContent =  msgResponse.settleVoice(doc, toUser, fromUser);
            } else if ("event".equals(msgType)) {   //事件，包括（取消）收听、点击click事件
                replyContent = msgResponse.settleEvent(doc, toUser, fromUser);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return replyContent;
    }

}
