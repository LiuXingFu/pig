package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 债券清理
 */
@Data
public class LiQui_SQ_ZQQL extends CommonalityData implements Serializable {

	/**
	 * 债券清理
	 */
	@ApiModelProperty("债券清理")
	private LiQui_SQ_ZQQL_ZQQL liQui_SQ_ZQQL_ZQQL = new LiQui_SQ_ZQQL_ZQQL();

}
