package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 清收-执行
 */
@Data
public class LiQui_ZX extends CommonalityData implements Serializable {

	/**
	 * 资产调查
	 */
	@ApiModelProperty("资产调查")
	private LiQui_ZX_ZCDC liQui_ZX_ZCDC = new LiQui_ZX_ZCDC();

	/**
	 * 资产控制
	 */
	@ApiModelProperty("资产控制")
	private LiQui_ZX_ZCKZ liQui_ZX_ZCKZ = new LiQui_ZX_ZCKZ();

	/**
	 * 资产处置
	 */
	@ApiModelProperty("资产处置")
	private LiQui_ZX_ZCCZ liQui_ZX_ZCCZ = new LiQui_ZX_ZCCZ();

	/**
	 * 财产分配
	 */
	@ApiModelProperty("财产分配")
	private LiQui_ZX_CCFP liQui_ZX_CCFB = new LiQui_ZX_CCFP();

	/**
	 * 结案
	 */
	@ApiModelProperty("结案")
	private LiQui_ZX_JA liQui_ZX_JA = new LiQui_ZX_JA();

}
