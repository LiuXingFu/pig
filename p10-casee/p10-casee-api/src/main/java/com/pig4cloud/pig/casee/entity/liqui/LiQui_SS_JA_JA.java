package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 结案
 */
@Data
public class LiQui_SS_JA_JA extends CommonalityData implements Serializable {

	/**
	 * 确认终结
	 */
	@ApiModelProperty("确认终结")
	private Integer confirmTheEnd;

}
