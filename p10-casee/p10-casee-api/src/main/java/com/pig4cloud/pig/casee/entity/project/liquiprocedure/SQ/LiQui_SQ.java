package com.pig4cloud.pig.casee.entity.project.liquiprocedure.SQ;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;

/**
 * 清收-诉前阶段
 */
@Data
public class LiQui_SQ extends CommonalityData implements Serializable {

	/**
	 * 诉前阶段立案送达情况
	 */
	LiQui_SQ_SQLASDQK liQui_SQ_SQLASDQK = new LiQui_SQ_SQLASDQK();

	/**
	 * 诉前阶段保全结果
	 */
	LiQui_SQ_SQBQJG liQui_SQ_SQBQJG = new LiQui_SQ_SQBQJG();

	/**
	 * 诉前阶段保全结果送达情况
	 */
	LiQui_SQ_SQBQJGSDQK liQui_SQ_SQBQJGSDQK = new LiQui_SQ_SQBQJGSDQK();

	/**
	 * 诉前阶段案件撤案
	 */
	LiQui_SQ_SQAJCA liQui_SQ_SQAJCA = new LiQui_SQ_SQAJCA();

	/**
	 * 诉前阶段撤案送达情况
	 */
	LiQui_SQ_SQAJCASDQK liQui_SQ_SQAJCASDQK = new LiQui_SQ_SQAJCASDQK();



}
