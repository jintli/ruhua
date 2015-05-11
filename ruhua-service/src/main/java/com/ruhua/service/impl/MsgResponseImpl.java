package com.ruhua.service.impl;

import com.ruhua.common.msgResponse.ResponseUtil;
import com.ruhua.dao.BindingDao;
import com.ruhua.domain.constants.DanmuData;
import com.ruhua.domain.response.Article;
import com.ruhua.domain.response.NewsMessageResponse;
import com.ruhua.service.BaseService;
import com.ruhua.service.MsgResponse;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-4-2
 * Time: 下午4:38
 * To change this template use File | Settings | File Templates.
 */
@Service
public class MsgResponseImpl extends BaseService implements MsgResponse {
    private static final Logger logger = Logger.getLogger(MsgResponseImpl.class);

    @Resource
    private BindingDao bindingDao;

    @Override
    public String settleText(Document doc,String toUser,String fromUser) {
        Element rootElt = doc.getRootElement();
        String content =  rootElt.elementTextTrim("Content");
        logger.info(fromUser + "：" + content);
        if(content.indexOf("/::") >= 0) {
            return null;
        }
        //关键字 年会
        if(content.indexOf("erp#") == 0) {
            String erp = content.substring(4);
            bindingDao.deleteOpenid(fromUser);
            bindingDao.insertBingding(fromUser,erp);
            return ResponseUtil.responseTextReturn("恭喜，您的账号已经绑定成功！现在开始疯狂提问吧。",fromUser,toUser);
        } else {
            List<String> erps = bindingDao.query(fromUser);
            if(erps == null || erps.size() <=0) {
                return ResponseUtil.responseTextReturn("抱歉，请您先回复“erp#您的erp账号”进行账号绑定！",fromUser,toUser);
            } else {
                DanmuData.setMsg(fromUser + "---" + content);
                return ResponseUtil.responseTextReturn("hi "+erps.get(0)+"，我们已经收到您的问题了！",fromUser,toUser);
            }
        }
    }


    @Override
    public String settleVoice(Document doc,String toUser,String fromUser) {
        Element rootElt = doc.getRootElement();
        String content =  rootElt.elementTextTrim("Recognition");
        logger.info("语音识别结果：" + content);
        logger.info(fromUser + "：" + content);
//        DanmuData.setMsg(content);
        return ResponseUtil.responseTextReturn("您还是打字吧！",fromUser,toUser);
    }

    @Override
    public String settleEvent(Document doc,String toUser,String fromUser) {
        String reply = "";
        NewsMessageResponse resp = null;
        Element rootElt = doc.getRootElement();
        String eventType =  rootElt.elementTextTrim("Event");
        if("subscribe".equals(eventType)) {
            reply = ResponseUtil.responseTextReturn("您好，欢迎关注hack day·2014年度荣誉盛典的活动。活动中要积极提问哦，我们会根据大屏幕上显示的问题进行抽奖，提问越多获奖的机会越大呢！" +
                    "那么现在，回复：“erp#您的ERP账号”进行绑定，方便后期将奖品送给您吧！",fromUser,toUser);
        }
        return reply;
    }

}