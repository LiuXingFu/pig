package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 诉前保全
 */
@Data
public class LiQui_SQ_SQBQ extends CommonalityData implements Serializable {

	/**
	 * 诉前保全
	 */
	@ApiModelProperty("诉前保全")
	private LiQui_SQ_SQBQ_SQBQ liQui_SQ_SQBQ_SQBQ = new LiQui_SQ_SQBQ_SQBQ();

}
