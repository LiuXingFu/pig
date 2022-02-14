package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.casee.entity.Behavior;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.vo
 * @ClassNAME: BehaviorOrCaseeVO
 * @Author: yd
 * @DATE: 2022/2/14
 * @TIME: 16:45
 * @DAY_NAME_SHORT: 周一
 */
@Data
public class BehaviorOrCaseeVO extends Behavior {

	/**
	 * 案号
	 */
	@ApiModelProperty(value="案号")
	private String caseeNumber;

}
