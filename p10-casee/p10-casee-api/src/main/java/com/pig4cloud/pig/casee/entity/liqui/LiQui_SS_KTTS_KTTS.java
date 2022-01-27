package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.admin.api.entity.FileAdder;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 开庭庭审
 */
@Data
public class LiQui_SS_KTTS_KTTS extends CommonalityData implements Serializable {

	/**
	 * 诉讼法院
	 */
	@ApiModelProperty("诉讼法院")
	private String litigationCourtId;

	/**
	 * 诉讼案号
	 */
	@ApiModelProperty("诉讼案号")
	private String litigationNumber;

	/**
	 * 庭审申请结果 0-受理 1-不予受理
	 */
	@ApiModelProperty("庭审申请结果 0-受理 1-不予受理")
	private Integer trialApplicationResult;

	/**
	 * 受理结果 0-审理 1-撤诉 2-驳回起诉
	 */
	@ApiModelProperty("受理结果 0-审理 1-撤诉 2-驳回起诉")
	private Integer acceptanceResult;

	/**
	 * 审理结果 0-庭前准备 1-回避 2-异常
	 */
	@ApiModelProperty("审理结果 0-庭前准备 1-回避 2-异常")
	private Integer trialResult;

	/**
	 * 庭审准备
	 */
	@ApiModelProperty("庭审准备")
	private List<FileAdder> trialPreparationUrl;

	/**
	 * 法庭调查
	 */
	@ApiModelProperty("法庭调查")
	private List<FileAdder> courtInvestigationUrl;

	/**
	 * 辩论
	 */
	@ApiModelProperty("辩论")
	private List<FileAdder> debateUrl;

	/**
	 * 诉讼财产保全 (LitigationPropertyPreservation)
	 */
	@ApiModelProperty("诉讼财产保全")
	private List<FileAdder> LPropertyPreservationUrl;

	/**
	 * 合议庭评审
	 */
	@ApiModelProperty("合议庭评审")
	private List<FileAdder> collegiatePanelReviewUrl;

	/**
	 * 庭审宣告(0-缺席判决 1-诉讼调解)
	 */
	@ApiModelProperty("庭审宣告")
	private List<FileAdder> courtAnnouncementUrl;

	/**
	 * 判决利息
	 */
	@ApiModelProperty("判决利息")
	private BigDecimal judgmentInterest;

	/**
	 * 判决金额
	 */
	@ApiModelProperty("判决金额")
	private BigDecimal judgmentAmount;

	/**
	 * 不予受理裁定书
	 */
	@ApiModelProperty("不予受理裁定书")
	private List<FileAdder> inadmissibilityRulingUrl;

	/**
	 * 民事撤诉申请书
	 */
	@ApiModelProperty("民事撤诉申请书")
	private List<FileAdder> civilDismissalApplicationUrl;

	/**
	 * 驳回起诉书
	 */
	@ApiModelProperty("驳回起诉书")
	private List<FileAdder> dismissTheIndictmentUrl;

	/**
	 * 回避申请书
	 */
	@ApiModelProperty("回避申请书")
	private List<FileAdder> avoidApplicationUrl;

	/**
	 * 延期审理申请书
	 */
	@ApiModelProperty("延期审理申请书")
	private List<FileAdder> postponementApplicationUrl;

	/**
	 * 诉讼应收费用
	 */
	@ApiModelProperty("诉讼应收费用")
	private BigDecimal litigationFees;

	/**
	 * 当事人地址确认书
	 */
	@ApiModelProperty("当事人地址确认书")
	private List<FileAdder> partyAddressConfirmationUrl;

	/**
	 * 备注
	 */
	@ApiModelProperty("备注")
	private String remark;

}
