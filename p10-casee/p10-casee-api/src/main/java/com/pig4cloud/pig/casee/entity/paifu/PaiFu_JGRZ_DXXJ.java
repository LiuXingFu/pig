package com.pig4cloud.pig.casee.entity.paifu;

import lombok.Data;

import java.io.Serializable;

@Data
/**
 * 价格认证 定向询价
 */
public class PaiFu_JGRZ_DXXJ implements Serializable {
	/**
	 * 文书制作
	 */
	private PaiFu_JGRZ_DXXJ_WSZZ paiFu_JGRZ_DXXJ_WSZZ=new PaiFu_JGRZ_DXXJ_WSZZ();

	/**
	 * 信息录入
	 */
	private PaiFu_JGRZ_DXXJ_XXLR paiFu_JGRZ_DXXJ_XXLR=new PaiFu_JGRZ_DXXJ_XXLR();

	/**
	 * 询价结果
	 */
	private PaiFu_JGRZ_DXXJ_XJJG paiFu_JGRZ_DXXJ_XJJG=new PaiFu_JGRZ_DXXJ_XJJG();
}
