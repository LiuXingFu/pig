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

	/**
	 * 执行阶段执恢领款
	 */
	LiQui_ZXZH_ZXZHLK liQui_ZXZH_ZXZHLK = new LiQui_ZXZH_ZXZHLK();

	/**
	 * 执行阶段执恢法院到款
	 */
	LiQui_ZXZH_ZXZHFYDK liQui_ZXZH_ZXZHFYDK= new LiQui_ZXZH_ZXZHFYDK();

	/**
	 * 执行阶段执恢终结
	 */
	LiQui_ZXZH_ZXZHZJ liQui_ZXZH_ZXZHZJ = new LiQui_ZXZH_ZXZHZJ();

	/**
	 * 执行阶段执恢实际执结
	 */
	LiQui_ZXZH_ZXZHSJZJ liQui_ZXZH_ZXZHSJZJ = new LiQui_ZXZH_ZXZHSJZJ();

	/**
	 * 执行阶段执恢结案/撤案
	 */
	LiQui_ZXZH_ZXZHJACA liQui_ZXZH_ZXZHJACA = new LiQui_ZXZH_ZXZHJACA();

	/**
	 * 执行阶段执恢结案/撤案送达情况
	 */
	LiQui_ZXZH_ZXZHJACASDQK liQui_ZXZH_ZXZHJACASDQK = new LiQui_ZXZH_ZXZHJACASDQK();

}
