package com.pig4cloud.pig.admin.api.vo;

import com.pig4cloud.pig.admin.api.entity.AssociateOutlesRe;
import lombok.Data;

@Data
public class AssociateOutlesReVO extends AssociateOutlesRe {

	/**
	 * 机构名称
	 */
	private String insName;

	/**
	 * 网点名称
	 */
	private String outlesName;

}
