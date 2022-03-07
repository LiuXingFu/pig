package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.casee.entity.PaymentRecord;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRecordCourtPaymentVO extends PaymentRecord {
	/**
	 * 法院到款总金额
	 */
	private BigDecimal courtPayment;
}
