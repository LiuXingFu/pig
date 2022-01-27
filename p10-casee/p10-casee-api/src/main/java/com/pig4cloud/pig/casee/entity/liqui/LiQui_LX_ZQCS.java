package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 执前催收
 */
@Data
public class LiQui_LX_ZQCS extends CommonalityData implements Serializable {

	/**
	 * 执前催收
	 */
	@ApiModelProperty("执前催收")
	private LiQui_LX_ZQCS_ZQCS liQui_LX_ZQCS_ZQCS = new LiQui_LX_ZQCS_ZQCS();

}
