package com.pig4cloud.pig.admin.api.feign.fallback;

import com.pig4cloud.pig.admin.api.feign.RemoteRelationshipAuthenticateService;
import com.pig4cloud.pig.common.core.util.R;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteRelationshipAuthenticateServiceFallbackImpl implements RemoteRelationshipAuthenticateService {

	@Setter
	private Throwable cause;

	@Override
	public R getByAuthenticateId(Integer authenticateId, String form) {
		log.error("通过AuthenticateId查询authenticateGoalId失败", cause);
		return null;
	}
}
