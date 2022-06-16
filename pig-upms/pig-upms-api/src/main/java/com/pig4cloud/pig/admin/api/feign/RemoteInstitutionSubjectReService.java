package com.pig4cloud.pig.admin.api.feign;

import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.factory.RemoteInstitutionSubjectReServiceFallbackFactory;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.constant.ServiceNameConstants;
import com.pig4cloud.pig.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(contextId = "RemoteInstitutionSubjectReService", value = ServiceNameConstants.UMPS_SERVICE,
		fallbackFactory = RemoteInstitutionSubjectReServiceFallbackFactory.class)
public interface RemoteInstitutionSubjectReService {

	@GetMapping("/institutionsubjectre/getSubjectByInsId/{insId}")
	R<Subject> getSubjectByInsId(@PathVariable Integer insId, @RequestHeader(SecurityConstants.FROM) String from);

}
