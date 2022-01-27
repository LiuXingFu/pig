package com.pig4cloud.pig.admin.api.feign.factory;

import com.pig4cloud.pig.admin.api.feign.RemoteUserInstitutionStaffService;
import com.pig4cloud.pig.admin.api.feign.fallback.RemoteTokenServiceFallbackImpl;
import com.pig4cloud.pig.admin.api.feign.fallback.RemoteUserInstitutionStaffServiceFallbackImpl;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteUserInstitutionStaffServiceFactory implements FallbackFactory<RemoteUserInstitutionStaffService> {
	@Override
	public RemoteUserInstitutionStaffService create(Throwable throwable) {
		RemoteUserInstitutionStaffServiceFallbackImpl remoteUserInstitutionStaffServiceFallback = new RemoteUserInstitutionStaffServiceFallbackImpl();
		remoteUserInstitutionStaffServiceFallback.setCause(throwable);
		return remoteUserInstitutionStaffServiceFallback;
	}
}
