package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 结案
 */
@Data
public class LiQui_ZX_JA_JA extends CommonalityData implements Serializable {
	/**
	 * 执行应收费用
	 */
	@ApiModelProperty("执行应收费用")
	private Double executeCharges;

	/**
	 * 执行实收费用
	 */
	@ApiModelProperty("执行实收费用")
	private Double executeActualFees;

	/**
	 * 拍辅应收费用
	 */
	@ApiModelProperty("拍辅应收费用")
	private Double takesukeCharges;

	/**
	 * 拍辅实收费用
	 */
	@ApiModelProperty("拍辅实收费用")
	private Double takesukeActualFees;

	/**
	 * 诉讼应收费用
	 */
	@ApiModelProperty("诉讼应收费用")
	private Double lawsuitCharges;

	/**
	 * 诉讼实收费用
	 */
	@ApiModelProperty("诉讼实收费用")
	private Double lawsuitActualFees;

}
