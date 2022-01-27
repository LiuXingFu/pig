package com.pig4cloud.pig.casee.entity.project.liquiprocedure.ZXZH;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 清收
 */
@Data
public class LiQuiZXZH extends CommonalityData implements Serializable {

	/**
	 * 执行执恢
	 */
	@ApiModelProperty("执行执恢")
	private LiQui_ZXZH liQui_ZXZH = new LiQui_ZXZH();

}
