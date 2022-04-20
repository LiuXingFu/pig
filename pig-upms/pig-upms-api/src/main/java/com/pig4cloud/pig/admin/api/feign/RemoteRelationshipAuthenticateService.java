package com.pig4cloud.pig.admin.api.feign;

import com.pig4cloud.pig.admin.api.feign.factory.RemoteOutlesTemplateReServiceFallbackFactory;
import com.pig4cloud.pig.admin.api.feign.factory.RemoteRelationshipAuthenticateServiceFallbackFactory;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.constant.ServiceNameConstants;
import com.pig4cloud.pig.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(contextId = "remoteRelationshipAuthenticateService", value = ServiceNameConstants.UMPS_SERVICE,
		fallbackFactory = RemoteRelationshipAuthenticateServiceFallbackFactory.class)
public interface RemoteRelationshipAuthenticateService {

	/**
	 * 通过AuthenticateId查询authenticateGoalId
	 * @return
	 */
	@GetMapping("/relationshipauthenticate/getByAuthenticateId/{authenticateId}" )
	R<Integer> getByAuthenticateId(@PathVariable("authenticateId") Integer authenticateId, @RequestHeader(SecurityConstants.FROM) String from);

}
