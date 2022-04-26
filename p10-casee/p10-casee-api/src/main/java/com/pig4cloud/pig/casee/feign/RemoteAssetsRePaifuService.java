package com.pig4cloud.pig.casee.feign;

import com.pig4cloud.pig.casee.feign.factory.RemoteAssetsRePaifuServiceFallbackFactory;
import com.pig4cloud.pig.common.core.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(contextId = "RemoteAssetsRePaifuService", value = ServiceNameConstants.CASEE_SERVICE,
		fallbackFactory = RemoteAssetsRePaifuServiceFallbackFactory.class)
public interface RemoteAssetsRePaifuService {

}
