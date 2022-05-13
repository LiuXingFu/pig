package com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 价格依据
 */
@Data
public class PaiFu_STCC_JGYJ_JGYJ extends CommonalityData implements Serializable {

	/**
	 * 拍辅费用记录id
	 */
	private Integer paiFuExpenseRecordId;

	/**
	 * 清收费用记录id
	 */
	private Integer liQuiExpenseRecordId;
	/**
	 * 定价日期
	 */
	private LocalDate pricingDate;

	/**
	 * 定价方式(0-议价 1-网络询价 2-询价 3-评估)
	 */
	private Integer pricingManner;

	/**
	 * 定价费用
	 */
	private BigDecimal pricingFee;

	/**
	 * 定价价格
	 */
	private BigDecimal listPrice;

	/**
	 * 附件
	 */
	private String appendixFile;

	/**
	 * 备注
	 */
	private String remark;
}
