package com.ruhua.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.ruhua.common.map.GeoUtils;
import com.ruhua.common.response.EntityJDResult;
import com.ruhua.dao.RecommendDao;
import com.ruhua.dao.UserDao;
import com.ruhua.domain.constants.ServiceResponseConstants;
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
import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 用于搭讪的推荐
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-11-30
 * Time: 下午1:22
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/recommend")
public class RecommendController extends BaseController {

    private Logger logger = Logger.getLogger(RecommendController.class);

    @Resource
    private UserDao userDao;
    @Resource
    private RecommendDao recommendDao;

    @RequestMapping(value = "/queryTodayRecommend.json", method = RequestMethod.GET)
    @ResponseBody
    @Login(required = true)
    public JSONPObject queryTodayRecommend(HttpServletRequest request,HttpServletResponse response,
                                     @RequestParam String callback) throws Exception {
        EntityJDResult result = new EntityJDResult(true, ServiceResponseConstants.FAILURE.getCode()
                ,ServiceResponseConstants.FAILURE.getMsg());
        BaseInfo userInfo = getLoginAccount();
        if(StringUtils.isEmpty(userInfo.getEmail())) {
            return new JSONPObject(callback, result);
        }
        //检查资料是否齐全
        UserInfo allUserInfo = userDao.queryUserInfoByEmail(userInfo.getEmail());
        if(allUserInfo.getPic() == null || allUserInfo.getPic().length() <=20
                || allUserInfo.getAge() <= 0 || allUserInfo.getLng() <= 0 || allUserInfo.getLat() <= 0) {
            result.setRetCode(ServiceResponseConstants.USER_INFO_LOSS.getCode());
            result.setRetMsg(ServiceResponseConstants.USER_INFO_LOSS.getMsg());
            return new JSONPObject(callback, result);
        }
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("email",userInfo.getEmail());
        double[] surrond = GeoUtils.getAround(allUserInfo.getLat(),allUserInfo.getLng(),5000);
        paramMap.put("minLat", surrond[0]);
        paramMap.put("maxLat", surrond[1]);
        paramMap.put("minLng", surrond[2]);
        paramMap.put("maxLng", surrond[3]);
        paramMap.put("love",allUserInfo.getLove());
        List<String> recommendEmail = recommendDao.getRecommendList(paramMap);
        result.setRetCode(ServiceResponseConstants.SUCCESS.getCode());
        result.setRetMsg(ServiceResponseConstants.SUCCESS.getMsg());
        result.setData(recommendEmail);
        return new JSONPObject(callback, result);
    }


    @RequestMapping(value = "/queryRecommendInfo.json", method = RequestMethod.GET)
    @ResponseBody
    @Login(required = true)
    public JSONPObject queryRecommendInfo(HttpServletRequest request,HttpServletResponse response,
                                           String email,@RequestParam String callback) throws Exception {
        EntityJDResult result = new EntityJDResult(true, ServiceResponseConstants.FAILURE.getCode()
                ,ServiceResponseConstants.FAILURE.getMsg());
        BaseInfo userInfo = getLoginAccount();
        if(StringUtils.isEmpty(userInfo.getEmail())) {
            return new JSONPObject(callback, result);
        }
        UserInfo allUserInfo = userDao.queryUserInfoByEmail(email);
        result.setRetCode(ServiceResponseConstants.SUCCESS.getCode());
        result.setRetMsg(ServiceResponseConstants.SUCCESS.getMsg());
        result.setData(allUserInfo);
        return new JSONPObject(callback, result);
    }

    @RequestMapping(value = "/insertLikePerson.json", method = RequestMethod.GET)
    @ResponseBody
    @Login(required = true)
    public JSONPObject insertLikePerson(HttpServletRequest request,HttpServletResponse response,
                                          String email,@RequestParam String callback) throws Exception {
        EntityJDResult result = new EntityJDResult(true, ServiceResponseConstants.FAILURE.getCode()
                ,ServiceResponseConstants.FAILURE.getMsg());
        BaseInfo userInfo = getLoginAccount();
        if(StringUtils.isEmpty(userInfo.getEmail())) {
            return new JSONPObject(callback, result);
        }
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("email",userInfo.getEmail());
        paramMap.put("target_email",email);
        if(recommendDao.insertLikePerson(paramMap)) {
            result.setRetCode(ServiceResponseConstants.SUCCESS.getCode());
            result.setRetMsg(ServiceResponseConstants.SUCCESS.getMsg());
        }
        return new JSONPObject(callback, result);
    }

    @RequestMapping(value = "/insertDislikePerson.json", method = RequestMethod.GET)
    @ResponseBody
    @Login(required = true)
    public JSONPObject insertDislikePerson(HttpServletRequest request,HttpServletResponse response,
                                        String email,@RequestParam String callback) throws Exception {
        EntityJDResult result = new EntityJDResult(true, ServiceResponseConstants.FAILURE.getCode()
                ,ServiceResponseConstants.FAILURE.getMsg());
        BaseInfo userInfo = getLoginAccount();
        if(StringUtils.isEmpty(userInfo.getEmail())) {
            return new JSONPObject(callback, result);
        }
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("email",userInfo.getEmail());
        paramMap.put("target_email",email);
        if(recommendDao.insertDislikePerson(paramMap)) {
            result.setRetCode(ServiceResponseConstants.SUCCESS.getCode());
            result.setRetMsg(ServiceResponseConstants.SUCCESS.getMsg());
        }
        return new JSONPObject(callback, result);
    }

    @RequestMapping(value = "/queryLikeMeUserInfo.json", method = RequestMethod.GET)
    @ResponseBody
    @Login(required = true)
    public JSONPObject queryLikeMeUserInfo(HttpServletRequest request,HttpServletResponse response,
                                           @RequestParam String callback) throws Exception {
        EntityJDResult result = new EntityJDResult(true, ServiceResponseConstants.FAILURE.getCode()
                ,ServiceResponseConstants.FAILURE.getMsg());
        BaseInfo userInfo = getLoginAccount();
        if(StringUtils.isEmpty(userInfo.getEmail())) {
            return new JSONPObject(callback, result);
        }
        List<UserInfo> likeMeUserInfoList = recommendDao.queryLikeMeUserInfo(userInfo.getEmail());
        if(likeMeUserInfoList != null && likeMeUserInfoList.size() > 0) {
            result.setRetCode(ServiceResponseConstants.SUCCESS.getCode());
            result.setRetMsg(ServiceResponseConstants.SUCCESS.getMsg());
            List<RecommendUser> recommendUserList = new ArrayList(likeMeUserInfoList.size());
            for(UserInfo userInfo1 : likeMeUserInfoList) {
                RecommendUser recommendUser = new RecommendUser();
                BeanUtils.copyProperties(recommendUser,userInfo1);
                String pic = userInfo1.getPic();
                if(pic != null && JSONObject.parseObject(pic).get("pic001") != null) {
                    recommendUser.setHeadPic(JSONObject.parseObject(pic).getString("pic001"));
                }
                recommendUserList.add(recommendUser);
            }
            result.setData(recommendUserList);
        } else {
            result.setRetCode(ServiceResponseConstants.LIKE_ME_USER_INFO_NULL.getCode());
            result.setRetMsg(ServiceResponseConstants.LIKE_ME_USER_INFO_NULL.getMsg());
        }
        return new JSONPObject(callback, result);
    }
}
