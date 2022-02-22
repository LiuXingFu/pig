package com.pig4cloud.pig.casee.entity.project.fundingzxprocedure;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.ShareEntity.PaymentRecord;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 资金执行资金划扣
 */
@Data
public class FundingZX_ZJZX_ZJZXZJHK_ZJZXZJHK extends CommonalityData implements Serializable {
	/**
	 * 划扣时间
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date deductionTime;

	/**
	 * 划扣金额
	 */
	private Integer deductionAmount;

	/**
	 * 附件
	 */
	private String appendixFile;

	/**
	 * 备注
	 */
	private String remark;
}
