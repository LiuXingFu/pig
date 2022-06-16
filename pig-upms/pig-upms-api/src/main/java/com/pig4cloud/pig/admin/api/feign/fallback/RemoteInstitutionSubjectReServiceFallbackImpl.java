package com.pig4cloud.pig.admin.api.feign.fallback;

import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteInstitutionSubjectReService;
import com.pig4cloud.pig.common.core.util.R;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteInstitutionSubjectReServiceFallbackImpl implements RemoteInstitutionSubjectReService {

	@Setter
	private Throwable cause;

	@Override
	public R<Subject> getSubjectByInsId(Integer insId, String from) {
		log.error("根据机构id查询主体信息失败", cause);
		return null;
	}
}
