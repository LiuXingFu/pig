package com.pig4cloud.pig.admin.api.dto;

import com.pig4cloud.pig.admin.api.entity.InsOutlesCourtRe;
import lombok.Data;

@Data
public class InsOutlesCourtRePageDTO extends InsOutlesCourtRe {

	/**
	 * 法院机构id
	 */
	private Integer courtInsId;

}
