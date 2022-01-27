package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 清收-履行
 */
@Data
public class LiQui_LX extends CommonalityData implements Serializable {

	/**
	 * 执前催收
	 */
	@ApiModelProperty("执前催收")
	private LiQui_LX_ZQCS liQui_LX_ZQCS = new LiQui_LX_ZQCS();

	/**
	 * 执前现勘
	 */
	@ApiModelProperty("执前现勘")
	private LiQui_LX_ZQXK liQui_LX_ZQXK = new LiQui_LX_ZQXK();

	/**
	 * 执前审查
	 */
	@ApiModelProperty("执前审查")
	private LiQui_LX_ZQSC liQui_LX_ZQSC = new LiQui_LX_ZQSC();

	/**
	 * 结案
	 */
	@ApiModelProperty("结案")
	private LiQui_LX_JA liQui_LX_JA = new LiQui_LX_JA();

}
