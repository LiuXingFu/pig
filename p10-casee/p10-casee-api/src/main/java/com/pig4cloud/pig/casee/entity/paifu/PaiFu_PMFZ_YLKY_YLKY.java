package com.pig4cloud.pig.casee.entity.paifu;

import com.pig4cloud.pig.casee.entity.paifu.detail.ActualLookSamplerList;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
/**
 * 引领看样
 */
public class PaiFu_PMFZ_YLKY_YLKY implements Serializable {
	/**看样人员名单*/
	private List<ActualLookSamplerList> samplerList;

	/**附件地址*/
	private String fileUrl;
	/**图片地址*/
	private String imageUrl;
}
