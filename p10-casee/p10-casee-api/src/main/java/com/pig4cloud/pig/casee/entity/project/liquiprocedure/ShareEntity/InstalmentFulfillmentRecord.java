package com.pig4cloud.pig.casee.entity.project.liquiprocedure.ShareEntity;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 分期履行记录
 */
@Data
public class InstalmentFulfillmentRecord extends CommonalityData implements Serializable {

	/**
	 * 期数
	 */
	private Integer period;

	/**
	 * 分期履行时间
	 */
	@JSONField(format="yyyy-MM-dd")
	private Date installmentTime;

	/**
	 * 分期履行金额
	 */
	private BigDecimal installmentAmount;

	/**
	 * 履行人
	 */
	private Integer fulfillName;
}
