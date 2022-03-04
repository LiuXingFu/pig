package com.pig4cloud.pig.casee.entity.assets.detail;

import com.pig4cloud.pig.casee.entity.assets.detail.detailentity.AssetsFreeze;
import com.pig4cloud.pig.casee.entity.assets.detail.detailentity.AssetsSeizure;
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

	/**
	 * 资金资产冻结实体
	 */
	@ApiModelProperty(value = "资金资产冻结实体")
	private AssetsFreeze assetsFreeze;

	/**
	 * 实体资产查封实体
	 */
	@ApiModelProperty(value = "实体资产查封实体")
	private AssetsSeizure assetsSeizure;
}
