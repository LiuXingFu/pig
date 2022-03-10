package com.pig4cloud.pig.casee.entity.project.beillegalprocedure;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 行为违法限制撤销
 */
@Data
public class BeIllegal_XWWF_XWWFXZCX_XWWFXZCX extends CommonalityData implements Serializable {
	/**
	 * 撤销日期
	 */
	@JSONField(format="yyyy-MM-dd")
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
	 * 附件
	 */
	private String appendixFile;

	/**
	 * 备注
	 */
	private String remark;
}
