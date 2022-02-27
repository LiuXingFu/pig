package com.pig4cloud.pig.casee.entity.project.liquiprocedure.ShareEntity;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 款项记录
 */
@Data
public class PaymentRecord extends CommonalityData implements Serializable {

	/**
	 * 款项类型(100-本金/利息 200-保全费 210-一审诉讼费 220-二审诉讼费 230-首次执行费 240-定价费 300-拍辅费 310-代理费 400-其它费用)
	 */
	private Integer paymentType;

	/**
	 * 到款金额
	 */
	private BigDecimal arrivalAmount;

	/**
	 * 债务人
	 */
	private String executedName;




}
