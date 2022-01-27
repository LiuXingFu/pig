package com.pig4cloud.pig.casee.entity.paifu;

import lombok.Data;

import java.io.Serializable;

@Data
/**
 * 交付辅助 协助清点
 */
public class PaiFu_JFFZ_JFFZ_XZQD implements Serializable {
	/**是否协助清点 0-否 1-是*/
	private Integer inventoryType;
	/**财产清单附件地址*/
	private String propertyListUrl;
}
