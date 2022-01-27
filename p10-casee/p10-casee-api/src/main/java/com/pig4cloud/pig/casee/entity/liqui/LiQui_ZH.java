package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 清收-执后
 */
@Data
public class LiQui_ZH extends CommonalityData implements Serializable {

	/**
	 * 抵债务处置
	 */
	@ApiModelProperty("抵债务处置")
	private LiQui_ZH_DZWCZ liQui_ZH_DZWCZ = new LiQui_ZH_DZWCZ();

	/**
	 * 结案
	 */
	@ApiModelProperty("结案")
	private LiQui_ZH_JA liQui_ZH_JA = new LiQui_ZH_JA();

}
