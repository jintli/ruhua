package com.ruhua.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.ruhua.common.response.EntityJDResult;
import com.ruhua.common.response.JDResult;
import com.ruhua.dao.UserDao;
import com.ruhua.domain.constants.ServiceResponseConstants;
import com.ruhua.domain.user.BaseInfo;
import com.ruhua.domain.user.LoginAccount;
import com.ruhua.domain.user.UserInfo;
import com.ruhua.domain.user.UserSetting;
import com.ruhua.domain.user.dto.UserInfoDto;
import com.ruhua.web.interceptor.Login;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-11-8
 * Time: 下午2:05
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    private Logger logger = Logger.getLogger(UserController.class);

    @Resource
    private UserDao userDao;

    @RequestMapping(value = "/createAccount.json", method = RequestMethod.GET)
    @ResponseBody
    public JSONPObject createAccount(HttpServletRequest request,HttpServletResponse response,
                                     BaseInfo baseInfo,@RequestParam String callback) throws Exception {
        JDResult result = new JDResult(true,ServiceResponseConstants.FAILURE.getCode()
                ,ServiceResponseConstants.FAILURE.getMsg());
        if(!baseInfo.val())
            return new JSONPObject(callback, result);
        if(userDao.emailCount(baseInfo)) {
            result.setRetCode(ServiceResponseConstants.EMAIL_HAS_CREATED.getCode());
            result.setRetMsg(ServiceResponseConstants.EMAIL_HAS_CREATED.getMsg());
            return new JSONPObject(callback, result);
        }
        if(userDao.saveUserBaseInfo(baseInfo)) {
            result.setRetCode(ServiceResponseConstants.SUCCESS.getCode());
            result.setRetMsg(ServiceResponseConstants.SUCCESS.getMsg());
            setCookie(LoginAccount.USERCOOKINAME,baseInfo,5*365*24*60*60);
        }
        return new JSONPObject(callback, result);
    }

    @RequestMapping(value = "/login.json", method = RequestMethod.GET)
    @ResponseBody
    public JSONPObject login(HttpServletRequest request,HttpServletResponse response,
                                     BaseInfo baseInfo,@RequestParam String callback) throws Exception {
        JDResult result = new JDResult(true,ServiceResponseConstants.FAILURE.getCode()
                ,ServiceResponseConstants.FAILURE.getMsg());
        if(userDao.login(baseInfo)) {
            result.setRetCode(ServiceResponseConstants.SUCCESS.getCode());
            result.setRetMsg(ServiceResponseConstants.SUCCESS.getMsg());
            setCookie(LoginAccount.USERCOOKINAME,baseInfo,5*365*24*60*60);
        }
        return new JSONPObject(callback, result);
    }

    @RequestMapping(value = "/autologin.json", method = RequestMethod.GET)
    @ResponseBody
    public JSONPObject autologin(HttpServletRequest request,HttpServletResponse response,
                             @RequestParam String callback) throws Exception {
        JDResult result = new JDResult(true,ServiceResponseConstants.FAILURE.getCode()
                ,ServiceResponseConstants.FAILURE.getMsg());
        BaseInfo userInfo = getLoginAccount();
        if(userDao.login(userInfo)) {
            result.setRetCode(ServiceResponseConstants.SUCCESS.getCode());
            result.setRetMsg(ServiceResponseConstants.SUCCESS.getMsg());
        }
        return new JSONPObject(callback, result);
    }

    @RequestMapping(value = "/logout.json", method = RequestMethod.GET)
    @ResponseBody
    public JSONPObject logout(HttpServletRequest request,HttpServletResponse response,
                             @RequestParam String callback) throws Exception {
        JDResult result = new JDResult(true,ServiceResponseConstants.FAILURE.getCode()
                ,ServiceResponseConstants.FAILURE.getMsg());
        BaseInfo baseInfo = new BaseInfo();
        setCookie(LoginAccount.USERCOOKINAME,baseInfo,1);
        result.setRetCode(ServiceResponseConstants.SUCCESS.getCode());
        result.setRetMsg(ServiceResponseConstants.SUCCESS.getMsg());
        return new JSONPObject(callback, result);
    }

    /**
     * 保存配置
     * @param request
     * @param response
     * @param userSetting
     * @param callback
     * @return
     * @throws Exception
     */
    @Login(required = true)
    @RequestMapping(value = "/saveSetting.json", method = RequestMethod.GET)
    @ResponseBody
    public JSONPObject saveSetting(HttpServletRequest request,HttpServletResponse response,
                             UserSetting userSetting,@RequestParam String callback) throws Exception {
        JDResult result = new JDResult(true,ServiceResponseConstants.FAILURE.getCode()
                ,ServiceResponseConstants.FAILURE.getMsg());
        userSetting.setEmail(getLoginAccount().getEmail());
        if(userDao.updateUserSetting(userSetting)) {
            result.setRetCode(ServiceResponseConstants.SUCCESS.getCode());
            result.setRetMsg(ServiceResponseConstants.SUCCESS.getMsg());
        }
        return new JSONPObject(callback, result);
    }

    /**
     * 查询用户信息
     * @param request
     * @param response
     * @param baseInfo
     * @param callback
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryUserInfo.json", method = RequestMethod.GET)
    @ResponseBody
    @Login(required = true)
    public JSONPObject queryUserInfo(HttpServletRequest request,HttpServletResponse response,
                                   BaseInfo baseInfo,@RequestParam String callback) throws Exception {
        EntityJDResult result = new EntityJDResult(true,ServiceResponseConstants.FAILURE.getCode()
                ,ServiceResponseConstants.FAILURE.getMsg());
        BaseInfo userInfo = getLoginAccount();
        if(StringUtils.isEmpty(userInfo.getEmail())) {
            return new JSONPObject(callback, result);
        }
        UserInfoDto userInfoDto = new UserInfoDto();
        String email = userInfo.getEmail();
        UserInfo allUserInfo = null;
        try {
            allUserInfo = userDao.queryUserInfoByEmail(email);
        } catch(Exception e) {
            e.printStackTrace();
        }
        if(allUserInfo == null) {
            return new JSONPObject(callback, result);
        }
        BeanUtils.copyProperties(userInfoDto,allUserInfo);
        String pic = allUserInfo.getPic();
        if(pic != null && JSONObject.parseObject(pic).get("pic001") != null) {
            userInfoDto.setHeadPic(JSONObject.parseObject(pic).getString("pic001"));
        } else userInfoDto.setHeadPic(null);
        if(StringUtils.isEmpty(allUserInfo.getCommunity())) {
            userInfoDto.setEnterAlert(DateUtil.formatDate(allUserInfo.getCreatetime(), "yyyy年MM月") + "加入");
        } else {
            userInfoDto.setEnterAlert(DateUtil.formatDate(allUserInfo.getCreatetime(), "yyyy年MM月") + "加入 "
                + "现住<span id='dynymicCommunity'>" + allUserInfo.getCommunity() + "</span>");
        }
        result.setRetCode(ServiceResponseConstants.SUCCESS.getCode());
        result.setRetMsg(ServiceResponseConstants.SUCCESS.getMsg());
        result.setData(userInfoDto);
        return new JSONPObject(callback, result);
    }

}