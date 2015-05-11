package com.ruhua.web.controller.tucao;

import com.alibaba.fastjson.JSONObject;
import com.ruhua.web.interceptor.Login;
import com.ruhua.web.servlet.DemoServlet;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.CharBuffer;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 15-1-25
 * Time: 下午12:25
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/tucao")
public class TucaoController {
    private static final Logger log = Logger.getLogger(TucaoController.class);

    /**
     * 获取在线人数
     * @return
     */
    @RequestMapping(value = "/getCount")
    @ResponseBody
    public JSONObject getCount(HttpServletRequest request,HttpServletResponse response,
        String p,String a,String l) {
        JSONObject result = new JSONObject();
        ArrayList<DemoServlet.MyMessageInbound> list = DemoServlet.mmiList;
        result.put("all",list.size());
        return result;
    }
}
