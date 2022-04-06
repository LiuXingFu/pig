package com.pig4cloud.pig.casee.entity.project.entityzxprocedure;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import com.pig4cloud.pig.casee.entity.paifuentity.detail.ReserveSeeSampleSeeSampleListDetail;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *	报名看样
 */
@Data
public class EntityZX_STZX_BMKY_BMKY extends CommonalityData implements Serializable {

	/**
	 *
	 *  报名看样名单
	 */
	List<ReserveSeeSampleSeeSampleListDetail> reserveSeeSampleSeeSampleLists=new ArrayList<>();
}
