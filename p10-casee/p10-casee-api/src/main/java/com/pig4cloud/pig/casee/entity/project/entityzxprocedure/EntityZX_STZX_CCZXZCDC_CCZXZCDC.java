package com.pig4cloud.pig.casee.entity.project.entityzxprocedure;

import com.pig4cloud.pig.casee.dto.PaymentRecordAddDTO;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 财产执行资产抵债
 */
@Data
public class EntityZX_STZX_CCZXZCDC_CCZXZCDC extends CommonalityData implements Serializable {

	/**
	 * 抵偿金额
	 */
	private BigDecimal compensationAmount;

	/**
	 * 抵偿日期
	 */
	private LocalDate settlementDate;

	/**
	 * 抵偿人名称
	 */
	private String subjectName;

	/**
	 * 抵偿人id
	 */
	private Integer subjectId;

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

	/**
	 * 抵偿分配记录
	 */
	List<PaymentRecordAddDTO> paymentRecordList;
}
