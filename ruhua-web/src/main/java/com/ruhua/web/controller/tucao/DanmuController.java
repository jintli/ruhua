package com.ruhua.web.controller.tucao;

import com.ruhua.domain.barrage.BarrageResult;
import com.ruhua.domain.constants.DanmuData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-12-22
 * Time: 下午2:03
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/danmu")
public class DanmuController {


    @RequestMapping(value = "/getMsg", method = RequestMethod.GET)
    @ResponseBody
    public BarrageResult getMsg(HttpServletRequest request,HttpServletResponse response) throws Exception {
        BarrageResult r = new BarrageResult();
        String msg = DanmuData.getMsg().toString();
        DanmuData.clear();
        try {
            r.setData(msg);
        } catch(Exception e) {
           e.printStackTrace();
        }
        return r;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public BarrageResult test(HttpServletRequest request,HttpServletResponse response) throws Exception {
        BarrageResult r = new BarrageResult();
        for(int i=0;i<100;i++) {
            DanmuData.setMsg("我是测试消息" + i);
        }
        return r;
    }
}
