package com.pig4cloud.pig.casee.entity.liquientity.detail.detailentity;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.ShareEntity.InstalmentFulfillmentRecord;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.entity.liquientity.detail.detailentity
 * @ClassNAME: 其他裁判结果
 * @Author: yd
 * @DATE: 2022/3/10
 * @TIME: 9:47
 * @DAY_NAME_SHORT: 周四
 */
@Data
public class OtherRefereeResult extends CommonalityData implements Serializable {
	/**
	 * 裁判/调解日期
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date refereeMediationTime;

	/**
	 * 诉讼费用
	 */
	private BigDecimal litigationCosts;

	/**
	 * 文书
	 */
	private String writFile;

	/**
	 * 裁判/调解结果（0-发回重审 1-维持一审裁判结果 2-改判、撤销或变更内容）
	 */
	private Integer refereeMediationResult;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 生效日期
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date effectiveDate;

	/**
	 * 裁判类型(0-判决 1-裁定 2-调解)
	 */
	private Integer refereeType;

	/**
	 * 生效履行期限
	 */
	private Integer effectivePerformancePeriod;

	/**
	 * 裁判金额
	 */
	private BigDecimal refereeAmount;

	/**
	 * 判决书
	 */
	private String verdictFile;

	/**
	 * 调解书
	 */
	private String mediationFile;

	/**
	 * 分期履行记录
	 */
	private List<InstalmentFulfillmentRecord> instalmentFulfillmentRecordList;

	/**
	 * 改判撤销或变更内容
	 */
	private String revokedContent;
}
