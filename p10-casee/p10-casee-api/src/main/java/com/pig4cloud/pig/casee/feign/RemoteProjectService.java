package com.pig4cloud.pig.casee.feign;

import com.pig4cloud.pig.casee.feign.factory.RemoteProjectServiceFallbackFactory;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.constant.ServiceNameConstants;
import com.pig4cloud.pig.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.math.BigDecimal;

@FeignClient(contextId = "RemoteProjectService", value = ServiceNameConstants.CASEE_SERVICE,
		fallbackFactory = RemoteProjectServiceFallbackFactory.class)
public interface RemoteProjectService {

	@GetMapping("/project/getProjectAmountBySubjectId/{subjectId}")
	R<BigDecimal> getProjectAmountBySubjectId(@PathVariable("subjectId") Integer subjectId, @RequestHeader(SecurityConstants.FROM) String from);

}
