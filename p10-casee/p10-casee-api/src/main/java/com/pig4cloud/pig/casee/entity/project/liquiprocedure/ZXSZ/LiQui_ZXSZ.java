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

	/**
	 *	执行阶段首次执行领款
	 */
	LiQui_ZXSZ_ZXSZLK liQui_ZXSZ_ZXSZLK = new LiQui_ZXSZ_ZXSZLK();

	/**
	 *	执行阶段首次执行法院到款
	 */
	LiQui_ZXSZ_ZXSZFYDK liQui_ZXSZ_ZXSZFYDK = new LiQui_ZXSZ_ZXSZFYDK();

	/**
	 * 执行阶段首次执行终结
	 */
	LiQui_ZXSZ_ZXSZZJ liQui_ZXSZ_ZXSZZJ = new LiQui_ZXSZ_ZXSZZJ();

	/**
	 * 执行阶段首次执行实际执结
	 */
	LiQui_ZXSZ_ZXSZSJZJ liQui_ZXSZ_ZXSZSJZJ = new LiQui_ZXSZ_ZXSZSJZJ();

	/**
	 * 执行阶段首次执行结案/撤案
	 */
	LiQui_ZXSZ_ZXSZJACA liQui_ZXSZ_ZXSZJACA = new LiQui_ZXSZ_ZXSZJACA();

	/**
	 *	执行阶段首次执行结案/撤案送达情况
	 */
	LiQui_ZXSZ_ZXSZJACASDQK liQui_ZXSZ_ZXSZJACASDQK = new LiQui_ZXSZ_ZXSZJACASDQK();

}
