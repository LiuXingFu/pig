package com.pig4cloud.pig.casee.entity.assets.detail;

import com.pig4cloud.pig.casee.entity.assets.detail.detailentity.*;
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
	 * 资金财产划扣
	 */
	@ApiModelProperty(value = "资金财产划扣")
	private AssetsSnap assetsSnap;



	/**
	 * 实体资产查封实体
	 */
	@ApiModelProperty(value = "实体资产查封实体")
	private AssetsSeizure assetsSeizure;

	/**
	 *  实体财产到款实体类
	 */
	@ApiModelProperty(value = "实体财产到款实体类")
	private AssetsPayment assetsPayment;
	/**
	 *  实体财产成交裁定
	 */
	@ApiModelProperty(value = "实体财产成交裁定")
	private AssetsCompletionRuling assetsCompletionRuling;

	/**
	 *  实体财产抵偿裁定
	 */
	@ApiModelProperty(value = "实体财产抵偿裁定")
	private AssetsCompensationAward assetsCompensationAward;

}
