package com.ruhua.service;

import com.ruhua.domain.geo.Coordinate;
import com.ruhua.domain.geo.POI;

import java.util.List;


public interface LocationService {
	/**
	 * 调用百度地图API
	 * @param type
	 * @param lnglat 39.75857,116.552094
	 * @return
	 */
	public List<POI> getPOIList(int type, Coordinate lnglat);
	
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
	public Coordinate transLngLat(Coordinate lnglat,int from,int to);
	
	/**
	 * 使用经纬度转换为当前商圈，通过商圈进行过滤
	 * @param lnglat
	 * @return
	 */
	public String getBusinessByLnglat(Coordinate lnglat);


    /**
     * 通过ip判断城市
     * @param ip
     * @return
     */
    String getCityByIp(String ip);
	
}
