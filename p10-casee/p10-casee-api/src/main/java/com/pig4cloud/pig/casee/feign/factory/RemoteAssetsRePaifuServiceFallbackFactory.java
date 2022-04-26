package com.pig4cloud.pig.casee.feign.factory;

import com.pig4cloud.pig.casee.feign.RemoteAssetsRePaifuService;
import com.pig4cloud.pig.casee.feign.RemoteLiquiTransferRecordService;
import com.pig4cloud.pig.casee.feign.fallback.RemoteAssetsRePaifuServiceFallbackImpl;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteAssetsRePaifuServiceFallbackFactory implements FallbackFactory<RemoteAssetsRePaifuService> {


	@Override
	public RemoteAssetsRePaifuService create(Throwable cause) {
		RemoteAssetsRePaifuServiceFallbackImpl remoteAssetsRePaifuServiceFallback = new RemoteAssetsRePaifuServiceFallbackImpl();
		remoteAssetsRePaifuServiceFallback.setCause(cause);
		return remoteAssetsRePaifuServiceFallback;
	}
}
