package com.pig4cloud.pig.casee.feign;

import com.pig4cloud.pig.casee.entity.Assets;
import com.pig4cloud.pig.casee.feign.factory.RemoteAssetsServiceFallbackFactory;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.constant.ServiceNameConstants;
import com.pig4cloud.pig.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(contextId = "RemoteAssetsService", value = ServiceNameConstants.CASEE_SERVICE,
		fallbackFactory = RemoteAssetsServiceFallbackFactory.class)
public interface RemoteAssetsService {

	/**
	 * 根据程序id查询财产
	 * @param targetId
	 * @return
	 */
	@GetMapping("/assets/queryAssetsPaifuById/{targetId}")
	R<Assets> queryAssetsByTargetId(@PathVariable("targetId") Integer targetId, @RequestHeader(SecurityConstants.FROM) String from);

}
