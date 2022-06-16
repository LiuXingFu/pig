package com.pig4cloud.pig.admin.api.feign.factory;

import com.pig4cloud.pig.admin.api.feign.RemoteInstitutionSubjectReService;
import com.pig4cloud.pig.admin.api.feign.fallback.RemoteInstitutionSubjectReServiceFallbackImpl;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteInstitutionSubjectReServiceFallbackFactory implements FallbackFactory<RemoteInstitutionSubjectReService> {

	@Override
	public RemoteInstitutionSubjectReService create(Throwable cause) {
		RemoteInstitutionSubjectReServiceFallbackImpl remoteInstitutionSubjectReServiceFallback = new RemoteInstitutionSubjectReServiceFallbackImpl();
		remoteInstitutionSubjectReServiceFallback.setCause(cause);
		return remoteInstitutionSubjectReServiceFallback;
	}
}
