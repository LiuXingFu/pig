package com.pig4cloud.pig.casee.feign.factory;

import com.pig4cloud.pig.casee.feign.RemoteProjectService;
import com.pig4cloud.pig.casee.feign.fallback.RemoteProjectServiceFallbackImpl;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.feign.factory
 * @ClassNAME: RemoteProjectServiceFallbackFactory
 * @Author: yd
 * @DATE: 2022/2/20
 * @TIME: 14:34
 * @DAY_NAME_SHORT: 周日
 */
@Component
public class RemoteProjectServiceFallbackFactory implements FallbackFactory<RemoteProjectService> {

	@Override
	public RemoteProjectService create(Throwable throwable) {
		RemoteProjectServiceFallbackImpl remoteProjectServiceFallbackFactory = new RemoteProjectServiceFallbackImpl();
		remoteProjectServiceFallbackFactory.setCause(throwable);
		return remoteProjectServiceFallbackFactory;
	}

}
