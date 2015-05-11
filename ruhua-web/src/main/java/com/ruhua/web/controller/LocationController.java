package com.ruhua.web.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.ruhua.common.map.MapDistance;
import com.ruhua.common.response.EntityJDResult;
import com.ruhua.common.response.JDResult;
import com.ruhua.dao.UserDao;
import com.ruhua.domain.constants.ServiceResponseConstants;
import com.ruhua.domain.geo.Coordinate;
import com.ruhua.domain.geo.POI;
import com.ruhua.domain.user.BaseInfo;
import com.ruhua.domain.user.UserInfo;
import com.ruhua.service.LocationService;
import com.ruhua.web.interceptor.Login;
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
import java.math.BigDecimal;
import java.util.List;

/**
 * 与地理位置有关
 * @author lijing3
 *
 */
@Controller
@RequestMapping(value="/location")
public class LocationController extends BaseController {
	Logger logger = Logger.getLogger(LocationController.class);
    @Resource
    private LocationService locationService;
    @Resource
    private UserDao userDao;

	/**
	 * 根据关键字查找周边的poi信息
     * @return
     */
    @RequestMapping(value = "/findSurroundedPOI.json")
    @ResponseBody
    @Login(required = true)
    public JSONPObject findSurroundedPOI(HttpServletRequest request,HttpServletResponse response,int type,
                                         Coordinate lnglat,@RequestParam String callback) {
    	lnglat = locationService.transLngLat(lnglat, 1, 5);//客户端直接用百度地图获取的百度坐标
        EntityJDResult result = new EntityJDResult(true, ServiceResponseConstants.FAILURE.getCode()
                ,ServiceResponseConstants.FAILURE.getMsg());
        List<POI> poiList = locationService.getPOIList(type,lnglat);
        if(poiList != null && poiList.size() >= 0) {
            result.setRetCode(ServiceResponseConstants.SUCCESS.getCode());
            result.setRetMsg(ServiceResponseConstants.SUCCESS.getMsg());
            result.setData(poiList);
        }
        return new JSONPObject(callback, result);
    }

    @RequestMapping(value = "/queryDestinationByType.json", method = RequestMethod.GET)
    @ResponseBody
    @Login(required = true)
    public JSONPObject queryDestinationByType(HttpServletRequest request,HttpServletResponse response,
                                              int type,Coordinate lnglat,@RequestParam String callback) throws Exception {
        EntityJDResult result = new EntityJDResult(true, ServiceResponseConstants.FAILURE.getCode()
                ,ServiceResponseConstants.FAILURE.getMsg());
        BaseInfo userInfo = getLoginAccount();
        if(StringUtils.isEmpty(userInfo.getEmail())) {
            return new JSONPObject(callback, result);
        }
        UserInfo allUserInfo = userDao.queryUserInfoByEmail(userInfo.getEmail());
        double lng = allUserInfo.getLng();
        double lat = allUserInfo.getLat();
        if(lng <= 0 || lat <= 0 || lnglat.getLng() <= 0 || lnglat.getLat() <= 0) {
            return new JSONPObject(callback, result);
        }
        double targetLng = Math.abs(new BigDecimal(lng + lnglat.getLng()).divide(new BigDecimal(2)).doubleValue());
        double targetLat = Math.abs(new BigDecimal(lat + lnglat.getLat()).divide(new BigDecimal(2)).doubleValue());
        List<POI> poiList = locationService.getPOIList(type , new Coordinate(targetLng,targetLat));
        if(poiList != null && poiList.size() > 0) {
            for(POI p : poiList) {
                p.setDis4u(MapDistance.getDistance(allUserInfo.getLat(),allUserInfo.getLng(),p.getCoordinate().getLat(),p.getCoordinate().getLng()));
                p.setDis4ta(MapDistance.getDistance(lnglat.getLat(),lnglat.getLng(),p.getCoordinate().getLat(),p.getCoordinate().getLng()));
            }
            result.setData(poiList);
            result.setRetCode(ServiceResponseConstants.SUCCESS.getCode());
            result.setRetMsg(ServiceResponseConstants.SUCCESS.getMsg());
        }
        return new JSONPObject(callback, result);
    }


}
