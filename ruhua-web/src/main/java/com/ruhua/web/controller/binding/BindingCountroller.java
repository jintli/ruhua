package com.ruhua.web.controller.binding;

import com.ruhua.dao.BindingDao;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 15-2-7
 * Time: 上午11:15
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value="/binding")
public class BindingCountroller {
    Logger logger = Logger.getLogger(BindingCountroller.class);

    @Resource
    BindingDao bindingDao;


    @RequestMapping(value = "/queryErp", method = RequestMethod.GET)
    @ResponseBody
    public String queryErp(HttpServletRequest request, HttpServletResponse response,
                      @RequestParam String openid) throws ServletException, IOException {
        String erp = "";
        List<String> erps = bindingDao.query(openid);
        if(erps != null && erps.size() >0) {
            erp = erps.get(0);
        }
        return erp;
    }

    @RequestMapping(value = "/saveQ", method = RequestMethod.GET)
    @ResponseBody
    public void saveQ(HttpServletRequest request, HttpServletResponse response,
                      @RequestParam String erp,@RequestParam String q) throws ServletException, IOException {
        bindingDao.insertQ(erp, URLDecoder.decode(q,"UTF-8"));
    }
}
