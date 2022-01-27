package com.pig4cloud.pig.casee.entity.paifu;

import lombok.Data;

import java.io.Serializable;

@Data
/**
 * 交付辅助
 */
public class PaiFu_JFFZ implements Serializable {
	/**
	 * 处置结果
	 */
	private PaiFu_JFFZ_CZJG paiFu_JFFZ_CZJG=new PaiFu_JFFZ_CZJG();
	/**
	 * 交付辅助
	 */
	private PaiFu_JFFZ_JFFZ paiFu_JFFZ_JFFZ=new PaiFu_JFFZ_JFFZ();
}
