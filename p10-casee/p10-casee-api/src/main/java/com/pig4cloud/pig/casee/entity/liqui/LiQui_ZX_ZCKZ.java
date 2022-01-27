package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 资产控制
 */
@Data
public class LiQui_ZX_ZCKZ extends CommonalityData implements Serializable {

	/**
	 * 资产控制
	 */
	@ApiModelProperty("资产控制")
	private LiQui_ZX_ZCKZ_ZCKZ liQui_ZX_ZCKZ_ZCKZ = new LiQui_ZX_ZCKZ_ZCKZ();
}
