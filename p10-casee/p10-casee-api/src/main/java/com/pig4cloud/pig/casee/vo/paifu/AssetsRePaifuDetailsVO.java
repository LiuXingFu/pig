package com.pig4cloud.pig.casee.vo.paifu;

import com.pig4cloud.pig.casee.entity.Casee;
import com.pig4cloud.pig.casee.entity.liquientity.AssetsReLiqui;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.paifuentity.AssetsRePaifu;
import com.pig4cloud.pig.casee.entity.paifuentity.ProjectPaifu;
import com.pig4cloud.pig.casee.vo.AssetsDeailsVO;
import com.pig4cloud.pig.casee.vo.CaseeLiquiPageVO;
import com.pig4cloud.pig.casee.vo.CaseeVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AssetsRePaifuDetailsVO extends AssetsRePaifu {

	/**
	 * 程序id
	 */
	private Integer assetsTargetId;

	/**
	 * 项目详情
	 */
	@ApiModelProperty(value="项目详情")
	private ProjectPaifu projectPaifu;

	/**
	 * 案件详情
	 */
	@ApiModelProperty(value="案件详情")
	private CaseeVO casee;

	/**
	 * 财产详情
	 */
	@ApiModelProperty(value="财产详情")
	private AssetsDeailsVO assets;

}
