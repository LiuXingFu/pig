package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 债券清理
 */
@Data
public class LiQui_SQ_ZQQL_ZQQL extends CommonalityData implements Serializable {

	/**
	 * 债券金额
	 */
	@ApiModelProperty(value = "债券金额")
	private BigDecimal bondAmount;

}
