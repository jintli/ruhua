package com.ruhua.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruhua.common.utils.HttpUtils;
import com.ruhua.domain.geo.Coordinate;
import com.ruhua.domain.geo.POI;
import com.ruhua.service.LocationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-11-25
 * Time: 上午9:56
 * To change this template use File | Settings | File Templates.
 */
@Service
public class LocationServiceImpl implements LocationService {
    Logger logger = Logger.getLogger(LocationServiceImpl.class);

    @Value("${BAIDU_MAP_AK}")
    private String ak;

    /**
     * 调用百度地图API
     * @param type
     * @param lnglat 39.75857,116.552094
     * @return
     */
    public List<POI> getPOIList(int type, Coordinate lnglat) {
        try {
            String typeName = "小区";
            String filter = "";
            switch (type) {
                case 0:
                    typeName= "小区";
                    filter="filter=industry_type:house|sort_name:distance|sort_rule:1";
                    break;
                case 1:
                    typeName= "餐厅";
                    filter="filter=industry_type:cater|sort_name:distance|sort_rule:1";
                    break;
                case 2:
                    typeName= "影院";
                    break;
                case 3:
                    typeName= "ktv";
                    break;
            }
            String httpType = URLEncoder.encode(typeName, "UTF-8");
            StringBuilder requestUrl = new StringBuilder("http://api.map.baidu.com/place/v2/search?ak=");
            requestUrl.append(ak).append("&output=json&query=").append(httpType)
                    .append("&page_size=100&page_num=0&scope=2&location=").append(lnglat.toString())
                    .append("&radius=8000").append("&").append(filter);
            logger.info("requestUrl:" + requestUrl);
            JSONObject jsonObject = HttpUtils.executeHttpGet(requestUrl.toString());
            int status = jsonObject.getIntValue("status");
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            List<POI> result = new ArrayList<POI>();
            if(status == 0) {
                POI poi = null;
                Coordinate tempLnglat = null;
                for(int i=0;i<jsonArray.size();i++) {
                    JSONObject item = jsonArray.getJSONObject(i);
                    poi = new POI();
                    poi.setName(item.getString("name"));
                    poi.setAddress(item.getString("address"));
                    JSONObject location = item.getJSONObject("location");
                    JSONObject detailInfo = item.getJSONObject("detail_info");
                    poi.setType(detailInfo.getString("type"));
                    poi.setPrice(detailInfo.getString("price"));
                    tempLnglat = new Coordinate(location.getDouble("lng"),location.getDouble("lat"));
                    poi.setCoordinate(tempLnglat);
                    result.add(poi);
                }
                return result;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 转换坐标 调用百度地图
     * @param lnglat
     * @param from
     * @param to
     * 1：GPS设备获取的角度坐标;
    2：GPS获取的米制坐标、sogou地图所用坐标;
    3：google地图、soso地图、aliyun地图、mapabc地图和amap地图所用坐标
    4：3中列表地图坐标对应的米制坐标
    5：百度地图采用的经纬度坐标
    6：百度地图采用的米制坐标
    7：mapbar地图坐标;
    8：51地图坐标
     * @return
     */
    public Coordinate transLngLat(Coordinate lnglat,int from,int to) {
        Coordinate result = new Coordinate();
        StringBuilder requestUrl = new StringBuilder("http://api.map.baidu.com/geoconv/v1/?coords=");
        requestUrl.append(lnglat.toRevertString());
        requestUrl.append("&from=").append(from);
        requestUrl.append("&to=").append(to);
        requestUrl.append("&ak=").append(ak);
        JSONObject jsonObject = HttpUtils.executeHttpGet(requestUrl.toString());
        int status = jsonObject.getIntValue("status");
        JSONArray jsonArray = jsonObject.getJSONArray("result");
        if(status == 0) {
            JSONObject location = jsonArray.getJSONObject(0);
            result.setLat(location.getDoubleValue("y"));
            result.setLng(location.getDoubleValue("x"));
            return result;
        } else {
            return null;
        }
    }

    /**
     * 使用经纬度转换为当前商圈，通过商圈进行过滤
     * @param lnglat
     * @return
     */
    public String getBusinessByLnglat(Coordinate lnglat) {
        StringBuilder requestUrl = new StringBuilder("http://api.map.baidu.com/geocoder/v2/?ak=");
        requestUrl.append("6fP46nmEFeIQ5ZHwfPVyGYrA");
        requestUrl.append("&location=").append(lnglat.toString());
        requestUrl.append("&output=json");
        requestUrl.append("&pois=").append(0);
        JSONObject jsonObject = HttpUtils.executeHttpGet(requestUrl.toString());
        int status = jsonObject.getIntValue("status");
        if(status == 0) {
            return jsonObject.getString("business");
        } else {
            return null;
        }
    }

    /**
     * 通过ip判断城市
     *
     * @param ip
     * @return
     */
    @Override
    public String getCityByIp(String ip) {
        StringBuilder requestUrl = new StringBuilder("http://api.map.baidu.com/location/ip?ak=");
        requestUrl.append("6fP46nmEFeIQ5ZHwfPVyGYrA");
        requestUrl.append("&ip=").append(ip);
        JSONObject jsonObject = HttpUtils.executeHttpGet(requestUrl.toString());
        int status = jsonObject.getIntValue("status");
        if(status == 0) {
            return jsonObject.getString("city").substring(0,2);
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
    }
}
