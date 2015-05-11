package com.ruhua.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.DateDeserializer;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.lang.reflect.Type;

public class FastJsonWrap {

	public static String toJson(Object obj) {
		return JSONObject.toJSONStringWithDateFormat(obj, "yyyy-MM-dd HH:mm:ss",
						SerializerFeature.WriteDateUseDateFormat);
	}

	public static Object toObject(String jsonString, Class<?> clazz) {
		return JSONObject.parseObject(jsonString, clazz);
	}

	/**
	 * 定制反序列化日期
	 * 
	 * @author wujunde
	 * 
	 * @param jsonString
	 * @param clazz
	 * @param dateFormatPattern
	 *            日期格式化
	 * @return
	 */
	public static Object toObject(String jsonString, Class<?> clazz, final String dateFormatPattern) {
		ParserConfig c = new ParserConfig();
		DateDeserializer dp = new DateDeserializer() {

			@SuppressWarnings("unchecked")
			@Override
			protected <T> T cast(DefaultJSONParser parser, Type clazz,	Object fieldName, Object val) {
				parser.setDateFormat(dateFormatPattern);
				return (T)super.cast(parser, clazz, fieldName, val);
			}
		};

		c.putDeserializer(java.util.Date.class, dp);
		return JSONObject.parseObject(jsonString, clazz, c,	JSON.DEFAULT_PARSER_FEATURE, Feature.values());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object toObject(String jsonString,TypeReference type){
		return JSONObject.parseObject(jsonString,type);
	}
}