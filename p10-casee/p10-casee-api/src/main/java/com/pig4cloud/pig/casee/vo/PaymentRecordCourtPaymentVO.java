package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.casee.entity.PaymentRecord;
import com.pig4cloud.pig.casee.vo.paifu.AssetsRePaifuDetailVO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PaymentRecordCourtPaymentVO extends PaymentRecord {
	/**
	 * 法院到款总金额
	 */
	private BigDecimal courtPayment;

	/**
	 * 财产关联集合
	 */
	List<AssetsRePaifuDetailVO> assetsReList;
}
