package com.pig4cloud.pig.casee.feign.fallback;

import com.pig4cloud.pig.casee.entity.Casee;
import com.pig4cloud.pig.casee.entity.liquientity.CaseeLiqui;
import com.pig4cloud.pig.casee.feign.RemoteCaseeLiquiService;
import com.pig4cloud.pig.common.core.util.R;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteCaseeLiquiServiceFallbackImpl implements RemoteCaseeLiquiService {

	@Setter
	private Throwable cause;

	@Override
	public R<CaseeLiqui> getCaseeLiquiByCaseeId( Integer caseeId, String form) {
		log.error("根据案件id查询案件详情失败！", cause);
		return null;
	}
}
