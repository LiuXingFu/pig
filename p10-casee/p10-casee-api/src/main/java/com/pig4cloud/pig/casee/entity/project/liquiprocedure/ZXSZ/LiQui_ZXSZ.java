package com.pig4cloud.pig.casee.entity.project.liquiprocedure.ZXSZ;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;

/**
 * 清收-执行阶段首次执行
 */
@Data
public class LiQui_ZXSZ extends CommonalityData implements Serializable {

	/**
	 *	执行阶段首次执行立案送达情况
	 */
	LiQui_ZXSZ_ZXSZLASDQK liQui_ZXSZ_ZXSZLASDQK = new LiQui_ZXSZ_ZXSZLASDQK();

}
