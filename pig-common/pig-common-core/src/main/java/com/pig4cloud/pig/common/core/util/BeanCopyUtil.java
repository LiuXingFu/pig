
package com.pig4cloud.pig.common.core.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;


/**
 * @ClassName: BeanCopyUtil
 * @version 1.0
 * @Desc: 实体Bean复制
 * @author miaoJianhua
 * @history v1.0
 *
 */
public class BeanCopyUtil {

	

	/**
	 * 
	 * 描述：复制实体 Bean list
	 * @param source 源实体list
	 * @param destCls 目标class
	 * @return 目标实体 bean list
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> List copyListBean(T source, Class destCls) {
		List<T> sourceList = (List<T>) source;
		List<T> objectiveList = new ArrayList<T>() ;
		try {
			T value = null;
			if (sourceList != null && sourceList.size() > 0) {
				for (T t : sourceList) {
					value = (T) Class.forName(destCls.getName()).newInstance();
					PropertyUtils.copyProperties(value, t);
					objectiveList.add((T) value);
				}
			}
		} catch (Exception e) {
		  e.printStackTrace();
		}
		return objectiveList;
	}
	
	
	
	/**
	 * 
	 * 描述：复制实体Bean
	 * @param source 源实体
	 * @param dest 目标实体
	 */
	public static void copyBean(Object source, Object dest){
		try {
			PropertyUtils.copyProperties(dest, source);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static <T> T mergeJSONObject(T from, T add){

		if(from instanceof JSONObject && add instanceof JSONObject){
			for(String key: ((JSONObject)add).keySet()){
				if(((JSONObject)from).get(key) instanceof JSONObject && ((JSONObject)add).get(key) instanceof JSONObject){
					((JSONObject)from).put(key,
							mergeJSONObject( ((JSONObject)from).get(key), ((JSONObject)add).get(key) )
					);
				}else{
					((JSONObject)from).put(key, ((JSONObject)add).get(key));
				}
			}
			return from;
		}else{
			return add;
		}



	}
}
