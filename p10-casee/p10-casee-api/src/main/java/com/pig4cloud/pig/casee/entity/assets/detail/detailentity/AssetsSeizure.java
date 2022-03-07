package com.pig4cloud.pig.casee.entity.assets.detail.detailentity;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体资产查封实体
 */
@Data
public class AssetsSeizure extends CommonalityData implements Serializable {

	/**
	 * 查封日期
	 */
	@JSONField(format="yyyy-MM-dd")
	private Date sealUpTime;

	/**
	 * 查封到期日
	 */
	@JSONField(format="yyyy-MM-dd")
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
	@JSONField(format="yyyy-MM-dd")
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

	/**
	 * 附件
	 */
	private String appendixFile;

}
