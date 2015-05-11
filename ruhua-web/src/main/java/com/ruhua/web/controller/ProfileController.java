package com.ruhua.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.ruhua.common.response.EntityJDResult;
import com.ruhua.common.response.JDResult;
import com.ruhua.dao.UserDao;
import com.ruhua.domain.constants.ServiceResponseConstants;
import com.ruhua.domain.user.BaseInfo;
import com.ruhua.domain.user.UserInfo;
import com.ruhua.domain.user.UserProfile;
import com.ruhua.domain.user.dto.UserProfileDto;
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

/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-11-29
 * Time: 下午12:26
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/profile")
public class ProfileController extends BaseController {

    private Logger logger = Logger.getLogger(ProfileController.class);

    @Resource
    private UserDao userDao;

    /**
     * 查询用户照片标签资料
     * @param request
     * @param response
     * @param baseInfo
     * @param callback
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryUserProfile.json", method = RequestMethod.GET)
    @ResponseBody
    @Login(required = true)
    public JSONPObject queryUserProfile(HttpServletRequest request,HttpServletResponse response,
                                        BaseInfo baseInfo,@RequestParam String callback) throws Exception {
        EntityJDResult result = new EntityJDResult(true, ServiceResponseConstants.FAILURE.getCode()
                ,ServiceResponseConstants.FAILURE.getMsg());
        BaseInfo userInfo = getLoginAccount();
        if(StringUtils.isEmpty(userInfo.getEmail())) {
            return new JSONPObject(callback, result);
        }
        UserProfileDto userProfileDto = new UserProfileDto();
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
        BeanUtils.copyProperties(userProfileDto, allUserInfo);
        result.setRetCode(ServiceResponseConstants.SUCCESS.getCode());
        result.setRetMsg(ServiceResponseConstants.SUCCESS.getMsg());
        result.setData(userProfileDto);
        return new JSONPObject(callback, result);
    }

    /**
     * 保存用户照片
     * @param request
     * @param response
     * @param picLoc
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveUserPic.json", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    @Login(required = true)
    public JDResult saveUserPic(HttpServletRequest request,HttpServletResponse response,
                                   String picLoc,String imgData) throws Exception {
        JDResult result = new JDResult(true, ServiceResponseConstants.FAILURE.getCode()
                ,ServiceResponseConstants.FAILURE.getMsg());
        BaseInfo userInfo = getLoginAccount();
        if(StringUtils.isEmpty(userInfo.getEmail())) {
            return result;
        }
        UserProfile profile = new UserProfile();
        try {
            profile.setEmail(userInfo.getEmail());
            String pic = userDao.queryUserPic(profile);
            JSONObject picJson = null;
            if(StringUtils.isEmpty(pic)) {
                picJson = JSON.parseObject("{}");
            } else {
                picJson = JSON.parseObject(pic);
            }
            picJson.put(picLoc,imgData);
            profile.setPic(picJson.toJSONString());
            userDao.updateUserPic(profile);
        } catch(Exception e) {
            return result;
        }
        result.setRetCode(ServiceResponseConstants.SUCCESS.getCode());
        result.setRetMsg(ServiceResponseConstants.SUCCESS.getMsg());
        return result;
    }

    /**
     * 删除用户照片
     * @param request
     * @param response
     * @param picLoc
     * @param callback
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/removeUserPic.json", method = RequestMethod.GET)
    @ResponseBody
    @Login(required = true)
    public JSONPObject removeUserPic(HttpServletRequest request,HttpServletResponse response,
                                        String picLoc,@RequestParam String callback) throws Exception {
        JDResult result = new JDResult(true, ServiceResponseConstants.FAILURE.getCode()
                ,ServiceResponseConstants.FAILURE.getMsg());
        BaseInfo userInfo = getLoginAccount();
        if(StringUtils.isEmpty(userInfo.getEmail())) {
            return new JSONPObject(callback, result);
        }
        UserProfile profile = new UserProfile();
        try {
            profile.setEmail(userInfo.getEmail());
            String pic = userDao.queryUserPic(profile);
            JSONObject picJson = null;
            if(!StringUtils.isEmpty(pic)) {
                picJson = JSON.parseObject(pic);
                picJson.remove(picLoc);
                profile.setPic(picJson.toJSONString());
                userDao.updateUserPic(profile);
            }
        } catch(Exception e) {
            return new JSONPObject(callback, result);
        }
        result.setRetCode(ServiceResponseConstants.SUCCESS.getCode());
        result.setRetMsg(ServiceResponseConstants.SUCCESS.getMsg());
        return new JSONPObject(callback, result);
    }

    /**
     * 保存用户标签
     * @param request
     * @param response
     * @param lable
     * @param callback
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateUserLable.json", method = RequestMethod.GET)
    @ResponseBody
    @Login(required = true)
    public JSONPObject updateUserLable(HttpServletRequest request,HttpServletResponse response,
                                     String lable,@RequestParam String callback) throws Exception {
        JDResult result = new JDResult(true, ServiceResponseConstants.FAILURE.getCode()
                ,ServiceResponseConstants.FAILURE.getMsg());
        BaseInfo userInfo = getLoginAccount();
        if(StringUtils.isEmpty(userInfo.getEmail())) {
            return new JSONPObject(callback, result);
        }
        UserProfile profile = new UserProfile();
        try {
            profile.setEmail(userInfo.getEmail());
            profile.setLable(lable);
            userDao.updateUserLable(profile);
        } catch(Exception e) {
            return new JSONPObject(callback, result);
        }
        result.setRetCode(ServiceResponseConstants.SUCCESS.getCode());
        result.setRetMsg(ServiceResponseConstants.SUCCESS.getMsg());
        return new JSONPObject(callback, result);
    }
}
