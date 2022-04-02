package com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 到款
 */
@Data
public class PaiFu_STCC_DK_DK extends CommonalityData implements Serializable {
	/**
	 * 到款金额
	 */
	private BigDecimal amountReceived;

	/**
	 * 最终到款时间
	 */
	private LocalDate finalPaymentDate;

	/**
	 * 最终付款人
	 */
	private String finalPayer;

	/**
	 * 拍辅费用
	 */
	private BigDecimal auxiliaryFee;

	/**
	 * 附件
	 */
	private String appendixFile;

	/**
	 * 备注
	 */
	private String remark;
}
