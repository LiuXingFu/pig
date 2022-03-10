package com.pig4cloud.pig.common.core.util;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

public class JsonUtils {
	// 定义jackson对象
	private static final ObjectMapper MAPPER = new ObjectMapper();


	@PostConstruct
	public void init(){
		JSONObject.DEFFAULT_DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
	}

	/**
	 * 将对象转换成json字符串。
	 * <p>Title: pojoToJson</p>
	 * <p>Description: </p>
	 *
	 * @param data
	 * @return
	 */
	public static String objectToJson(Object data) {
		try {
//			String string = MAPPER.writeValueAsString(data);
			String string = JSONObject.toJSONString(data);

			return string;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将对象转换成json字符串
	 * <p>Title: objectToJsonObject</p>
	 * <p>Description: </p>
	 *
	 * @param data
	 * @return
	 */
	public static String objectToJsonObject(Object data) {
		try {
//			String string = MAPPER.writeValueAsString(data);
			return JSONObject.toJSONString(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将json结果集转化为对象
	 *
	 * @param jsonData json数据
	 * @param beanType 对象中的object类型
	 * @return
	 */
	public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
		try {
//			JSONObject.parseObject(jsonDatam, beanType, )
			T t = JSONObject.parseObject(jsonData, beanType);
//			T t = MAPPER.readValue(jsonData, beanType);
			return t;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将json数据转换成pojo对象list
	 * <p>Title: jsonToList</p>
	 * <p>Description: </p>
	 *
	 * @param jsonData
	 * @param beanType
	 * @return
	 */
	public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
//		JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
		try {
//			List<T> list = MAPPER.readValue(jsonData, javaType);
			List<T> list = JSONObject.parseArray(jsonData, beanType);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
