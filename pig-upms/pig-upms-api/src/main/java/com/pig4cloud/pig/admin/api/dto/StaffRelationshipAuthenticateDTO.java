package com.pig4cloud.pig.admin.api.dto;

import com.pig4cloud.pig.admin.api.entity.UserInstitutionStaff;
import lombok.Data;

@Data
public class StaffRelationshipAuthenticateDTO extends UserInstitutionStaff {

	/**
	 * 认证目标id
	 */
	private Integer authenticateGoalId;

	/**
	 * 认证目标类型 (1000-主体 1100-法院)
	 */
	private Integer authenticateGoalType;

}
