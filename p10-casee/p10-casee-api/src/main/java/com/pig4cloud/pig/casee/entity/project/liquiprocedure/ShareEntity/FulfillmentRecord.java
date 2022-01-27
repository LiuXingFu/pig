package com.pig4cloud.pig.casee.entity.project.liquiprocedure.ShareEntity;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 履行记录
 */
@Data
public class FulfillmentRecord extends CommonalityData implements Serializable {

	/**
	 * 履行时间
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date fulfillTime;

	/**
	 * 履行金额
	 */
	private BigDecimal fulfillmentAmount;

	/**
	 * 履行人
	 */
	private String performer;




}
