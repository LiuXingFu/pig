package com.pig4cloud.pig.casee.entity.paifuentity.detail;

import com.pig4cloud.pig.casee.entity.liquientity.detail.detailentity.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 项目案件财产关联详情
 */
@Data
public class AssetsRePaifuDetail {

	/**
	 *   0: 申请人,1: 案外人,2: 无抵押权人
	 */
	@ApiModelProperty(value = "0: 申请人,1: 案外人,2: 无抵押权人")
	private Integer mortgagee;

	/**
	 * 抵押时间
	 */
	@ApiModelProperty(value = "抵押开始时间")
	private LocalDate mortgageStartTime;

	/**
	 * 抵押时间
	 */
	@ApiModelProperty(value = "抵押结束时间")
	private LocalDate mortgageEndTime;

	/**
	 * 抵押金额
	 */
	@ApiModelProperty(value = "抵押金额")
	private BigDecimal mortgageAmount;

	/**
	 *  拍卖状态：
	 */
	@ApiModelProperty(value = "拍卖状态：")
	private Integer auctionStatus;

	/**
	 * 实体资产查封实体
	 */
	@ApiModelProperty(value = "实体资产查封")
	private AssetsSeizure assetsSeizure;

	/**
	 *  实体财产到款实体类
	 */
	@ApiModelProperty(value = "实体财产到款")
	private AssetsPayment assetsPayment;

	/**
	 * 资产抵债
	 */
	@ApiModelProperty(value = "资产抵债")
	private AssetsAssetCompensate assetsAssetCompensate;

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
