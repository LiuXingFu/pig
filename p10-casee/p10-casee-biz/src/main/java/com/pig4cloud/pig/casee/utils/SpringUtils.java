package com.pig4cloud.pig.casee.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtils implements ApplicationContextAware {

	/**
	 * 当前IOC
	 *
	 */
	private static ApplicationContext applicationContext;

	/**
	 * 设置applicationContext
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	//容器中取出Bean
	public static <T> T getObject(Class<T> clazz){
		return applicationContext.getBean(clazz);
	}
}
