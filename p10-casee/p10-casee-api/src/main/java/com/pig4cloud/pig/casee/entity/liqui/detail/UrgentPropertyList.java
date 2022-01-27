package com.pig4cloud.pig.casee.entity.liqui.detail;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 资产处置 亟待处理财产
 */
@Data
public class UrgentPropertyList {
	/**
	 * 当事人Id 案件人员ID
	 */
	private Integer partyId;

	/**
	 * 当事人名称
	 */
	private String partyName;

	/**
	 * 财产类型
	 */
	private Integer evidenceOfMoney;

	/**
	 * 财产数量
	 */
	private Integer NumberOfProperty;

	/**
	 * 评估价格
	 */
	private BigDecimal evaluatedPrice;

	/**
	 * 处置价格
	 */
	private BigDecimal disposalPrice;

	/**
	 * 待处理财产 劳动报酬
	 */
	private BigDecimal LaborRemuneration;

	/**
	 * 待处理财产 领款凭证
	 */
	private String pendingCertificateOfPaymentUrl;
}
