package com.pig4cloud.pig.casee.entity.liqui.detail;

import com.pig4cloud.pig.admin.api.entity.FileAdder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 全部还款
 */
@Data
public class FullRepayment {

	/**
	 * 还款金额
	 */
	@ApiModelProperty("还款金额")
	private BigDecimal repaymentAmount_QBHK;

	/**
	 * 银行凭证
	 */
	@ApiModelProperty("银行凭证")
	private List<FileAdder> bankDocumentUrl_QBHK;

	/**
	 * 结清证明
	 */
	@ApiModelProperty("结清证明")
	private List<FileAdder> settleProveUrl_QBHK;

	/**
	 * 还款记录列表
	 */
	@ApiModelProperty("还款记录列表")
	private List<PaymentHistoryList> paymentHistoryList_QBHK;

}
