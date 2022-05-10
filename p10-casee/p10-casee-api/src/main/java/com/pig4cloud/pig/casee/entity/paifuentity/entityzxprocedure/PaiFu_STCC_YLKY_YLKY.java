package com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import com.pig4cloud.pig.casee.entity.paifuentity.detail.ActualLookSamplerListDetail;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 引领看样
 */
@Data
public class PaiFu_STCC_YLKY_YLKY extends CommonalityData implements Serializable {
	/**看样人员名单*/
	private List<ActualLookSamplerListDetail> samplerList=new ArrayList<>();

}
