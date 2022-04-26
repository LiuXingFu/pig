package com.pig4cloud.pig.casee.feign.factory;

import com.pig4cloud.pig.casee.feign.RemoteAssetsService;
import com.pig4cloud.pig.casee.feign.fallback.RemoteAssetsServiceFallbackImpl;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteAssetsServiceFallbackFactory implements FallbackFactory<RemoteAssetsService>{


	@Override
	public RemoteAssetsService create(Throwable cause) {
		RemoteAssetsServiceFallbackImpl remoteAssetsServiceFallback = new RemoteAssetsServiceFallbackImpl();
		remoteAssetsServiceFallback.setCause(cause);
		return remoteAssetsServiceFallback;
	}
}
