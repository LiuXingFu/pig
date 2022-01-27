package com.pig4cloud.pig.common.mybatis.config;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * MybatisPlus 自动填充配置
 *
 * @author L.cm
 */
@Slf4j
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		log.debug("mybatis plus start insert fill ....");
		LocalDateTime now = LocalDateTime.now();

		fillValIfNullByName("createTime", now, metaObject, false);
		fillValIfNullByName("updateTime", now, metaObject, false);
		fillValIfNullByName("createBy", getUserId(), metaObject, false);
		fillValIfNullByName("insId", getUserInsId(), metaObject, false);
		fillValIfNullByName("updateBy", getUserId(), metaObject, false);
		fillValIfNullByName("updateInsId", getUserInsId(), metaObject, false);
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		log.debug("mybatis plus start update fill ....");
		fillValIfNullByName("updateTime", LocalDateTime.now(), metaObject, true);
		fillValIfNullByName("updateBy", getUserId(), metaObject, true);
		fillValIfNullByName("updateInsId", getUserInsId(), metaObject, false);
	}

	/**
	 * 填充值，先判断是否有手动设置，优先手动设置的值，例如：job必须手动设置
	 * @param fieldName 属性名
	 * @param fieldVal 属性值
	 * @param metaObject MetaObject
	 * @param isCover 是否覆盖原有值,避免更新操作手动入参
	 */
	private static void fillValIfNullByName(String fieldName, Object fieldVal, MetaObject metaObject, boolean isCover) {
		// 1. 没有 get 方法
		if (!metaObject.hasSetter(fieldName)) {
			return;
		}
		// 2. 如果用户有手动设置的值
		Object userSetValue = metaObject.getValue(fieldName);
		String setValueStr = StrUtil.str(userSetValue, Charset.defaultCharset());
		if (StrUtil.isNotBlank(setValueStr) && !isCover) {
			return;
		}
		// 3. field 类型相同时设置
		Class<?> getterType = metaObject.getGetterType(fieldName);
		if (ClassUtils.isAssignableValue(getterType, fieldVal)) {
			metaObject.setValue(fieldName, fieldVal);
		}
	}

	/**
	 * 获取 spring security 当前的用户名
	 * @return 当前用户名
	 */
	private String getUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (Optional.ofNullable(authentication).isPresent()) {
			return authentication.getName();
		}
		return null;
	}

	/**
	 * created by xiaojun
	 * 获取 spring security 当前的用户ID
	 * @return 当前用户名
	 */
	private Integer getUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if (Optional.ofNullable(principal).isPresent()) {
			try {
				Method getIdMethod = principal.getClass().getDeclaredMethod("getId");
				Object userId = getIdMethod.invoke(principal);
				if(userId instanceof Integer){
					return (Integer) userId;
				}
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * created by xiaojun
	 * 获取 spring security 当前的用户机构ID
	 * @return 当前用户名
	 */
	private Integer getUserInsId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if (Optional.ofNullable(principal).isPresent()) {
			try {
				Method getInsIdMethod = principal.getClass().getDeclaredMethod("getInsId");
				Object insId = getInsIdMethod.invoke(principal);
				if(insId instanceof Integer){
					return (Integer) insId;
				}
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

}
