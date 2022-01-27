package com.pig4cloud.pig.casee.entity.paifu;

import lombok.Data;

import java.io.Serializable;

@Data
/**
 * 评估定价
 */
public class PaiFu_JGRZ_PGDJ implements Serializable {
	/**
	 * 文书制作
	 */
	private PaiFu_JGRZ_PGDJ_WSZZ paiFu_JGRZ_PGDJ_WSZZ=new PaiFu_JGRZ_PGDJ_WSZZ();

	/**
	 * 系统录入
	 */
	private PaiFu_JGRZ_PGDJ_XTLR paiFu_JGRZ_PGDJ_XTLR=new PaiFu_JGRZ_PGDJ_XTLR();

	/**
	 * 评估公司对接
	 */
	private PaiFu_JGRZ_PGDJ_PGGSDJ paiFu_JGRZ_PGDJ_PGGSDJ=new PaiFu_JGRZ_PGDJ_PGGSDJ();
}
