package com.pig4cloud.pig.casee.feign.factory;

import com.pig4cloud.pig.casee.feign.RemoteLiquiTransferRecordService;
import com.pig4cloud.pig.casee.feign.fallback.RemoteLiquiTransferRecordServiceFallbackImpl;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteLiquiTransferRecordServiceFallbackFactory implements FallbackFactory<RemoteLiquiTransferRecordService> {


	@Override
	public RemoteLiquiTransferRecordService create(Throwable cause) {
		RemoteLiquiTransferRecordServiceFallbackImpl remoteLiquiTransferRecordServiceFallback = new RemoteLiquiTransferRecordServiceFallbackImpl();
		remoteLiquiTransferRecordServiceFallback.setCause(cause);
		return remoteLiquiTransferRecordServiceFallback;
	}
}
