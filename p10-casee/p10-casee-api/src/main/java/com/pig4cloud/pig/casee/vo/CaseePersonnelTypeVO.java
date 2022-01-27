package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.casee.entity.CaseePersonnel;
import lombok.Data;

@Data
public class CaseePersonnelTypeVO extends CaseePersonnel {

	/**
	 * 类型（0-申请人，1-被执行人，2-担保人）
	 */
	private Integer personnelReType;

}
