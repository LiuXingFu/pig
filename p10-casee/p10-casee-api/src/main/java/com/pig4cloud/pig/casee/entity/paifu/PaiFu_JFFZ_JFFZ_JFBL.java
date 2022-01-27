package com.pig4cloud.pig.casee.entity.paifu;

import lombok.Data;

import java.io.Serializable;

@Data
/**
 * 交付辅助 交付笔录
 */
public class PaiFu_JFFZ_JFFZ_JFBL implements Serializable {
	/**是否协助制作交付笔录 0-否 1-是*/
	private Integer deliveryTranscriptType;
	/**交付笔录附件地址*/
	private String deliveryTranscriptUrl;
}
