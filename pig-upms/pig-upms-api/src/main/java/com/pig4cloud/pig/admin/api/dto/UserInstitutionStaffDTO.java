package com.pig4cloud.pig.admin.api.dto;

import com.pig4cloud.pig.admin.api.entity.UserInstitutionStaff;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserInstitutionStaffDTO extends UserInstitutionStaff {

	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 角色ID
	 */
	private Integer roleId;

	/**
	 * 部门ID
	 */
	private List<Integer> dept;

	/**
	 * 网点ID
	 */
	private List<Integer> outles;

	/**
	 * 权限类型
	 */
	private Integer roleType;

	/**
	 * 用户昵称
	 */
	private String nickName;

}
