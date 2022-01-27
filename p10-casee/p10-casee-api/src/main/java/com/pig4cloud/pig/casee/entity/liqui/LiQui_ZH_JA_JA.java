package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 结案
 */
@Data
public class LiQui_ZH_JA_JA extends CommonalityData implements Serializable {
	/**
	 * 结案状态
	 */
	@ApiModelProperty(value = "结案状态")
	private Integer caseClosedStatus;

}
