package com.pig4cloud.pig.casee.entity.project.entityzxprocedure;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 财产执行资产抵债
 */
@Data
public class EntityZX_STZX_CCZXZCDZ_CCZXZCDZ extends CommonalityData implements Serializable {

	/**
	 * 抵偿金额
	 */
	private Double compensationAmount;

	/**
	 * 抵偿日期
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date settlementDate;

	/**
	 * 抵偿人
	 */
	private String indemnifier;

	/**
	 * 拍辅费用
	 */
	private Double AuxiliaryFee;

	/**
	 * 附件
	 */
	private String appendixFile;

	/**
	 * 备注
	 */
	private String remark;
}
