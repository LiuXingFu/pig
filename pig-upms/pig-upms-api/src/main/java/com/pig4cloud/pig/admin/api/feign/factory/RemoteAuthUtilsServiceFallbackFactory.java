package com.pig4cloud.pig.admin.api.feign.factory;

import com.pig4cloud.pig.admin.api.feign.RemoteAuthUtilsService;
import com.pig4cloud.pig.admin.api.feign.fallback.RemoteAuthUtilsServiceFallbackImpl;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteAuthUtilsServiceFallbackFactory implements FallbackFactory<RemoteAuthUtilsService> {

	@Override
	public RemoteAuthUtilsService create(Throwable throwable) {
		RemoteAuthUtilsServiceFallbackImpl remoteAuthUtilsServiceFallback = new RemoteAuthUtilsServiceFallbackImpl();
		remoteAuthUtilsServiceFallback.setCause(throwable);
		return remoteAuthUtilsServiceFallback;
	}
}
