package com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSYS;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 诉讼阶段一审原告上诉情况
 */
@Data
public class LiQui_SSYS_SSYSYGSSQK_SSYSYGSSQK extends CommonalityData implements Serializable {

	/**
	 * 确认日期
	 */
	private LocalDate confirmTime;

	/**
	 * 上诉情况(0-不上诉 1-上诉 )
	 */
	private Integer appeal;

	/**
	 * 备注
	 */
	private String remark;

}
