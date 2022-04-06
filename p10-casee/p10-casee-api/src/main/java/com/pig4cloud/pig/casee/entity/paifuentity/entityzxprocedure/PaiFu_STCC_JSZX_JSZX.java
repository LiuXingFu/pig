package com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import com.pig4cloud.pig.casee.entity.paifuentity.detail.ReserveSeeSampleConsultingList;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 接受咨询
 */
@Data
public class PaiFu_STCC_JSZX_JSZX extends CommonalityData implements Serializable {
	/**
	 * 咨询名单
	 */
	List<ReserveSeeSampleConsultingList> reserveSeeSampleConsultingLists=new ArrayList<>();
}
