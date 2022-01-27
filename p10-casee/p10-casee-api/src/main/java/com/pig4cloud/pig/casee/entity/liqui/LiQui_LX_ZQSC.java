package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 执前审查
 */
@Data
public class LiQui_LX_ZQSC extends CommonalityData implements Serializable {

	/**
	 * 执前审查
	 */
	@ApiModelProperty("执前审查")
	private LiQui_LX_ZQSC_ZQSC liQui_LX_ZQSC_ZQSC = new LiQui_LX_ZQSC_ZQSC();

}
