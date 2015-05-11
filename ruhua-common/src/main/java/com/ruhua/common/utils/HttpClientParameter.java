package com.ruhua.common.utils;

import java.io.Serializable;

public class HttpClientParameter implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String url;
	
	private Boolean isLogRtnData;
	
	private String contentType;
	
	private String urlEncodeChartSet;
	
	private String requestContentChartSet;
	
	private String responseDecoderChartSet;
	
	private Object postData;
	
	public HttpClientParameter(){
		
	}
	
	public HttpClientParameter(String url){
		this(url, false,null, null, null);
	}
	
	public HttpClientParameter(String url, boolean isLogRtnData){
		this(url, isLogRtnData,null, null, null);
	}
	
	public HttpClientParameter(String url, String requestContentChartSet){
		this(url, false, requestContentChartSet, null, null);
	}
	
	public HttpClientParameter(String url, boolean isLogRtnData, String requestContentChartSet){
		this(url, isLogRtnData, requestContentChartSet, null, null);
	}
	
	public HttpClientParameter(String url, boolean isLogRtnData, String requestContentChartSet, String responseDecoderChartSet){
		this(url, isLogRtnData, requestContentChartSet, responseDecoderChartSet, null);
	}
	
	public HttpClientParameter(String url, boolean isLogRtnData, String requestContentChartSet, String responseDecoderChartSet, Object postData){
		this.url = url;
		this.isLogRtnData = isLogRtnData;
		this.requestContentChartSet = requestContentChartSet;
		this.responseDecoderChartSet = responseDecoderChartSet;
		this.postData = postData;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getIsLogRtnData() {
		return isLogRtnData;
	}

	public void setIsLogRtnData(Boolean isLogRtnData) {
		this.isLogRtnData = isLogRtnData;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public String getUrlEncodeChartSet() {
		return urlEncodeChartSet;
	}

	public void setUrlEncodeChartSet(String urlEncodeChartSet) {
		this.urlEncodeChartSet = urlEncodeChartSet;
	}

	public String getRequestContentChartSet() {
		return requestContentChartSet;
	}

	public void setRequestContentChartSet(String requestContentChartSet) {
		this.requestContentChartSet = requestContentChartSet;
	}

	public String getResponseDecoderChartSet() {
		return responseDecoderChartSet;
	}

	public void setResponseDecoderChartSet(String responseDecoderChartSet) {
		this.responseDecoderChartSet = responseDecoderChartSet;
	}

	public Object getPostData() {
		return postData;
	}

	public void setPostData(Object postData) {
		this.postData = postData;
	}
}
