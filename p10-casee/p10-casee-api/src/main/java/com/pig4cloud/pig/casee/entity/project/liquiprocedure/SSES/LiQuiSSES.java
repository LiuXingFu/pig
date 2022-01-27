package com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSES;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 清收
 */
@Data
public class LiQuiSSES extends CommonalityData implements Serializable {

	/**
	 * 诉讼二审
	 */
	@ApiModelProperty("诉讼二审")
	private LiQui_SSES liQui_SSES = new LiQui_SSES();

}
