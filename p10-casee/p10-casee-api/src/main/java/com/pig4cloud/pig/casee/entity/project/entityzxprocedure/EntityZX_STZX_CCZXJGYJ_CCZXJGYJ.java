package com.pig4cloud.pig.casee.entity.project.entityzxprocedure;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 财产执行价格依据
 */
@Data
public class EntityZX_STZX_CCZXJGYJ_CCZXJGYJ extends CommonalityData implements Serializable {

	/**
	 * 费用记录id
	 */
	private Integer expenseRecordId;

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
