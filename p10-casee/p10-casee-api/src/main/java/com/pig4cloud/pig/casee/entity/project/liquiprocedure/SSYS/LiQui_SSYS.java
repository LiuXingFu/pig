package com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSYS;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;

/**
 * 清收-诉讼阶段一审
 */
@Data
public class LiQui_SSYS extends CommonalityData implements Serializable {

	/**
	 * 诉讼阶段一审立案送达情况
	 */
	LiQui_SSYS_SSYSLASDQK liQui_SSYS_SSYSLASDQK = new LiQui_SSYS_SSYSLASDQK();

	/**
	 * 诉讼阶段一审庭审信息
	 */
	LiQui_SSYS_SSYSTSXX liQui_SSYS_SSYSTSXX = new LiQui_SSYS_SSYSTSXX();

	/**
	 * 诉讼阶段一审裁判结果
	 */
	LiQui_SSYS_SSYSCPJG liQui_SSYS_SSYSCPJG = new LiQui_SSYS_SSYSCPJG();

	/**
	 * 诉讼阶段一审裁判文书最终送达情况
	 */
	LiQui_SSYS_SSYSCPWSZZSDQK liQui_SSYS_SSYSCPWSZZSDQK = new LiQui_SSYS_SSYSCPWSZZSDQK();

	/**
	 * 诉讼阶段一审原告上诉情况
	 */
	LiQui_SSYS_SSYSYGSSQK LiQui_SSYS_SSYSYGSSQK = new LiQui_SSYS_SSYSYGSSQK();

	/**
	 * 诉讼阶段一审原告上诉
	 */
	LiQui_SSYS_SSYSYGSS liQui_SSYS_SSYSYGSS = new LiQui_SSYS_SSYSYGSS();

	/**
	 * 诉讼阶段一审原告上诉送达情况
	 */
	LiQui_SSYS_SSYSYGSSSDQK liQui_SSYS_SSYSYGSSSDQK = new LiQui_SSYS_SSYSYGSSSDQK();

	/**
	 * 诉讼阶段一审上诉到期确认
	 */
	LiQui_SSYS_SSYSSSDQQR liQui_SSYS_SSYSSSDQQR = new LiQui_SSYS_SSYSSSDQQR();

	/**
	 * 诉讼阶段一审结案/撤案
	 */
	LiQui_SSYS_SSYSJACA liQui_SSYS_SSYSJACA = new LiQui_SSYS_SSYSJACA();

	/**
	 * 诉讼阶段一审结案/撤案送达情况
	 */
	LiQui_SSYS_SSYSJACASDQK liQui_SSYS_SSYSJACASDQK = new LiQui_SSYS_SSYSJACASDQK();
}
