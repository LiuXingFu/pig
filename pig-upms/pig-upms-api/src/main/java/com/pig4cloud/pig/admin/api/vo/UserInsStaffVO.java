package com.pig4cloud.pig.admin.api.vo;

import com.pig4cloud.pig.admin.api.entity.UserInstitutionStaff;
import lombok.Data;

import java.util.List;

@Data
public class UserInsStaffVO extends UserInstitutionStaff {

	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 用户昵称
	 */
	private String nickName;

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
}
