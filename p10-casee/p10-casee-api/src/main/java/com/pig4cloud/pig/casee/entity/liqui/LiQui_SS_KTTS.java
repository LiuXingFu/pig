package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 开庭庭审
 */
@Data
public class LiQui_SS_KTTS extends CommonalityData implements Serializable {

	/**
	 * 开庭庭审
	 */
	@ApiModelProperty("开庭庭审")
	private LiQui_SS_KTTS_KTTS liQui_SS_KTTS_KTTS = new LiQui_SS_KTTS_KTTS();

}
