package com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSYS;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 清收
 */
@Data
public class LiQuiSSYS extends CommonalityData implements Serializable {

	/**
	 * 诉讼一审
	 */
	@ApiModelProperty("诉讼一审")
	private LiQui_SSYS liQui_SSYS = new LiQui_SSYS();

}
