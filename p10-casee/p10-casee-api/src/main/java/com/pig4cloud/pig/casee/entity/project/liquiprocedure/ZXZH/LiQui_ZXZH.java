package com.pig4cloud.pig.casee.entity.project.liquiprocedure.ZXZH;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;

/**
 * 执行执恢
 */
@Data
public class LiQui_ZXZH extends CommonalityData implements Serializable {

	/**
	 *	执行阶段执恢立案送达情况
	 */
	LiQui_ZXZH_ZXZHLASDQK liQui_ZXZH_ZXZHLASDQK = new LiQui_ZXZH_ZXZHLASDQK();
}
