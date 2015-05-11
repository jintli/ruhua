package com.ruhua.domain.geo;

import java.io.Serializable;

/**
 * 坐标
 * Created by IntelliJ IDEA.
 * User: lijing3
 * Date: 14-4-3
 * Time: 下午2:17
 */
public class Coordinate implements Serializable{
    // 经度
    private double lng;
    // 纬度
    private double lat;
    public Coordinate(){
    }
    public Coordinate(double lng,double lat){
        this.lng = lng;
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return lat + "," + lng;
    }

    public String toRevertString() {
        return lng + "," + lat;
    }
}
