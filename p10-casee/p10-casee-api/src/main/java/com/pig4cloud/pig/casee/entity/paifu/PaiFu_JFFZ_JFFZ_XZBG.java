package com.pig4cloud.pig.casee.entity.paifu;

import lombok.Data;

import java.io.Serializable;

@Data
/**
 * 交付辅助 协助变更
 */
public class PaiFu_JFFZ_JFFZ_XZBG implements Serializable {
	/**是否协助变更 0-否 1-是*/
	private Integer helpChangeType;
	/**执行通知书附件地址*/
	private String executionNoticeUrl;
}
