package com.pig4cloud.pig.admin.api.feign.factory;

import com.pig4cloud.pig.admin.api.feign.RemoteRelationshipAuthenticateService;
import com.pig4cloud.pig.admin.api.feign.fallback.RemoteRelationshipAuthenticateServiceFallbackImpl;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteRelationshipAuthenticateServiceFallbackFactory implements FallbackFactory<RemoteRelationshipAuthenticateService> {

	@Override
	public RemoteRelationshipAuthenticateService create(Throwable cause) {
		RemoteRelationshipAuthenticateServiceFallbackImpl remoteRelationshipAuthenticateServiceFallback = new RemoteRelationshipAuthenticateServiceFallbackImpl();
		remoteRelationshipAuthenticateServiceFallback.setCause(cause);
		return remoteRelationshipAuthenticateServiceFallback;
	}
}
