package com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSES;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;

/**
 * 清收-诉讼阶段二审
 */
@Data
public class LiQui_SSES extends CommonalityData implements Serializable {

	/**
	 * 诉讼阶段二审立案送达情况
	 */
	LiQui_SSES_SSESLASDQK liQui_SSES_SSESLASDQK = new LiQui_SSES_SSESLASDQK();

	/**
	 * 诉讼阶段二审庭审信息
	 */
	LiQui_SSES_SSESTSXX liQui_SSES_SSESTSXX = new LiQui_SSES_SSESTSXX();

	/**
	 * 诉讼阶段二审裁判结果
	 */
	LiQui_SSES_SSESCPJG liQui_SSES_SSESCPJG = new LiQui_SSES_SSESCPJG();

	/**
	 * 诉讼阶段二审裁判文书最终送达情况
	 */
	LiQui_SSES_SSESCPWSZZSDQK liQui_SSES_SSESCPWSZZSDQK = new LiQui_SSES_SSESCPWSZZSDQK();

}
