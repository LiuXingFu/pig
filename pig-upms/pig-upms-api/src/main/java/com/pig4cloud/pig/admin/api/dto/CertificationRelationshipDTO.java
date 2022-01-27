package com.pig4cloud.pig.admin.api.dto;

import com.pig4cloud.pig.admin.api.entity.InstitutionAssociate;
import lombok.Data;

@Data
public class CertificationRelationshipDTO extends InstitutionAssociate {

	/**
	 * 消息id
	 */
	private Integer messageId;

}
