package com.ruhua.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class HttpUtils {
	
	public static JSONObject executeHttpGet(String urlStr) {
        String result = null;
        URL url = null;
        HttpURLConnection connection = null;
        InputStreamReader in = null;
        JSONObject jsonObject = null;
        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            in = new InputStreamReader(connection.getInputStream(),"UTF-8");
            BufferedReader bufferedReader = new BufferedReader(in);
            StringBuffer strBuffer = new StringBuffer();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                strBuffer.append(line);
            }
            result = strBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
 
        }
        try {
			jsonObject = JSONObject.parseObject(result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
        return jsonObject;
    }
	
}
