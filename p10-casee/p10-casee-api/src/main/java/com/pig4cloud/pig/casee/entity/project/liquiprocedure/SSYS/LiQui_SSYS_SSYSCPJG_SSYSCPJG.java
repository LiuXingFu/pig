package com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSYS;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.ShareEntity.InstalmentFulfillmentRecord;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 诉讼阶段一审裁判结果
 */
@Data
public class LiQui_SSYS_SSYSCPJG_SSYSCPJG extends CommonalityData implements Serializable {
	/**
	 * 裁判/调解日期
	 */
	private LocalDate refereeMediationTime;

	/**
	 * 诉讼费用
	 */
	private BigDecimal litigationCosts;

	/**
	 * 文书上传
	 */
	private String writFile;

	/**
	 * 裁判/调解结果（0-驳回 1-确定）
	 */
	private Integer refereeMediationResult;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 上诉期限
	 */
	private Integer appealDeadline;

	/**
	 * 裁判类型(0-判决 1-裁定 2-调解)
	 */
	private Integer refereeType;

	/**
	 * 裁判金额
	 */
	private BigDecimal refereeAmount;

	/**
	 * 本金
	 */
	private BigDecimal principal;

	/**
	 * 利息
	 */
	private BigDecimal interest;

	/**
	 * 确定内容
	 */
	private String determineContent;

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
}
