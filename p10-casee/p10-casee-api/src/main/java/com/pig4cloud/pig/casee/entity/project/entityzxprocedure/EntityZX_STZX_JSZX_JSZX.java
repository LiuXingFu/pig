package com.pig4cloud.pig.casee.entity.project.entityzxprocedure;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import com.pig4cloud.pig.casee.entity.paifuentity.detail.ReserveSeeSampleConsultingListDetail;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 接受咨询
 */
@Data
public class EntityZX_STZX_JSZX_JSZX extends CommonalityData implements Serializable {
	/**
	 * 咨询名单
	 */
	List<ReserveSeeSampleConsultingListDetail> reserveSeeSampleConsultingLists=new ArrayList<>();
}
