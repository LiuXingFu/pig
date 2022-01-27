package com.pig4cloud.pig.casee.entity.project.liquiprocedure.ZXSZ;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 清收
 */
@Data
public class LiQuiZXSZ extends CommonalityData implements Serializable {

	/**
	 * 执行阶段首次执行
	 */
	@ApiModelProperty("执行阶段首次执行")
	private LiQui_ZXSZ liQui_ZXSZ = new LiQui_ZXSZ();

}
