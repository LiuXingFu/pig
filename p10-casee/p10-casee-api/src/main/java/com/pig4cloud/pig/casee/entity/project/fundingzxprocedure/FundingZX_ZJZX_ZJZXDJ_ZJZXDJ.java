package com.pig4cloud.pig.casee.entity.project.fundingzxprocedure;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 资金执行冻结
 */
@Data
public class FundingZX_ZJZX_ZJZXDJ_ZJZXDJ extends CommonalityData implements Serializable {
	/**
	 * 冻结时间
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date freezeTime;

	/**
	 * 冻结到期日
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date freezeExpirationDate;

	/**
	 * 是否首冻(0-否 1-是)
	 */
	private Integer whetherFirstFrozen;

	/**
	 * 冻结金额
	 */
	private Integer frozenAmount;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 附件
	 */
	private String appendixFile;
}
