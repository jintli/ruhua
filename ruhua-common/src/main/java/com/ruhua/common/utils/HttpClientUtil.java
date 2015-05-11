package com.ruhua.common.utils;

import com.alibaba.fastjson.TypeReference;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Http接口调用工具类
 * 
 * 调用例子：param, ApiRe
 *      String url = "http://www.xxx.ddd/api?param=data";
 * 		HttpClientParameter param = new HttpClientParameter(url,"GBK")t(sultDTO.class);
 * @author wenjun;
 * 		ApiResultDTO apiRst = httpClientUtil.ge
 *
 */
@Service
public class HttpClientUtil{
	
	/**
	 * 日志记录类
	 */
	private final static Logger log = Logger.getLogger(HttpClientUtil.class);
	
	private final static String Log_Prefix="HTTP_CLIENT_CALL_API_";
	
	private static boolean isLogRtnData;
	
	public static boolean isLogRtnData() {
		return isLogRtnData;
	}

	public static void setLogRtnData(boolean isLogRtnData) {
		HttpClientUtil.isLogRtnData = isLogRtnData;
	}

	/**
	 * 检查参数并设置默认值
	 * @param param
	 * @param isCheckData
	 */
	private void setDefaultValueAndCheckParam(HttpClientParameter param,boolean isCheckData){
		
		Assert.notNull(param, HttpClientParameter.class.getName()+":param is empty!");
		
		Assert.notNull(param.getUrl(),HttpClientParameter.class.getName()+":url is empty!");
		
		if(isCheckData){
			Assert.notNull(param.getPostData(),HttpClientParameter.class.getName()+":postData is empty!");
		}
		
		if(param.getUrlEncodeChartSet()==null || param.getUrlEncodeChartSet().trim().equals("")){
			param.setUrlEncodeChartSet("UTF-8");
		}
		
		if(param.getRequestContentChartSet()==null || param.getRequestContentChartSet().trim().equals("")){
			param.setRequestContentChartSet("UTF-8");
		}
		
		if(param.getContentType()==null || param.getContentType().trim().equals("")){
			param.setContentType("application/json");
		}
		
		//设置对返回结果解码方式
		if(param.getResponseDecoderChartSet()==null || param.getResponseDecoderChartSet().trim().equals("")){
			param.setResponseDecoderChartSet(null);
		}
		
		//设置是否对返回结果打日志的标志
		if(param.getIsLogRtnData()==null){
			param.setIsLogRtnData(isLogRtnData);
			if(param.getIsLogRtnData()==null){
				param.setIsLogRtnData(false);
			}
		}
	}


	/**
	 * rest接口 post方式调用
	 * @param param   httpClient调用参数
	 * @param rtnType 返回类型
	 * @return
	 */ 
	@SuppressWarnings("unchecked")
	public <T> T post(HttpClientParameter param,Class<T> rtnType) {
		T rtnRst = null;
		String apiRstStr = "";
		
		//设置默认值
    	setDefaultValueAndCheckParam(param,true);
    	
		String apiUrl = param.getUrl();
		PostMethod post = new PostMethod(apiUrl);
        try {
        	String jsonStrData = (param.getPostData() instanceof String) ? (String)param.getPostData() : FastJsonWrap.toJson(param.getPostData());
    		RequestEntity requestEntity = new StringRequestEntity(jsonStrData, param.getContentType(), param.getRequestContentChartSet());
    		post.setRequestEntity(requestEntity);
    		post.addRequestHeader("Accept" , param.getContentType());
    		post.getParams().setContentCharset(param.getRequestContentChartSet());
    		
            HttpClient httpclient = new HttpClient(); 
            int result = httpclient.executeMethod(post);
            if(result!=200){
            	log.error(Log_Prefix+"ERR|CODE:"+result+" URL:"+apiUrl);
            	return rtnRst;
            }
            apiRstStr = post.getResponseBodyAsString();
            if(param.getResponseDecoderChartSet()!=null){
            	apiRstStr = URLDecoder.decode(apiRstStr, param.getResponseDecoderChartSet());
            }
            if(param.getIsLogRtnData()){
            	log.error(Log_Prefix+"TIP|URL:"+apiUrl+" APIRST:"+apiRstStr);
            }
            rtnRst = (T)FastJsonWrap.toObject(apiRstStr, rtnType);
        }catch(Exception ex){
        	log.error(Log_Prefix+"EX|MSG:"+ex.getLocalizedMessage()+" URL:"+apiUrl+" APIRST:"+apiRstStr,ex);
        }
        finally {
        	post.releaseConnection();
        }
		return rtnRst;
	}
	
	/**
	 * rest接口 get方式调用
	 * @param param   httpClient调用参数
	 * @param rtnType 返回类型
	 * @return
	 */  
	@SuppressWarnings("unchecked")
	public <T> T get(HttpClientParameter param,Class<T> rtnType) {
		T rtnRst = null;
		String apiRstStr = ""; 
		//设置默认值
    	setDefaultValueAndCheckParam(param,false);
    	
		String apiUrl = param.getUrl();
		GetMethod get = new GetMethod(apiUrl);
		get.addRequestHeader("Accept" ,  param.getContentType());
		get.getParams().setContentCharset(param.getRequestContentChartSet());
        HttpClient httpclient = new HttpClient(); 
    	try {
             int result = httpclient.executeMethod(get);
             if(result!=200){
             	log.error(Log_Prefix+"ERR|CODE:"+result+" URL:"+apiUrl);
             	return rtnRst;
             }
             apiRstStr = get.getResponseBodyAsString();
            log.info("request url is before "+apiUrl+"url result "+apiRstStr);
             if(param.getResponseDecoderChartSet()!=null){
            	 apiRstStr = URLDecoder.decode(apiRstStr, param.getResponseDecoderChartSet());
             }

            log.info("request url is "+apiUrl+"url result "+apiRstStr);
             if(param.getIsLogRtnData()){
            	 log.error(Log_Prefix+"TIP|URL:"+apiUrl+" APIRST:"+apiRstStr);
             }
             rtnRst = (T)FastJsonWrap.toObject(apiRstStr, rtnType);
         }catch(Exception ex){
         	log.error(Log_Prefix+"EX|MSG:"+ex.getLocalizedMessage()+" URL:"+apiUrl+" APIRST:"+apiRstStr,ex);
         }
         finally {
             get.releaseConnection();
         }
		return rtnRst;
	}



    /**
     * rest接口 get方式调用
     * @param param   httpClient调用参数
     * @param rtnType 返回类型
     * @return
     */
    @SuppressWarnings("unchecked")
	public <T> List<T> getArray(HttpClientParameter param,Class<T> rtnType) {
        List<T> rtnRst = null;
        String apiRstStr = "";
        //设置默认值
        setDefaultValueAndCheckParam(param,false);

        String apiUrl = param.getUrl();
        GetMethod get = new GetMethod(apiUrl);
        get.addRequestHeader("Accept" ,  param.getContentType());
        get.getParams().setContentCharset(param.getRequestContentChartSet());
        HttpClient httpclient = new HttpClient();
        try {
            int result = httpclient.executeMethod(get);
            if(result!=200){
                log.error(Log_Prefix+"ERR|CODE:"+result+" URL:"+apiUrl);
                return rtnRst;
            }
            apiRstStr = get.getResponseBodyAsString();
            log.info("request url is before "+apiUrl+"url result "+apiRstStr);
            if(param.getResponseDecoderChartSet()!=null){
                apiRstStr = URLDecoder.decode(apiRstStr, param.getResponseDecoderChartSet());
            }

            log.info("request url is "+apiUrl+"url result "+apiRstStr);
            if(param.getIsLogRtnData()){
                log.error(Log_Prefix+"TIP|URL:"+apiUrl+" APIRST:"+apiRstStr);
            }
            rtnRst = (List<T>)FastJsonWrap.toObject(apiRstStr, new TypeReference<List<T>>(){});
        }catch(Exception ex){
            log.error(Log_Prefix+"EX|MSG:"+ex.getLocalizedMessage()+" URL:"+apiUrl+" APIRST:"+apiRstStr,ex);
        }
        finally {
            get.releaseConnection();
        }
        return rtnRst;
    }
    
    /**
     * 对url进行编码
     * @param clientParam
     * @param client
     * @param urlParams
     */
    public void encodeUrl(HttpClientParameter clientParam, String... urlParams){
    	try{
    		String url = clientParam.getUrl().trim();
        	if(url.indexOf("?")<0){
        		url += "?";
        	}
        	List<String> paramList = new ArrayList<String>();
        	if(urlParams!=null && urlParams.length>0 ){
        		for(int i=0;i<urlParams.length;i+=2){
        			String curKey = urlParams[i];
        			String curVal = URLEncoder.encode(urlParams[i+1], clientParam.getUrlEncodeChartSet());
        			if(curKey.startsWith("@{") && curKey.endsWith("}")){
        				//
                		url = url.replace(curKey, curVal);
                	}else{
                		paramList.add(curKey+"="+curVal);
                	}
        		}
        	} 
        	if(!url.endsWith("?") && !url.endsWith("&") && paramList.size()>0){
        		url += "&";
        	} 
        	url += StringUtils.join(paramList, "&");
        	clientParam.setUrl(url);
    	}catch(Throwable ex){
    		log.error(Log_Prefix+"EX|encodeUrl"+ex.getLocalizedMessage(),ex);
    	}
    }
    
    /**
     * 对数据进行编码
     * @param text 
     * @param enc
     */
    public String encodeData(String text,String enc){
    	if(StringUtils.isNotEmpty(text)){
    		try {
    			return URLEncoder.encode(text,enc);
			} catch (UnsupportedEncodingException e) { 
				e.printStackTrace();
			}
    	}
    	return text;
    }
    
    /**
     * 对数据进行解码
     * @param text 
     * @param enc
     */
    public String decodeData(String text,String enc){
    	if(StringUtils.isNotEmpty(text)){
    		try {
    			return URLDecoder.decode(text,enc);
			} catch (UnsupportedEncodingException e) { 
				e.printStackTrace();
			}
    	}
    	return text;
    }
}
