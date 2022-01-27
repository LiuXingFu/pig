package com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSYS;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 诉讼阶段一审上诉到期确认
 */
@Data
public class LiQui_SSYS_SSYSSSDQQR_SSYSSSDQQR extends CommonalityData implements Serializable {
	/**
	 * 确认日期
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date confirmTime;

	/**
	 * 生效日期
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date takeEffectTime;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 被告是否上诉(0-否 1-是 )
	 */
	private Integer defendantWhetherAppeal;

	/**
	 * 生效履行期限
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date effectivePerformanceTime;
}
