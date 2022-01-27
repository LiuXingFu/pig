package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 资产处置
 */
@Data
public class LiQui_ZX_ZCCZ extends CommonalityData implements Serializable {

	/**
	 * 资产处置
	 */
	@ApiModelProperty("资产处置")
	private LiQui_ZX_ZCCZ_ZCCZ liQui_ZX_ZCCZ_ZCCZ = new LiQui_ZX_ZCCZ_ZCCZ();

}
