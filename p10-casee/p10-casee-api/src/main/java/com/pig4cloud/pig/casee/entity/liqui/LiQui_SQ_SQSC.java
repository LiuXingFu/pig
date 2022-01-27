package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 诉前审查
 */
@Data
public class LiQui_SQ_SQSC extends CommonalityData implements Serializable {

	/**
	 * 诉前审查
	 */
	@ApiModelProperty("诉前审查")
	private LiQui_SQ_SQSC_SQSC liQui_SQ_SQSC_SQSC = new LiQui_SQ_SQSC_SQSC();

}
