package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.admin.api.entity.FileAdder;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import com.pig4cloud.pig.casee.entity.liqui.detail.AccountInformationList;
import com.pig4cloud.pig.casee.entity.liqui.detail.AssetDisposalAssociateCaseList;
import com.pig4cloud.pig.casee.entity.liqui.detail.IncomeInformationList;
import com.pig4cloud.pig.casee.entity.liqui.detail.UrgentPropertyList;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 资产处置
 */
@Data
public class LiQui_ZX_ZCCZ_ZCCZ extends CommonalityData implements Serializable {

	//资产处置
	/**
	 * 资产处置状态(0-划扣 1-提取 2-变现提存)
	 */
	@ApiModelProperty("资产处置状态(0-划扣 1-提取 2-变现提存)")
	private Integer assetsDisposalStatus;

	//划扣
	/**
	 * 存款账户信息
	 */
	@ApiModelProperty("存款账户信息")
	private List<AccountInformationList> accountInformationList;

	/**
	 * 账户所有人身份证号 被执行人身份证
	 */
	@ApiModelProperty("账户所有人身份证号")
	private String accountOwnerIDCard;

	/**
	 * 账户所有人名称
	 */
	@ApiModelProperty("账户所有人名称")
	private String accountOwnerName;

	//  线上扣划
	/**
	 * 冻结账户
	 */
	@ApiModelProperty("冻结账户")
	private String frozenAccount;

	/**
	 * 冻结时间
	 */
	@ApiModelProperty("冻结时间")
	private Date freezingTime;

	/**
	 * 线上扣划 扣划时间
	 */
	@ApiModelProperty("线上扣划 扣划时间")
	private Date onLineDeductTheTime;

	/**
	 * 线上扣划 扣划法官
	 */
	@ApiModelProperty("线上扣划 扣划法官")
	private String onLineDeductTheJudge;

	/**
	 * 线上扣划 扣划金额
	 */
	@ApiModelProperty("线上扣划 扣划金额")
	private BigDecimal onLineAgreement;

	/**
	 * 线上扣划 领款时间
	 */
	@ApiModelProperty("线上扣划 领款时间")
	private BigDecimal onLineDrawMoneyTime;

	//  线下扣划
	/**
	 * 线下扣划 扣划时间
	 */
	@ApiModelProperty("线下扣划 扣划时间")
	private Date offlineDeductTheTime;

	/**
	 * 线下扣划 扣划法官
	 */
	@ApiModelProperty("线下扣划 扣划法官")
	private String offlineDeductTheJudge;

	/**
	 * 线下扣划 扣划金额
	 */
	@ApiModelProperty("线下扣划 扣划金额")
	private BigDecimal offlineAgreement;

	/**
	 * 线下扣划 领款时间
	 */
	@ApiModelProperty("线下扣划 领款时间")
	private Date offlineDrawMoneyTime;

	//提取
	/**
	 * 被执行人收入信息
	 */
	@ApiModelProperty("被执行人收入信息")
	private List<IncomeInformationList> incomeInformationList;

	/**
	 * 住房公积金附件
	 */
	@ApiModelProperty("住房公积金附件")
	private List<FileAdder> housingFundUrl;

	/**
	 * 协助执行通知附件
	 */
	@ApiModelProperty("协助执行通知附件")
	private List<FileAdder> assistNoticeUrl;

	//变现提存
	/**
	 * 关联案件
	 */
	@ApiModelProperty("关联案件")
	private List<AssetDisposalAssociateCaseList> assetDisposalAssociateCaseList;

	/**
	 * 亟待处理财产
	 */
	@ApiModelProperty("亟待处理财产")
	private List<UrgentPropertyList> urgentPropertyList;

	//通知履行
	/**
	 * 履行通知表附件
	 */
	@ApiModelProperty("履行通知表附件")
	private List<FileAdder> performanceNoticeFormUrl;

}
