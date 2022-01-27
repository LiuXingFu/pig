package com.pig4cloud.pig.casee.entity.paifu;

import lombok.Data;

import java.io.Serializable;

@Data
/**
 * 交付辅助
 */
public class PaiFu_JFFZ_JFFZ implements Serializable {
	/**
	 * 协助变更
	 */
	private PaiFu_JFFZ_JFFZ_XZBG paiFu_JFFZ_JFFZ_XZBG=new PaiFu_JFFZ_JFFZ_XZBG();

	/**
	 * 协助清点
	 */
	private PaiFu_JFFZ_JFFZ_XZQD paiFu_JFFZ_JFFZ_XZQD=new PaiFu_JFFZ_JFFZ_XZQD();

	/**
	 * 协助腾退
	 */
	private PaiFu_JFFZ_JFFZ_XZTT paiFu_JFFZ_JFFZ_XZTT=new PaiFu_JFFZ_JFFZ_XZTT();

	/**
	 * 交付笔录
	 */
	private PaiFu_JFFZ_JFFZ_JFBL paiFu_JFFZ_JFFZ_JFBL=new PaiFu_JFFZ_JFFZ_JFBL();

	/**
	 * 其它
	 */
	private PaiFu_JFFZ_JFFZ_QT paiFu_JFFZ_JFFZ_QT=new PaiFu_JFFZ_JFFZ_QT();
}
