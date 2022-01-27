package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.admin.api.entity.FileAdder;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import com.pig4cloud.pig.casee.entity.liqui.detail.RelatedCasesList;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 资产调查
 */
@Data
public class LiQui_ZX_ZCDC_ZCDC extends CommonalityData implements Serializable {
	//常规调查
	/**
	 * 当事人姓名
	 */
	@ApiModelProperty("当事人姓名")
	private String partyName;

	/**
	 * 当事人联系方式
	 */
	@ApiModelProperty("当事人联系方式")
	private String partyPhone;

	/**
	 * 当事人身份证号
	 */
	@ApiModelProperty("当事人身份证号")
	private String partyIDCard;

	/**
	 * 银行存款
	 */
	@ApiModelProperty("银行存款")
	private BigDecimal bankSavings;

	/**
	 * 公积金账户
	 */
	@ApiModelProperty("公积金账户")
	private String providentFundAccount;

	/**
	 * 证券账户
	 */
	@ApiModelProperty("证券账户")
	private String securitiesAccount;

	/**
	 * 财产性权益附件
	 */
	@ApiModelProperty("财产性权益附件")
	private List<FileAdder> propertyRightsUrl;

	/**
	 * 是否有可处置物(标的物) 0-无 1-有
	 */
	@ApiModelProperty("是否有可处置物(标的物) 0-无 1-有")
	private Integer canDisposableThings;

	/**
	 * 不动产资料附件
	 */
	@ApiModelProperty("不动产资料附件")
	private List<FileAdder> realEstateUrl;

	/**
	 * 动产资料附件
	 */
	@ApiModelProperty("动产资料附件")
	private List<FileAdder> movablePropertyUrl;

	/**
	 * 是否被查封 0-否 1-是
	 */
	@ApiModelProperty("是否被查封 0-否 1-是")
	private Integer canSealUp;

	/**
	 * 首封法院是否已处置 0-否 1-是
	 */
	@ApiModelProperty("首封法院是否已处置 0-否 1-是")
	private Integer canCourtDisposition;

	//特殊调查
	/**
	 * 关联案件
	 */
	@ApiModelProperty("关联案件")
	private List<RelatedCasesList> relatedCasesList;

	/**
	 * 出入境身份说明附件
	 */
	@ApiModelProperty("出入境身份说明附件")
	private List<FileAdder> entryAndExitInstructionsUrl;

	/**
	 * 水电气登记附件
	 */
	@ApiModelProperty("水电气登记附件")
	private List<FileAdder> utilitiesRegisterUrl;

	/**
	 * 婚姻配偶查询附件
	 */
	@ApiModelProperty("婚姻配偶查询附件")
	private List<FileAdder> marriageSpouseInquiryUrl;

	/**
	 * 行程查询附件
	 */
	@ApiModelProperty("行程查询附件")
	private List<FileAdder> tineraryQueryUrl;

	/**
	 * 临柜查询附件
	 */
	@ApiModelProperty("临柜查询附件")
	private List<FileAdder> queryAtTheCounterUrl;

	/**
	 * 实地走访附件
	 */
	@ApiModelProperty("实地走访附件")
	private List<FileAdder> fieldVisit;

	/**
	 * 外地实地查询附件
	 */
	@ApiModelProperty("外地实地查询附件")
	private List<FileAdder> fieldInquiriesUrl;

	/**
	 * 其它附件
	 */
	@ApiModelProperty("其它附件")
	private List<FileAdder> otherUrl;

	//悬赏调查
	/**
	 * 悬赏名称
	 */
	@ApiModelProperty("悬赏名称")
	private String rewardName;

	/**
	 * 悬赏类别(0-财产信息 1-当事人行踪)
	 */
	@ApiModelProperty("悬赏类别(0-财产信息 1-当事人行踪)")
	private Integer rewardCategory;

	/**
	 * 姓名
	 */
	@ApiModelProperty("姓名")
	private String name;

	/**
	 * 年龄
	 */
	@ApiModelProperty("年龄")
	private Integer age;

	/**
	 * 身份证号码
	 */
	@ApiModelProperty("份证号码")
	private String identificationNumber;

	/**
	 * 性别
	 */
	@ApiModelProperty("性别")
	private Integer sex;

	/**
	 * 户籍所在地
	 */
	@ApiModelProperty("户籍所在地")
	private String Domicile;

	/**
	 * 执行案号
	 */
	@ApiModelProperty("执行案号")
	private String executionCaseNumber;

	/**
	 * 承办法院
	 */
	@ApiModelProperty("承办法院")
	private String court;

	/**
	 * 案件类型(0-民事 1-刑事)
	 */
	@ApiModelProperty("案件类型(0-民事 1-刑事)")
	private String caseType;

	/**
	 * 涉案标的额
	 */
	@ApiModelProperty("涉案标的额")
	private BigDecimal TargetAmount;

	/**
	 * 案件情况说明
	 */
	@ApiModelProperty("案件情况说明")
	private String caseIllustrate;

	/**
	 * 悬赏内容
	 */
	@ApiModelProperty("悬赏内容")
	private String rewardContent;

}
