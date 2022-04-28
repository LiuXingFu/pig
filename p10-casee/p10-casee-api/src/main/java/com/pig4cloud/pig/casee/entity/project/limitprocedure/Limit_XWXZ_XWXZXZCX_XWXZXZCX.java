package com.pig4cloud.pig.casee.entity.project.limitprocedure;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 行为限制限制撤销
 */
@Data
public class Limit_XWXZ_XWXZXZCX_XWXZXZCX extends CommonalityData implements Serializable {
	/**
	 * 撤销日期
	 */
	private LocalDate revokeTime;

	/**
	 * 撤销原因
	 */
	private String reasonForRevocation;

	/**
	 * 撤销依据
	 */
	private String groundsForRevocation;

	/**
	 * 附件
	 */
	private String appendixFile;

	/**
	 * 备注
	 */
	private String remark;
}
