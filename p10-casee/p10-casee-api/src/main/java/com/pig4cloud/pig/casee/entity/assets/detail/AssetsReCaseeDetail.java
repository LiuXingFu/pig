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
	 * 资金财产冻结送达情况
	 */
	@ApiModelProperty(value = "资金财产冻结送达情况")
	private AssetsFreezeServedSituation assetsFreezeServedSituation;

	/**
	 * 资金财产划扣
	 */
	@ApiModelProperty(value = "资金财产划扣")
	private AssetsSnap assetsSnap;

	/**
	 * 资金财产划扣送达情况
	 */
	@ApiModelProperty(value = "资金财产划扣送达情况")
	private AssetsSnapServedSituation assetsSnapServedSituation;




	/**
	 * 实体资产查封实体
	 */
	@ApiModelProperty(value = "实体资产查封实体")
	private AssetsSeizure assetsSeizure;

	/**
	 * 实体资产查封财产送达情况
	 */
	@ApiModelProperty(value = "实体资产查封财产送达情况")
	private AssetsSeizureServedSituation assetsSeizureServedSituation;

	/**
	 * 实体资产商请移送实体类
	 */
	@ApiModelProperty(value = "实体资产商请移送实体类")
	private AssetsPleaseTransfer assetsPleaseTransfer;

	/**
	 * 资产处置移交
	 */
	@ApiModelProperty(value = "资产处置移交")
	private AssetsDispositionTransfer assetsDispositionTransfer;

	/**
	 * 实体资产资产处置移交送达情况
	 */
	@ApiModelProperty(value = "实体资产资产处置移交送达情况")
	private AssetsDispositionTransferServedSituation assetsDispositionTransferServedSituation;

	/**
	 *  实体资产现勘
	 */
	@ApiModelProperty(value = "实体资产现勘")
	private AssetsExploration assetsExploration;
	/**
	 *  实体财产不动产现勘入户
	 */
	@ApiModelProperty(value = "实体财产不动产现勘入户")
	private AssetsRealEstateExplorationHome assetsRealEstateExplorationHome;
	/**
	 *  实体财产价格依据
	 */
	@ApiModelProperty(value = "实体财产价格依据")
	private AssetsPriceBasis assetsPriceBasis;
	/**
	 *  实体财产拍卖公告
	 */
	@ApiModelProperty(value = "实体财产拍卖公告")
	private AssetsAuctionAnnouncement assetsAuctionAnnouncement;
	/**
	 *  实体财产拍卖结果
	 */
	@ApiModelProperty(value = "实体财产拍卖结果")
	private AssetsAuctionResults assetsAuctionResults;

	/**
	 *  实体财产拍卖结果送达情况
	 */
	@ApiModelProperty(value = "实体财产拍卖结果送达情况")
	private AssetsAuctionResultsServedSituation assetsAuctionResultsServedSituation;
	/**
	 *  实体财产到款实体类
	 */
	@ApiModelProperty(value = "实体财产到款实体类")
	private AssetsPayment assetsPayment;
	/**
	 *  实体财产资产抵偿
	 */
	@ApiModelProperty(value = "实体财产资产抵偿")
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
	/**
	 *  实体财产抵偿裁定送达情况
	 */
	@ApiModelProperty(value = "实体财产抵偿裁定送达情况")
	private AssetsCompensationAwardServedSituation assetsCompensationAwardServedSituation;
	/**
	 *  实体财产腾退成功
	 */
	@ApiModelProperty(value = "实体财产腾退成功")
	private AssetsEvacuateSuccessfully assetsEvacuateSuccessfully;

}
