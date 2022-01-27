package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 结案
 */
@Data
public class LiQui_ZX_JA extends CommonalityData implements Serializable {

	/**
	 * 结案
	 */
	@ApiModelProperty("结案")
	private LiQui_ZX_JA_JA liQui_ZX_JA_JA = new LiQui_ZX_JA_JA();

}
