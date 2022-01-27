package com.pig4cloud.pig.casee.entity.liqui.detail;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 资产处置 关联案件列表
 */
@Data
public class AssetDisposalAssociateCaseList {
	/**
	 * 当事人身份证 被执行人身份证
	 */
	private String partyIDCard;
	/**
	 * 当事人名称
	 */
	private String partyName;
	/**
	 * 执行案号
	 */
	private String executionCaseNumber;
	/**
	 * 承办法官
	 */
	private String judge;
	/**
	 * 提取金额
	 */
	private BigDecimal amountDrawn;
	/**
	 * 提取日期
	 */
	private Date extractDate;
	/**
	 * 案件领款凭证
	 */
	private String caseCertificateOfPaymentUrl;
}
