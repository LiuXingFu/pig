package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 清收-诉前
 */
@Data
public class LiQui_SQ extends CommonalityData implements Serializable {

	/**
	 * 债券清理
	 */
	@ApiModelProperty("债券清理")
	private LiQui_SQ_ZQQL liQui_SQ_ZQQL = new LiQui_SQ_ZQQL();

	/**
	 * 诉前催送
	 */
	@ApiModelProperty("诉前催送")
	private LiQui_SQ_SQCS liQui_SQ_SQCS = new LiQui_SQ_SQCS();

	/**
	 * 诉前保全
	 */
	@ApiModelProperty("诉前保全")
	private LiQui_SQ_SQBQ liQui_SQ_SQBQ = new LiQui_SQ_SQBQ();

	/**
	 * 诉前审查
	 */
	@ApiModelProperty("诉前审查")
	private LiQui_SQ_SQSC liQui_SQ_SQSC = new LiQui_SQ_SQSC();

	/**
	 * 结案
	 */
	@ApiModelProperty("结案")
	private LiQui_SQ_JA liQui_SQ_JA = new LiQui_SQ_JA();

}
