package com.pig4cloud.pig.casee.entity.paifu;

import lombok.Data;

import java.io.Serializable;

@Data
/**
 * 价格认证
 */
public class PaiFu_JGRZ implements Serializable {
	/**
	 * 双方议价
	 */
	private PaiFu_JGRZ_SFYJ paiFu_JGRZ_SFYJ=new PaiFu_JGRZ_SFYJ();

	/**
	 * 定向询价
	 */
	private PaiFu_JGRZ_DXXJ paiFu_JGRZ_DXXJ=new PaiFu_JGRZ_DXXJ();

	/**
	 * 网络询价
	 */
	private PaiFu_JGRZ_WLXJ paiFu_JGRZ_WLXJ=new PaiFu_JGRZ_WLXJ();

	/**
	 * 评估定价
	 */
	private PaiFu_JGRZ_PGDJ paiFu_JGRZ_PGDJ=new PaiFu_JGRZ_PGDJ();

	/**
	 * 尽职调查
	 */
	private PaiFu_JGRZ_JZDC paiFu_JGRZ_JZDC=new PaiFu_JGRZ_JZDC();



}
