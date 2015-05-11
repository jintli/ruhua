package com.ruhua.common.map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruhua.common.io.HttpRequest;
import com.ruhua.domain.geo.Coordinate;
import org.apache.commons.lang.StringUtils;

/**
 * poi相关工具类
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-4-4
 * Time: 上午9:12
 * To change this template use File | Settings | File Templates.
 */
public class POIUtils {

    private static String regeocURL = "http://ugc.map.soso.com/rgeoc/";
    private POIUtils(){}

    /**
     * 通过坐标信息获取POI信息
     * 详情：http://wiki.open.t.qq.com/index.php/LBS相关/通过经纬度获取地理位置
     * @param coordinate
     * @return
     */
    public static String getPOIByCoordinate(Coordinate coordinate) {
        String respStr = HttpRequest.sendGet(regeocURL, "lnglat=" + String.valueOf(coordinate.getLng()) + ","
                + String.valueOf(coordinate.getLat() + "&reqsrc=wb"));
        if(!StringUtils.isNotEmpty(respStr) || respStr.indexOf("{") < 0) {
            return "";
        }
        JSONObject addressInfo = JSONObject.parseObject(respStr);
        String newLabel = null;
        if(addressInfo != null) {
            JSONObject info = (JSONObject)addressInfo.get("info");
            if(info != null && "0".equals(String.valueOf(info.get("error")))) {
                JSONObject details = (JSONObject)addressInfo.get("detail");
                JSONArray poiList = (JSONArray)details.get("poilist");
                if(poiList.size() > 0) {
                    JSONObject first = (JSONObject)poiList.get(0);
                    newLabel = String.valueOf(first.get("addr")) + String.valueOf(first.get("name"));
                } else {
                    JSONArray results = (JSONArray)details.get("results");
                    if(results.size() > 0) {
                        JSONObject first = (JSONObject)results.get(0);
                        newLabel = String.valueOf(first.get("name"));
                    }
                }
            }
        }
        return newLabel;
    }
}
