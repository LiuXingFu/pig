package com.pig4cloud.pig.casee.feign;

import com.pig4cloud.pig.casee.entity.Casee;
import com.pig4cloud.pig.casee.entity.liquientity.CaseeLiqui;
import com.pig4cloud.pig.casee.feign.factory.RemoteCaseeLiquiServiceFallbackFactory;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.constant.ServiceNameConstants;
import com.pig4cloud.pig.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(contextId = "RemoteCaseeLiquiService", value = ServiceNameConstants.CASEE_SERVICE,
		fallbackFactory = RemoteCaseeLiquiServiceFallbackFactory.class)
public interface RemoteCaseeLiquiService {

	/**
	 * 根据案件id查询案件详情
	 * @param caseeId
	 * @return
	 */
	@GetMapping("/caseeLiqui/getCaseeLiquiByCaseeId/{caseeId}")
	R<CaseeLiqui> getCaseeLiquiByCaseeId(@PathVariable("caseeId") Integer caseeId, @RequestHeader(SecurityConstants.FROM) String from);

}
