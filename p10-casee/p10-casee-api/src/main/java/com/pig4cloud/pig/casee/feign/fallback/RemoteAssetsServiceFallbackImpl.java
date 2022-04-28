package com.pig4cloud.pig.casee.feign.fallback;

import com.pig4cloud.pig.casee.entity.Assets;
import com.pig4cloud.pig.casee.feign.RemoteAssetsService;
import com.pig4cloud.pig.common.core.util.R;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteAssetsServiceFallbackImpl implements RemoteAssetsService {

	@Setter
	private Throwable cause;

	@Override
	public R<Assets> queryAssetsByTargetId(Integer targetId, String from) {
		log.error("根据程序id查询财产失败！", cause);
		return null;
	}
}
