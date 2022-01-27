package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 抵债务处置
 */
@Data
public class LiQui_ZH_DZWCZ extends CommonalityData implements Serializable {

	/**
	 * 抵债务处置
	 */
	@ApiModelProperty("抵债务处置")
	private LiQui_ZH_DZWCZ_DZWCZ liQui_ZH_DZWCZ_DZWCZ = new LiQui_ZH_DZWCZ_DZWCZ();

}
