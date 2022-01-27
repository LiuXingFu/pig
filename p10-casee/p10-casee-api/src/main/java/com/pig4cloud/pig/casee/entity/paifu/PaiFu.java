package com.pig4cloud.pig.casee.entity.paifu;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaiFu implements Serializable {
	/**
	 * 价格认证
	 */
	private PaiFu_JGRZ paiFu_JGRZ=new PaiFu_JGRZ();
	/**
	 * 拍卖辅助
	 */
	private PaiFu_PMFZ paiFu_PMFZ=new PaiFu_PMFZ();

	/**
	 * 交付辅助
	 */
	private PaiFu_JFFZ paiFu_JFFZ=new PaiFu_JFFZ();
}
