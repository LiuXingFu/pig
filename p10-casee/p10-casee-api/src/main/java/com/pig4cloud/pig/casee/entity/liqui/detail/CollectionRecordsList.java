package com.pig4cloud.pig.casee.entity.liqui.detail;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 催收记录列表
 */
@Data
public class CollectionRecordsList {

	/**
	 * 催收时间
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date collectionTime;

	/**
	 * 被执行人
	 */
	private String executedPersonId;
	/**
	 * 被执行人Name
	 */
	private String executedPersonName;

	/**
	 * 清收工作人员
	 */
	private String collectionStaffId;
	/**
	 * 清收工作人员Name
	 */
	private String collectionStaffName;

	/**
	 * 催收方式 0-外访催收 1-电话催收
	 */
	private Integer collectionMethod;

	/**
	 * 被执行人地址
	 */
	private String executedAddressId;

	/**
	 * 催收结果 0-部分还款
	 */
	private String collectionResult;

	/**
	 * PTP时间
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date PTPTime;

	/**
	 * PTP金额
	 */
	private BigDecimal PTPAmount;

	/**
	 * 更新被执行人联系方式
	 */
	private String renewExecutedPersonPhone;

	/**
	 * 更新被执行人联系地址
	 */
	private String renewExecutedAddressId;

	/**
	 * 催收备注
	 */
	private String collectionRemark;

	/**
	 * 下次跟进时间
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date followUpTime;

}
