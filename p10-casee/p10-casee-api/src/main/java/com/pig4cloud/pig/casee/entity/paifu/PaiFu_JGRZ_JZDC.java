package com.pig4cloud.pig.casee.entity.paifu;

import lombok.Data;

import java.io.Serializable;

@Data
/**
 * 尽职调查
 */
public class PaiFu_JGRZ_JZDC implements Serializable {
	/**
	 * 尽职调查报告
	 */
	private PaiFu_JGRZ_JZDC_JZDCBG paiFu_JGRZ_JZDC_JZDCBG=new PaiFu_JGRZ_JZDC_JZDCBG();

	/**
	 * 照片
	 */
	private PaiFu_JGRZ_JZDC_ZP paiFu_JGRZ_JZDC_ZP=new PaiFu_JGRZ_JZDC_ZP();

	/**
	 * 摄像素材
	 */
	private PaiFu_JGRZ_JZDC_SXSC paiFu_JGRZ_JZDC_SXSC=new PaiFu_JGRZ_JZDC_SXSC();

	/**
	 * 粘贴公告
	 */
	private PaiFu_JGRZ_JZDC_ZTGG paiFu_JGRZ_JZDC_ZTGG=new PaiFu_JGRZ_JZDC_ZTGG();

	/**
	 * 其它
	 */
	private PaiFu_JGRZ_JZDC_QT paiFu_JGRZ_JZDC_QT=new PaiFu_JGRZ_JZDC_QT();
}

