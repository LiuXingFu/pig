package com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSYS;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 诉讼阶段一审原告上诉情况
 */
@Data
public class LiQui_SSYS_SSYSYGSSQK_SSYSYGSSQK extends CommonalityData implements Serializable {

	/**
	 * 确认日期
	 */
	@JSONField(format="yyyy-MM-dd")
	private Date confirmTime;

	/**
	 * 上诉情况(0-不上诉 1-上诉 )
	 */
	private Integer appeal;

	/**
	 * 备注
	 */
	private String remark;

}
