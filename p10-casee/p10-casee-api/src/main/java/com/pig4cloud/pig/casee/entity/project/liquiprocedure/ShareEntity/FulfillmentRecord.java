package com.pig4cloud.pig.casee.entity.project.liquiprocedure.ShareEntity;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 履行记录
 */
@Data
public class FulfillmentRecord extends CommonalityData implements Serializable {

	/**
	 * 履行时间
	 */
	private LocalDate fulfillTime;

	/**
	 * 履行金额
	 */
	private BigDecimal fulfillmentAmount;

	/**
	 * 履行人
	 */
	private String performer;




}
