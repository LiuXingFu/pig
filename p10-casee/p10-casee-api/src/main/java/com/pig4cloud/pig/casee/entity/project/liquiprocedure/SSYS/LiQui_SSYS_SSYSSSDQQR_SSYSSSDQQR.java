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
	@JSONField(format="yyyy-MM-dd")
	private Date confirmTime;

	/**
	 * 被告是否上诉(0-否 1-是 )
	 */
	private Integer defendantWhetherAppeal;

	/**
	 * 应诉时间
	 */
	@JSONField(format="yyyy-MM-dd")
	private Date respondingTime;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 上诉对象(0-被告 1-案外人）
	 */
	private Integer objectOfAppeal;

	/**
	 * 答辩状
	 */
	private String replyShapeFile;

}
