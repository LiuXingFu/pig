package com.pig4cloud.pig.admin.api.feign.fallback;

import com.pig4cloud.pig.admin.api.feign.RemoteUserInstitutionStaffService;
import com.pig4cloud.pig.common.core.util.R;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteUserInstitutionStaffServiceFallbackImpl implements RemoteUserInstitutionStaffService {

	@Setter
	private Throwable cause;

	@Override
	public R getStaffOutlesList(String from) {
		log.error("调用查询员工的所属网点id集合 失败", cause);
		return null;
	}
}
