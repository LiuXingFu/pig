package com.pig4cloud.pig.casee.entity.project.liquiprocedure.SQ;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 清收
 */
@Data
public class LiQuiSQ extends CommonalityData implements Serializable {

	/**
	 * 诉前
	 */
	@ApiModelProperty("诉前")
	private LiQui_SQ liQui_SQ = new LiQui_SQ();
}
