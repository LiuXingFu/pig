package com.pig4cloud.pig.casee.entity.project.liquiprocedure.LX;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;

/**
 * 清收-履行阶段
 */
@Data
public class LiQui_LX extends CommonalityData implements Serializable {

	/**
	 * 履行阶段督促
	 */
	LiQui_LX_LXDC liQui_LX_LXDC = new LiQui_LX_LXDC();

	/**
	 * 履行阶段执行和解协议
	 */
	LiQui_LX_LXZXHJXY liQui_LX_LXZXHJXY = new LiQui_LX_LXZXHJXY();

	/**
	 * 履行阶段首次执行立案
	 */
	LiQui_LX_LXSCZXLA liQui_LX_LXSCZXLA = new LiQui_LX_LXSCZXLA();

}
