package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 财产分配
 */
@Data
public class LiQui_ZX_CCFP extends CommonalityData implements Serializable {

	/**
	 * 财产分配
	 */
	@ApiModelProperty("财产分配")
	private LiQui_ZX_CCFP_CCFP liQui_ZX_CCFP_CCFP = new LiQui_ZX_CCFP_CCFP();

}
