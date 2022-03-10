package com.pig4cloud.pig.casee.entity.project.fundingzxprocedure;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.ShareEntity.PaymentRecord;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * 资金执行资金划扣
 */
@Data
public class FundingZX_ZJZX_ZJZXZJHK_ZJZXZJHK extends CommonalityData implements Serializable {
	/**
	 * 划扣时间
	 */
	@JSONField(format="yyyy-MM-dd")
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
