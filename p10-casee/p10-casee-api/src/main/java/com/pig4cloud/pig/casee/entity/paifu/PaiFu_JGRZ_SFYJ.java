package com.pig4cloud.pig.casee.entity.paifu;

import lombok.Data;

import java.io.Serializable;

@Data
/**
 * 双方议价
 */
public class PaiFu_JGRZ_SFYJ implements Serializable {
	/**
	 * 阿里报告
	 */
	private PaiFu_JGRZ_SFYJ_ALBG paiFu_JGRZ_SFYJ_ALBG=new PaiFu_JGRZ_SFYJ_ALBG();

	/**
	 * 议价笔录
	 */
	private PaiFu_JGRZ_SFYJ_YJBL paiFu_JGRZ_SFYJ_YJBL=new PaiFu_JGRZ_SFYJ_YJBL();

	/**
	 * 议价结果
	 */
	private PaiFu_JGRZ_SFYJ_YJJG paiFu_JGRZ_SFYJ_YJJG=new PaiFu_JGRZ_SFYJ_YJJG();
}
