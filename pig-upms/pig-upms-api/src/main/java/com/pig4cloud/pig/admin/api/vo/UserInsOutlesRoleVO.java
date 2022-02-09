package com.pig4cloud.pig.admin.api.vo;

import com.pig4cloud.pig.admin.api.entity.Institution;
import com.pig4cloud.pig.admin.api.entity.Outles;
import com.pig4cloud.pig.admin.api.entity.SysRole;
import lombok.Data;

@Data
public class UserInsOutlesRoleVO {

	/**
	 * 机构信息
	 */
	private Institution institution;

	/**
	 * 网点信息
	 */
	private Outles outles;

	/**
	 * 角色信息
	 */
	private SysRole sysRole;

	/**
	 * 权限标识集合
	 */
	private String[] permissions;
}
