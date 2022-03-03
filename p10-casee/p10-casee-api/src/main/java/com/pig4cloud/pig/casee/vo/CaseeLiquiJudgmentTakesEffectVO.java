package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.casee.entity.liquientity.CaseeLiqui;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CaseeLiquiJudgmentTakesEffectVO extends CaseeLiqui {

	/**
	 * 裁判生效日期
	 */
	@ApiModelProperty(value="裁判生效日期")
	private String effectiveDate;


}
