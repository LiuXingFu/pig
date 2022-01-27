package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 清收-诉讼
 */
@Data
public class LiQui_SS extends CommonalityData implements Serializable {

	/**
	 * 法律⽂书
	 */
	@ApiModelProperty("法律⽂书")
	private LiQui_SS_FLWS liQui_SS_FLWS = new LiQui_SS_FLWS();

	/**
	 * 开庭庭审
	 */
	@ApiModelProperty("开庭庭审")
	private LiQui_SS_KTTS liQui_SS_KTTS = new LiQui_SS_KTTS();

	/**
	 * 结案
	 */
	@ApiModelProperty("结案")
	private LiQui_SS_JA liQui_SS_JA = new LiQui_SS_JA();

}
