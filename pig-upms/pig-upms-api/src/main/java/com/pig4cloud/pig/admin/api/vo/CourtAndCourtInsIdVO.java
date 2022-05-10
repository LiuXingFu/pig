package com.pig4cloud.pig.admin.api.vo;

import com.pig4cloud.pig.admin.api.entity.Court;
import lombok.Data;

@Data
public class CourtAndCourtInsIdVO extends Court {



	/**
	 * 关联法院id
	 */
	private Integer courtInsId;

//	private

}
