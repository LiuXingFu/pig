package com.pig4cloud.pig.casee.entity.project.fundingzxprocedure;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 资金执行资金划扣
 */
@Data
public class FundingZX_ZJZX_ZJZXZJHK_ZJZXZJHK extends CommonalityData implements Serializable {

	/**
	 * 回款记录id
	 */
	private Integer paymentRecordId;

	/**
	 * 划扣时间
	 */
	private LocalDate deductionTime;

	/**
	 * 划扣金额
	 */
	private BigDecimal deductionAmount;

	/**
	 * 附件
	 */
	private String appendixFile;

	/**
	 * 备注
	 */
	private String remark;
}
