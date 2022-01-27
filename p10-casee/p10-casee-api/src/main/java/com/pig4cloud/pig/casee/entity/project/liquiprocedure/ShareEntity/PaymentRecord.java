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
	 * 款项类型(0-本金/利息 1-诉讼费 2-保全费 3-评估费 4-网络询价费 5-定向询价费 6-拍辅费 7-公告费 8-代理费 9-其它费用)
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
