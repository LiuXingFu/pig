package com.pig4cloud.pig.casee.entity.project.liquiprocedure.ZH;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 清收
 */
@Data
public class LiQui extends CommonalityData implements Serializable {

	/**
	 * 执后
	 */
	@ApiModelProperty("执后")
	private LiQui_ZH liQui_ZH = new LiQui_ZH();

}
