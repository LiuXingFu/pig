package com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSQT;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 清收
 */
@Data
public class LiQuiSSQT extends CommonalityData implements Serializable{

	/**
	 * 诉讼其他
	 */
	@ApiModelProperty("诉讼其他")
	private LiQui_SSQT liQui_SSQT = new LiQui_SSQT();

}
