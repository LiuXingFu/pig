package com.pig4cloud.pig.casee.entity.paifu;

import lombok.Data;

import java.io.Serializable;

@Data
/**
 * 拍卖辅助 公告前辅助一拍
 */
public class PaiFu_PMFZ_GGQFZYP implements Serializable {
	/**
	 * 文书制作
	 */
	private PaiFu_PMFZ_GGQFZ_WSZZ paiFu_PMFZ_GGQFZYP_WSZZ=new PaiFu_PMFZ_GGQFZ_WSZZ();

	/**
	 * 公告上传
	 */
	private PaiFu_PMFZ_GGQFZ_GGSC paiFu_PMFZ_GGQFZYP_GGSC=new PaiFu_PMFZ_GGQFZ_GGSC();

	/**
	 * 其它
	 */
	private PaiFu_PMFZ_GGQFZ_QT paiFu_PMFZ_GGQFZYP_QT=new PaiFu_PMFZ_GGQFZ_QT();

}
