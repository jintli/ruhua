package com.ruhua.web.controller;

import com.ruhua.common.encode.Encoder;
import com.ruhua.common.io.StreamUtils;
import com.ruhua.service.O2OWechatLocalService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 微信转发服务入口
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-9-28
 * Time: 下午7:17
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/weChatEntrance")
public class WeChatEntranceController extends BaseController {
    private Logger logger = Logger.getLogger(WeChatEntranceController.class);
    @Resource
    private O2OWechatLocalService o2OWechatLocalService;
    /**
     * 微信校验入口，用于微信校验服务用
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/go", method = RequestMethod.GET)
    public void validate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        vali(request,response);
        //测试
        //return  settleRequest(request,response);
    }

    public void vali(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        if(o2OWechatLocalService.validateEntranceURL(signature,timestamp,nonce,echostr)){
            response.getWriter().write(echostr);
        } else {
            response.getWriter().write("调皮鬼");
        }
        response.getWriter().flush();
    }

    /**
     * 微信处理事件
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/go", method = RequestMethod.POST)
    public void settleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("----------post---------------");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        //校验是否从微信而来
        if(!o2OWechatLocalService.validateEntranceURL(signature,timestamp,nonce,"val")){
            return;
        }
        String result = "";
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        result = StreamUtils.readRequestStrByScanner(request);
        result = Encoder.stringToEncode(result, "UTF-8");
        logger.info("result:" + result);
        String replyContent = "";
        replyContent = o2OWechatLocalService.settleRecieveResponse(result);
        logger.info("-------------------------------");
        logger.info(replyContent);
        logger.info("-------------------------------");
        if (replyContent != null) {
            response.getWriter().write(replyContent);
        }
        logger.info("----------post end---------------");
    }
}