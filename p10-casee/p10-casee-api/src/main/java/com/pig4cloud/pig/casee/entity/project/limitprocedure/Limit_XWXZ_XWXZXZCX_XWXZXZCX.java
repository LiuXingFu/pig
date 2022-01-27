package com.pig4cloud.pig.casee.entity.project.limitprocedure;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 行为限制限制撤销
 */
@Data
public class Limit_XWXZ_XWXZXZCX_XWXZXZCX extends CommonalityData implements Serializable {
	/**
	 * 撤销日期
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date revokeTime;

	/**
	 * 撤销原因
	 */
	private String reasonForRevocation;

	/**
	 * 撤销依据
	 */
	private String groundsForRevocation;

	/**
	 * 备注
	 */
	private String remark;
}
