package com.pig4cloud.pig.casee.feign.fallback;

import com.pig4cloud.pig.casee.entity.LiquiTransferRecord;
import com.pig4cloud.pig.casee.feign.RemoteLiquiTransferRecordService;
import com.pig4cloud.pig.common.core.util.R;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteLiquiTransferRecordServiceFallbackImpl implements RemoteLiquiTransferRecordService {

	@Setter
	private Throwable cause;

	@Override
	public R<LiquiTransferRecord> getByPaifuProjectIdAndAssetsId(Integer paifuProjectId, Integer assetsId, String from) {
		log.error("根据拍辅项目id查询清收移交记录信息失败！", cause);
		return null;
	}
}
