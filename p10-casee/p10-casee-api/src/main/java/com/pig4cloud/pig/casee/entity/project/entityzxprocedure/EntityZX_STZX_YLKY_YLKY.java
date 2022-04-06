package com.pig4cloud.pig.casee.entity.project.entityzxprocedure;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import com.pig4cloud.pig.casee.entity.paifuentity.detail.ActualLookSamplerListDetail;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 引领看样
 */
@Data
public class EntityZX_STZX_YLKY_YLKY extends CommonalityData implements Serializable {
	/**看样人员名单*/
	private List<ActualLookSamplerListDetail> samplerList;

	/**附件地址*/
	private String fileUrl;
	/**图片地址*/
	private String imageUrl;
}
