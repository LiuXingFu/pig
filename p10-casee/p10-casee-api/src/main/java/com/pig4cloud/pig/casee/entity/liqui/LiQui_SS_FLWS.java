package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 法律⽂书
 */
@Data
public class LiQui_SS_FLWS extends CommonalityData implements Serializable {

	/**
	 * 法律⽂书
	 */
	@ApiModelProperty("法律⽂书")
	private LiQui_SS_FLWS_FLWS liQui_SS_FLWS_FLWS = new LiQui_SS_FLWS_FLWS();

}
