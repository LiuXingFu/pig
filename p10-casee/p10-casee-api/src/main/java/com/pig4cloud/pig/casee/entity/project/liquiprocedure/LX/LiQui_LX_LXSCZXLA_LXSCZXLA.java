package com.pig4cloud.pig.casee.entity.project.liquiprocedure.LX;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 履行阶段首次执行立案
 */
@Data
public class LiQui_LX_LXSCZXLA_LXSCZXLA extends CommonalityData implements Serializable {
	/**
	 * 立案时间
	 */
	private LocalDate filingTime;

	/**
	 * 首执案号
	 */
	private String caseeNumber;

	/**
	 * 执行法院
	 */
	private String courtName;

	/**
	 * 执行法官
	 */
	private String judgeName;

	/**
	 * 执行金额
	 */
	private BigDecimal caseeAmount;

	/**
	 * 债务人
	 */
	private List<String> executedName;

	/**
	 * 执行费用
	 */
	private BigDecimal judicialExpenses;

	/**
	 * 办理人
	 */
	private String userNickName;

	/**
	 * 备注
	 */
	private String remark;
}
