package com.pig4cloud.pig.casee.entity.project.entityzxprocedure;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 财产执行查封
 */
@Data
public class EntityZX_STZX_CCZXCF_CCZXCF extends CommonalityData implements Serializable {
	/**
	 * 查封日期
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date sealUpTime;

	/**
	 * 查封到期日
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date seizureExpiryDate;

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
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date firstCoverDueDate;

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
}
