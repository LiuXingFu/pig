package com.pig4cloud.pig.casee.entity.project.entityzxprocedure;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 财产执行到款
 */
@Data
public class EntityZX_STZX_CCZXDK_CCZXDK extends CommonalityData implements Serializable {
	/**
	 * 清收回款记录id
	 */
	private Integer paymentRecordId;

	/**
	 * 清收拍辅费用产生记录id
	 */
	private Integer expenseRecordId;

	/**
	 * 到款金额
	 */
	private BigDecimal amountReceived;

	/**
	 * 最终到款时间
	 */
	private LocalDate finalPaymentDate;

	/**
	 * 最终付款人
	 */
	private String finalPayer;

	/**
	 * 付款人电话
	 */
	private String phone;

	/**
	 * 付款人身份证
	 */
	private String identityCard;

	/**
	 * 拍辅费用
	 */
	private BigDecimal auxiliaryFee;

	/**
	 * 附件
	 */
	private String appendixFile;

	/**
	 * 备注
	 */
	private String remark;

}
