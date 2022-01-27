package com.pig4cloud.pig.admin.api.feign.fallback;

import com.pig4cloud.pig.admin.api.feign.RemoteAuthUtilsService;
import com.pig4cloud.pig.common.core.util.R;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RemoteAuthUtilsServiceFallbackImpl implements RemoteAuthUtilsService {

	@Setter
	private Throwable cause;

	@Override
	public R getUseData(String uni_id, String uni_id_token, String from) {
		log.error("根据uniId与uniIdToken拉取用户数据", cause);
		return null;
	}

	@Override
	public R pushAppMessage(List<String> clientids, String title, String content, String from) {
		log.error("推送消息", cause);
		return null;
	}

}
