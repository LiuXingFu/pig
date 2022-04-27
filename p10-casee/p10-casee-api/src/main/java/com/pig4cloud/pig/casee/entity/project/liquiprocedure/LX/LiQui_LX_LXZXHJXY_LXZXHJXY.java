package com.pig4cloud.pig.casee.entity.project.liquiprocedure.LX;

import com.pig4cloud.pig.casee.entity.project.liquiprocedure.ShareEntity.FulfillmentRecord;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.ShareEntity.InstalmentFulfillmentRecord;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 履行阶段执行和解协议
 */
@Data
public class LiQui_LX_LXZXHJXY_LXZXHJXY {

	/**
	 * 和解时间
	 */
	private LocalDate settlementTime;

	/**
	 * 和解方式(0-不分期 1-分期)
	 */
	private Integer reconciliation;

	/**
	 * 和解总金额
	 */
	private BigDecimal totalSettlementAmount;

	/**
	 * 待履行记录
	 */
	private List<FulfillmentRecord> fulfillmentRecordList;

	/**
	 * 分期履行记录
	 */
	private List<InstalmentFulfillmentRecord> instalmentFulfillmentRecordList;

}
