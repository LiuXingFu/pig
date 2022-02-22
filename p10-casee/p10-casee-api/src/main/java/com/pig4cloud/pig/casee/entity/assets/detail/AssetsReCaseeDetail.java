package com.pig4cloud.pig.casee.entity.assets.detail;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 项目案件财产关联详情
 */
@Data
public class AssetsReCaseeDetail {

	/**
	 * 抵押权人
	 */
	@ApiModelProperty(value = "抵押权人")
	private Integer mortgagee;
}
