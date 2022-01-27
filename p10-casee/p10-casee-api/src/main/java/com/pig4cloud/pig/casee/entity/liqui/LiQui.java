package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 清收
 */
@Data
public class LiQui extends CommonalityData implements Serializable {

	/**
	 * 诉前
	 */
	@ApiModelProperty("诉前")
	private LiQui_SQ liQui_SQ = new LiQui_SQ();

	/**
	 * 诉讼
	 */
	@ApiModelProperty("诉讼")
	private LiQui_SS liQui_SS = new LiQui_SS();

	/**
	 * 履⾏
	 */
	@ApiModelProperty("履⾏")
	private LiQui_LX liQui_LX = new LiQui_LX();

	/**
	 * 执⾏
	 */
	@ApiModelProperty("执⾏")
	private LiQui_ZX liQui_ZX = new LiQui_ZX();

	/**
	 * 执后
	 */
	@ApiModelProperty("执后")
	private LiQui_ZH liQui_ZH = new LiQui_ZH();
}
