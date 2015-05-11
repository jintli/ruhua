package com.ruhua.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.ruhua.common.response.EntityJDResult;
import com.ruhua.common.response.JDResult;
import com.ruhua.dao.DateDao;
import com.ruhua.dao.UserDao;
import com.ruhua.domain.constants.ServiceResponseConstants;
import com.ruhua.domain.date.DateAllInfo;
import com.ruhua.domain.date.DateInfo;
import com.ruhua.domain.date.DateUser;
import com.ruhua.domain.recommend.RecommendUser;
import com.ruhua.domain.user.BaseInfo;
import com.ruhua.domain.user.UserInfo;
import com.ruhua.web.interceptor.Login;
import org.apache.commons.beanutils.BeanUtils;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-12-2
 * Time: 下午9:22
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/date")
public class DateController extends BaseController {

    private Logger logger = Logger.getLogger(DateController.class);

    @Resource
    private DateDao dateDao;
    @Resource
    private UserDao userDao;

    @RequestMapping(value = "/queryCanDateUserInfo.json", method = RequestMethod.GET)
    @ResponseBody
    @Login(required = true)
    public JSONPObject queryCanDateUserInfo(HttpServletRequest request,HttpServletResponse response,
                                           @RequestParam String callback) throws Exception {
        EntityJDResult result = new EntityJDResult(ServiceResponseConstants.FAILURE.getCode()
                ,ServiceResponseConstants.FAILURE.getMsg());
        BaseInfo userInfo = getLoginAccount();
        if(StringUtils.isEmpty(userInfo.getEmail())) {
            return new JSONPObject(callback, result);
        }
        List<UserInfo> canDateInfoList = null;
        try {
            canDateInfoList = dateDao.queryCanDateUserInfo(userInfo.getEmail());
        } catch(Exception e) {
            e.printStackTrace();
        }
        if(canDateInfoList != null && canDateInfoList.size() > 0) {
            result.setRetCode(ServiceResponseConstants.SUCCESS.getCode());
            result.setRetMsg(ServiceResponseConstants.SUCCESS.getMsg());
            List<RecommendUser> recommendUserList = new ArrayList(canDateInfoList.size());
            for(UserInfo userInfo1 : canDateInfoList) {
                RecommendUser recommendUser = new RecommendUser();
                BeanUtils.copyProperties(recommendUser, userInfo1);
                String pic = userInfo1.getPic();
                if(pic != null && JSONObject.parseObject(pic).get("pic001") != null) {
                    recommendUser.setHeadPic(JSONObject.parseObject(pic).getString("pic001"));
                }
                HashMap<String,String> parameter = new HashMap<String, String>();
                parameter.put("email",userInfo.getEmail());
                parameter.put("dateEmail",recommendUser.getEmail());
                DateInfo dateInfo = dateDao.selectDateInfo(parameter);
                recommendUser.setStatus(dateInfo == null ? -1 : dateInfo.getStatus());
                recommendUserList.add(recommendUser);
            }
            result.setData(recommendUserList);
        } else {
            result.setRetCode(ServiceResponseConstants.CAN_DATE_USER_INFO_NULL.getCode());
            result.setRetMsg(ServiceResponseConstants.CAN_DATE_USER_INFO_NULL.getMsg());
        }
        return new JSONPObject(callback, result);
    }

    @RequestMapping(value = "/insertDatePerson.json", method = RequestMethod.GET)
    @ResponseBody
    @Login(required = true)
    public JSONPObject insertDatePerson(HttpServletRequest request,HttpServletResponse response,
                                        DateInfo dateInfo,@RequestParam String callback) throws Exception {
        JDResult result = new JDResult(true, ServiceResponseConstants.FAILURE.getCode()
                ,ServiceResponseConstants.FAILURE.getMsg());
        BaseInfo userInfo = getLoginAccount();
        if(StringUtils.isEmpty(userInfo.getEmail())) {
            return new JSONPObject(callback, result);
        }
        dateInfo.setEmail(userInfo.getEmail());
        if(dateDao.insertDatePerson(dateInfo)) {
            result.setRetCode(ServiceResponseConstants.SUCCESS.getCode());
            result.setRetMsg(ServiceResponseConstants.SUCCESS.getMsg());
        }
        return new JSONPObject(callback, result);
    }

    @RequestMapping(value = "/queryDateMeUserInfo.json", method = RequestMethod.GET)
    @ResponseBody
    @Login(required = true)
    public JSONPObject queryDateMeUserInfo(HttpServletRequest request,HttpServletResponse response,
                                           @RequestParam String callback) throws Exception {
        EntityJDResult result = new EntityJDResult(true, ServiceResponseConstants.FAILURE.getCode()
                ,ServiceResponseConstants.FAILURE.getMsg());
        BaseInfo userInfo = getLoginAccount();
        if(StringUtils.isEmpty(userInfo.getEmail())) {
            return new JSONPObject(callback, result);
        }
        HashMap<String,String> parameter = new HashMap<String,String>();
        parameter.put("email",userInfo.getEmail());
        List<DateUser> dateMeUserInfoList = dateDao.queryDateMePerson(parameter);
        if(dateMeUserInfoList != null && dateMeUserInfoList.size() > 0) {
            result.setRetCode(ServiceResponseConstants.SUCCESS.getCode());
            result.setRetMsg(ServiceResponseConstants.SUCCESS.getMsg());
            for(DateUser dateUser : dateMeUserInfoList) {
                String pic = dateUser.getPic();
                if(pic != null && JSONObject.parseObject(pic).get("pic001") != null) {
                    dateUser.setHeadPic(JSONObject.parseObject(pic).getString("pic001"));
                }
                dateUser.setWhat("在 " + dateUser.getLoc() + " " + (dateUser.getType() == 1 ? "吃饭" : (dateUser.getType() == 2 ? "看电影" :(dateUser.getType() == 3 ? "K歌" : ""))));
                dateUser.setDatetime(dateUser.getDatetime().substring(0,16));
            }
            result.setData(dateMeUserInfoList);
        } else {
            result.setRetCode(ServiceResponseConstants.LIKE_ME_USER_INFO_NULL.getCode());
            result.setRetMsg(ServiceResponseConstants.LIKE_ME_USER_INFO_NULL.getMsg());
        }
        return new JSONPObject(callback, result);
    }

    @RequestMapping(value = "/queryDateMeUserInfoOne.json", method = RequestMethod.GET)
    @ResponseBody
    @Login(required = true)
    public JSONPObject queryDateMeUserInfoOne(HttpServletRequest request,HttpServletResponse response,
                                           String dateid,@RequestParam String callback) throws Exception {
        EntityJDResult result = new EntityJDResult(true, ServiceResponseConstants.FAILURE.getCode()
                ,ServiceResponseConstants.FAILURE.getMsg());
        BaseInfo userInfo = getLoginAccount();
        if(StringUtils.isEmpty(userInfo.getEmail())) {
            return new JSONPObject(callback, result);
        }
        HashMap<String,String> parameter = new HashMap<String,String>();
        parameter.put("id",dateid);
        DateAllInfo dateMeUserInfo = dateDao.queryDateMePersonOne(parameter);
        if(dateMeUserInfo != null) {
            result.setRetCode(ServiceResponseConstants.SUCCESS.getCode());
            result.setRetMsg(ServiceResponseConstants.SUCCESS.getMsg());
            String pic = dateMeUserInfo.getPic();
            if(pic != null && JSONObject.parseObject(pic).get("pic001") != null) {
                dateMeUserInfo.setHeadPic(JSONObject.parseObject(pic).getString("pic001"));
            }
            dateMeUserInfo.setWhat("在 " + dateMeUserInfo.getLoc() + " " + (dateMeUserInfo.getType() == 1 ? "吃饭" :
                    (dateMeUserInfo.getType() == 2 ? "看电影" :(dateMeUserInfo.getType() == 3 ? "K歌" : ""))));
            dateMeUserInfo.setDatetime(dateMeUserInfo.getDatetime().substring(0,16));
            dateMeUserInfo.setMeInfo(userDao.queryUserInfoByEmail(userInfo.getEmail()));
            dateMeUserInfo.setYouInfo(userDao.queryUserInfoByEmail(dateMeUserInfo.getEmail()));
            result.setData(dateMeUserInfo);
        } else {
            result.setRetCode(ServiceResponseConstants.LIKE_ME_USER_INFO_NULL.getCode());
            result.setRetMsg(ServiceResponseConstants.LIKE_ME_USER_INFO_NULL.getMsg());
        }
        return new JSONPObject(callback, result);
    }

    @RequestMapping(value = "/savaDateStatus.json", method = RequestMethod.GET)
    @ResponseBody
    @Login(required = true)
    public JSONPObject savaDateStatus(HttpServletRequest request,HttpServletResponse response,
                                              Integer id,Integer status,@RequestParam String callback) throws Exception {
        JDResult result = new JDResult(true, ServiceResponseConstants.FAILURE.getCode()
                ,ServiceResponseConstants.FAILURE.getMsg());
        BaseInfo userInfo = getLoginAccount();
        if(StringUtils.isEmpty(userInfo.getEmail())) {
            return new JSONPObject(callback, result);
        }
        HashMap<String,Integer> parameter = new HashMap<String,Integer>();
        parameter.put("id",id);
        parameter.put("status",status);
        if(dateDao.savaDateStatus(parameter)) {
            result.setRetCode(ServiceResponseConstants.SUCCESS.getCode());
            result.setRetMsg(ServiceResponseConstants.SUCCESS.getMsg());
        }
        return new JSONPObject(callback, result);
    }

}
