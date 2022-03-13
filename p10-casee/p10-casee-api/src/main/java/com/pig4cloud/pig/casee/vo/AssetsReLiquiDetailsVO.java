package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.casee.entity.assets.AssetsReLiqui;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AssetsReLiquiDetailsVO extends AssetsReLiqui {

	/**
	 * 项目详情
	 */
	@ApiModelProperty(value="项目详情")
	private ProjectLiqui projectLiqui;

	/**
	 * 案件详情
	 */
	@ApiModelProperty(value="案件详情")
	private CaseeLiquiPageVO caseeLiqui;

	/**
	 * 财产详情
	 */
	@ApiModelProperty(value="财产详情")
	private AssetsDeailsVO assets;

}
