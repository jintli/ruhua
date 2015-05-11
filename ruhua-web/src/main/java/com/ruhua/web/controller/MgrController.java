package com.ruhua.web.controller;

import com.ruhua.common.response.EntityJDResult;
import com.ruhua.common.response.JDResult;
import com.ruhua.dao.MgrDao;
import com.ruhua.domain.constants.ServiceResponseConstants;
import com.ruhua.domain.user.UserSetting;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-11-29
 * Time: 下午6:52
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/mgr")
public class MgrController {
    private Logger logger = Logger.getLogger(UserController.class);

    @Resource
    private MgrDao mgrDao;

    @RequestMapping(value = "/query.json", method = RequestMethod.GET)
    @ResponseBody
    public JDResult createAccount(HttpServletRequest request,HttpServletResponse response,
        @RequestParam String token) throws Exception {
        EntityJDResult result = new EntityJDResult(ServiceResponseConstants.FAILURE.getCode());
        if(!"48417$3EFR145".equals(token)) {
            return result;
        }
        List<UserSetting> setting = mgrDao.queryUserLoc();
        result.setRetCode(ServiceResponseConstants.SUCCESS.getCode());
        result.setRetMsg(ServiceResponseConstants.SUCCESS.getMsg());
        result.setData(setting);
        return result;
    }
}
