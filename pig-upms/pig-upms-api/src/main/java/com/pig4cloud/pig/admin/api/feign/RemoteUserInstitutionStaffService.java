package com.pig4cloud.pig.admin.api.feign;

import com.pig4cloud.pig.admin.api.feign.factory.RemoteUserInstitutionStaffServiceFactory;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.constant.ServiceNameConstants;
import com.pig4cloud.pig.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 *
 */
@FeignClient(contextId = "RemoteUserInstitutionStaffService", value = ServiceNameConstants.UMPS_SERVICE,
		fallbackFactory = RemoteUserInstitutionStaffServiceFactory.class)
public interface RemoteUserInstitutionStaffService {

	@GetMapping("/userinstitutionstaff/getStaffOutlesList")
	R<List<Integer>> getStaffOutlesList( @RequestHeader(SecurityConstants.FROM) String from);

}
