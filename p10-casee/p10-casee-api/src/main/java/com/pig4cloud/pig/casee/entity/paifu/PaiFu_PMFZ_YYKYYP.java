package com.pig4cloud.pig.casee.entity.paifu;

import lombok.Data;

import java.io.Serializable;

@Data
/**
 * 预约看样一拍
 */
public class PaiFu_PMFZ_YYKYYP implements Serializable {

	/**
	 * 接受咨询
	 */
	private PaiFu_PMFZ_YYKY_JSZX paiFu_PMFZ_YYKYYP_JSZX=new PaiFu_PMFZ_YYKY_JSZX();

	/**
	 * 宣传推广
	 */
	private PaiFu_PMFZ_YYKY_XCTG paiFu_PMFZ_YYKYYP_XCTG=new PaiFu_PMFZ_YYKY_XCTG();

	/**
	 * 看样报名
	 */
	private PaiFu_PMFZ_YYKY_KYBM paiFu_PMFZ_YYKYYP_KYBM=new PaiFu_PMFZ_YYKY_KYBM();
}
