package com.pig4cloud.pig.casee.feign;

import com.pig4cloud.pig.casee.entity.LiquiTransferRecord;
import com.pig4cloud.pig.casee.feign.factory.RemoteLiquiTransferRecordServiceFallbackFactory;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.constant.ServiceNameConstants;
import com.pig4cloud.pig.common.core.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(contextId = "remoteLiquiTransferRecordService", value = ServiceNameConstants.CASEE_SERVICE,
		fallbackFactory = RemoteLiquiTransferRecordServiceFallbackFactory.class)
public interface RemoteLiquiTransferRecordService {

	@GetMapping("/liquitransferrecord/getByPaifuProjectId/{paifuProjectId}/{assetsId}")
	R<LiquiTransferRecord> getByPaifuProjectIdAndAssetsId(@PathVariable("paifuProjectId") Integer paifuProjectId,
														  @PathVariable("assetsId") Integer assetsId,
														  @RequestHeader(SecurityConstants.FROM) String from);

}
