package com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSYS;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 诉讼阶段一审上诉
 */
@Data
public class LiQui_SSYS_SSYSYGSS_SSYSYGSS extends CommonalityData implements Serializable {
	/**
	 * 原告上诉时间
	 */
	private LocalDate plaintiffsAppealTime;

	/**
	 * 上诉状
	 */
	private String petitionFile;

	/**
	 * 备注
	 */
	private String remark;
}
