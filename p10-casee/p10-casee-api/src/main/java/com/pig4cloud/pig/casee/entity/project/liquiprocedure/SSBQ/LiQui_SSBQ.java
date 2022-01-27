package com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSBQ;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;

/**
 * 诉讼阶段保全立案送达情况
 */
@Data
public class LiQui_SSBQ extends CommonalityData implements Serializable {

	/**
	 * 诉讼阶段保全立案送达情况
	 */
	LiQui_SSBQ_SSBQLASDQK liQui_SSBQ_SSBQLASDQK = new LiQui_SSBQ_SSBQLASDQK();

	/**
	 * 诉讼阶段保全保全结果
	 */
	LiQui_SSBQ_SSBQBQJG liQui_SSBQ_SSBQBQJG = new LiQui_SSBQ_SSBQBQJG();

	/**
	 * 诉讼阶段保全结果送达情况
	 */
	LiQui_SSBQ_SSBQBQJGSDQK liQui_SSBQ_SSBQBQJGSDQK = new LiQui_SSBQ_SSBQBQJGSDQK();

	/**
	 * 诉讼阶段保全撤案
	 */
	LiQui_SSBQ_SSBQCA liQui_SSBQ_SSBQCA = new LiQui_SSBQ_SSBQCA();

	/**
	 * 诉讼阶段保全撤案送达情况
	 */
	LiQui_SSBQ_SSBQCASDQK liQui_SSBQ_SSBQCASDQK = new LiQui_SSBQ_SSBQCASDQK();

}
