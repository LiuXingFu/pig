package com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSYS;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.admin.api.entity.FileAdder;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 诉讼阶段一审上诉
 */
@Data
public class LiQui_SSYS_SSYSYGSS_SSYSYGSS extends CommonalityData implements Serializable {
	/**
	 * 原告上诉时间
	 */
	@JSONField(format="yyyy-MM-dd")
	private Date plaintiffsAppealTime;

	/**
	 * 上诉状
	 */
	private String petitionFile;

	/**
	 * 备注
	 */
	private String remark;
}
