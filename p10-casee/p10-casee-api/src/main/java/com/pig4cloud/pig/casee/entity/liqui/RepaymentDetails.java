package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import com.pig4cloud.pig.casee.entity.liqui.detail.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 还款详情
 */
@Data
public class RepaymentDetails extends CommonalityData implements Serializable {

	/**
	 * 还款状态
	 */
	@ApiModelProperty("还款状态")
	private Integer repaymentStatus;

	/**
	 * 全部还款
	 */
	private FullRepayment fullRepayment;

	/**
	 * 部分还款未签署和解协议
	 */
	private PartialRepaymentNSAS partialRepaymentNSAS;

	/**
	 * 部分还款已签署和解协议
	 */
	private PartialRepaymentSASA partialRepaymentSASA;

	/**
	 * 联系方式错误
	 */
	private WrongContact wrongContact;

	/**
	 * 拒绝还款
	 */
	private RefuseToRepay refuseToRepay;

	/**
	 * 其他
	 */
	private Other other;

}
