package com.pig4cloud.pig.casee.entity.paifu;

import lombok.Data;

import java.io.Serializable;

@Data
/**
 * 处置结果
 */
public class PaiFu_JFFZ_CZJG_CZJG implements Serializable {
	/**竞价成功确认书*/
	private String confirmationUrl;
	/**收费函附件地址*/
	private String feeLetterUrl;
	/**裁定书附件地址*/
	private String awardUrl;
}
