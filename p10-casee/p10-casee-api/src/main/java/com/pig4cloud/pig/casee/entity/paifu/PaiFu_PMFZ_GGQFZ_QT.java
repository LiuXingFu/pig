package com.pig4cloud.pig.casee.entity.paifu;

import lombok.Data;

import java.io.Serializable;

@Data
/**
 * 公告前辅助 其它
 */
public class PaiFu_PMFZ_GGQFZ_QT implements Serializable {
	/**附件地址*/
	private String fileUrl;
	/**图片地址*/
	private String imageUrl;
}
