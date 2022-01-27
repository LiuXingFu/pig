package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 诉前催送
 */
@Data
public class LiQui_SQ_SQCS extends CommonalityData implements Serializable {

	/**
	 * 诉前催收
	 */
	@ApiModelProperty("诉前催送")
	private LiQui_SQ_SQCS_SQCS liQui_SQ_SQCS_SQCS = new LiQui_SQ_SQCS_SQCS();

}
