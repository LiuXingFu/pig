package com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import com.pig4cloud.pig.casee.entity.paifuentity.detail.SamplePreparationWorkListDetail;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 看样准备工作
 */
@Data
public class PaiFu_STCC_KYZBGZ_KYZBGZ extends CommonalityData implements Serializable {

	/**
	 * 看样准备工作信息
	 */
	List<SamplePreparationWorkListDetail> samplePreparationWorkListDetails=new ArrayList<>();
}
