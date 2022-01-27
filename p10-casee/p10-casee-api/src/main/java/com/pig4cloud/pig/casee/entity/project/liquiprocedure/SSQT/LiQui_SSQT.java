package com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSQT;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;

/**
 * 诉讼阶段其他
 */
@Data
public class LiQui_SSQT extends CommonalityData implements Serializable {

	/**
	 * 诉讼阶段其他立案送达情况
	 */
	LiQui_SSQT_SSQTLASDQK liQui_SSQT_SSQTLASDQK = new LiQui_SSQT_SSQTLASDQK();

	/**
	 * 诉讼阶段其他庭审信息
	 */
	LiQui_SSQT_SSQTTSXX liQui_SSQT_SSQTTSXX = new LiQui_SSQT_SSQTTSXX();

	/**
	 * 诉讼阶段其他裁判结果
	 */
	LiQui_SSQT_SSQTCPJG liQui_SSQT_SSQTCPJG = new LiQui_SSQT_SSQTCPJG();

	/**
	 * 诉讼阶段其他裁判文书最终送达情况
	 */
	LiQui_SSQT_SSQTCPWSZZSDQK liQui_SSQT_SSQTCPWSZZSDQK = new LiQui_SSQT_SSQTCPWSZZSDQK();

	/**
	 * 诉讼阶段二审结案/撤案
	 */
	LiQui_SSQT_SSQTJACA liQui_SSQT_SSQTJACA = new LiQui_SSQT_SSQTJACA();

	/**
	 * 诉讼阶段二审结案/撤案送达情况
	 */
	LiQui_SSQT_SSQTJACASDQK liQui_SSQT_SSQTJACASDQK = new LiQui_SSQT_SSQTJACASDQK();

}
