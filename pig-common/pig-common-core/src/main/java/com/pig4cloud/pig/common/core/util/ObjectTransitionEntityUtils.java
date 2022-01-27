package com.pig4cloud.pig.common.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ObjectTransitionEntityUtils {

	public static  <T> T readValueMap(Object obj,Class valueType) throws Exception {
		ObjectMapper objectMapper=new ObjectMapper();
		String str=null;
		try {
			str=objectMapper.writeValueAsString(obj);
			return (T)objectMapper.readValue(str,valueType);

		}catch (IOException e){
			throw new Exception("实体类转换异常");
		}
	}
}
