package com.pig4cloud.pig.casee.entity.project.entityzxprocedure;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 财产执行查封
 */
@Data
public class EntityZX_STZX_CCZXCF_CCZXCF extends CommonalityData implements Serializable {
	/**
	 * 查封日期
	 */
	private LocalDate sealUpTime;

	/**
	 * 查封到期日
	 */
	private LocalDate seizureExpiryDate;

	/**
	 * 查封情况(0-首封 1-轮候)
	 */
	private Integer sealUpCondition;

	/**
	 * 首封案号
	 */
	private String firstCaseNumber;

	/**
	 * 首封到期日
	 */
	private LocalDate firstCoverDueDate;

	/**
	 * 首封法院
	 */
	private String firstCourt;

	/**
	 * 首封法官
	 */
	private String firstJudge;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 附件
	 */
	private String appendixFile;
}
