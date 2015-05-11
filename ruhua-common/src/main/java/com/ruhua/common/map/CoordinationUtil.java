package com.ruhua.common.map;

import com.ruhua.domain.geo.Coordinate;

/**
 * 坐标相关
 * Created with IntelliJ IDEA.
 * User: lijing3
 * Date: 14-4-8
 * Time: 上午9:35
 * To change this template use File | Settings | File Templates.
 */
public class CoordinationUtil {

    /**
     * 坐标纠偏 从百度地图坐标系到腾讯地图（微信使用的是腾讯地图）
     * @param baiduCoordination
     * @return
     */
    public static Coordinate rectifyFromBDToTC(Coordinate baiduCoordination) {
        double pi = 3.14159265358979324 * 3000.0 / 180.0;
        double lng = baiduCoordination.getLng() - 0.0065;
        double lat = baiduCoordination.getLat() - 0.006;
        double z = Math.sqrt(lng * lng + lat * lat) - 0.00002 * Math.sin(lat * pi);
        double theta = Math.atan2(lat, lng) - 0.000003 * Math.cos(lng * pi);
        double newLng = z * Math.cos(theta);
        double newLat = z * Math.sin(theta);
        return new Coordinate(newLng,newLat);
    }

    /**
     * 坐标纠偏 从腾讯地图坐标系到百度地图（门店系统使用的是百度地图）
     * @param tencentCoordination
     * @return
     */
    public static Coordinate rectifyFromTCToBD(Coordinate tencentCoordination) {
        double pi = 3.14159265358979324 * 3000.0 / 180.0;
        double lng = tencentCoordination.getLng();
        double lat = tencentCoordination.getLat();
        double z = Math.sqrt(lng * lng + lat * lat) + 0.00002 * Math.sin(lat * pi);
        double theta = Math.atan2(lat, lng) + 0.000003 * Math.cos(lng * pi);
        double newLng = z * Math.cos(theta) + 0.0065;
        double newLat = z * Math.sin(theta) + 0.006;
        return new Coordinate(newLng,newLat);
    }
}
