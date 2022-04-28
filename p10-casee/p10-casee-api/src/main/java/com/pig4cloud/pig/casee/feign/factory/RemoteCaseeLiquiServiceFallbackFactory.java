package com.pig4cloud.pig.casee.feign.factory;

import com.pig4cloud.pig.casee.feign.RemoteCaseeLiquiService;
import com.pig4cloud.pig.casee.feign.fallback.RemoteCaseeLiquiServiceFallbackImpl;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteCaseeLiquiServiceFallbackFactory implements FallbackFactory<RemoteCaseeLiquiService> {
	@Override
	public RemoteCaseeLiquiService create(Throwable cause) {
		RemoteCaseeLiquiServiceFallbackImpl remoteCaseeLiquiServiceFallback = new RemoteCaseeLiquiServiceFallbackImpl();
		remoteCaseeLiquiServiceFallback.setCause(cause);
		return remoteCaseeLiquiServiceFallback;
	}
}
