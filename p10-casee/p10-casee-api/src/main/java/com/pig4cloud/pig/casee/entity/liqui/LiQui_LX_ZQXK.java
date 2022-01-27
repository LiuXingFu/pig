package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 执前现勘
 */
@Data
public class LiQui_LX_ZQXK extends CommonalityData implements Serializable {

	/**
	 * 执前现勘
	 */
	@ApiModelProperty("执前现勘")
	private LiQui_LX_ZQXK_ZQXK liQui_LX_ZQXK_ZQXK = new LiQui_LX_ZQXK_ZQXK();

}
