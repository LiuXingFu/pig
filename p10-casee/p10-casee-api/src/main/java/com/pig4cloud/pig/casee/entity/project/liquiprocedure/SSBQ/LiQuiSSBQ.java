package com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSBQ;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 清收
 */
@Data
public class LiQuiSSBQ extends CommonalityData implements Serializable{

	/**
	 * 诉讼保全
	 */
	@ApiModelProperty("诉讼保全")
	private LiQui_SSBQ liQui_SSBQ = new LiQui_SSBQ();

}
