package com.pig4cloud.pig.casee.entity.paifu;

import lombok.Data;

import java.io.Serializable;

@Data
/**
 * 交付辅助 协助腾退
 */
public class PaiFu_JFFZ_JFFZ_XZTT implements Serializable {
	/**是否协助腾退 0-否 1-是*/
	private Integer retreatType;
	/**腾退图片地址*/
	private String retreatImageUrl;
}
