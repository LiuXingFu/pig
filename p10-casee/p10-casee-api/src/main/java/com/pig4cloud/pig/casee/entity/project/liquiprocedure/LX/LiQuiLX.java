package com.pig4cloud.pig.casee.entity.project.liquiprocedure.LX;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 清收
 */
@Data
public class LiQuiLX extends CommonalityData implements Serializable {

	/**
	 * 履行阶段
	 */
	@ApiModelProperty("履行阶段")
	private LiQui_LX liQui_LX = new LiQui_LX();
}
