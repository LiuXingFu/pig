package com.pig4cloud.pig.casee.entity.paifu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
/**
 * 交付辅助 其它
 */
public class PaiFu_JFFZ_JFFZ_QT implements Serializable {
	/**附件地址*/
	@ApiModelProperty(value = "附件地址")
	private String fileUrl;
	/**图片地址*/
	@ApiModelProperty(value = "图片地址")
	private String imageUrl;
}
