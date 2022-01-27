package com.pig4cloud.pig.admin.api.vo;

import com.pig4cloud.pig.admin.api.entity.Outles;
import com.pig4cloud.pig.admin.api.entity.SysDept;
import com.pig4cloud.pig.admin.api.entity.SysRole;
import com.pig4cloud.pig.admin.api.entity.UserInstitutionStaff;
import lombok.Data;

import java.util.List;

@Data
public class UserInstitutionStaffVO extends UserInstitutionStaff {

	/**
	 * 当前所属部门
	 */
	List<SysDept> deptList;

	/**
	 * 当前所属角色
	 */
	List<SysRole> sysRoleList;

	/**
	 * 当前所属网点
	 */
	List<Outles> outlesList;

	/**
	 * 联系电话
	 */
	String phone;

}
