package com.pig4cloud.pig.casee.feign.fallback;

import com.pig4cloud.pig.casee.feign.RemoteProjectService;
import com.pig4cloud.pig.common.core.util.R;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.feign.fallback
 * @ClassNAME: RemoteProjectServiceFallbackFactory
 * @Author: yd
 * @DATE: 2022/2/20
 * @TIME: 14:38
 * @DAY_NAME_SHORT: 周日
 */
@Slf4j
@Component
public class RemoteProjectServiceFallbackImpl implements RemoteProjectService {

	@Setter
	private Throwable cause;

	@Override
	public R<BigDecimal> getProjectAmountBySubjectId(Integer subjectId, String from) {
		log.error("新增主体关联借贷表信息失败", cause);

		return null;
	}
}
