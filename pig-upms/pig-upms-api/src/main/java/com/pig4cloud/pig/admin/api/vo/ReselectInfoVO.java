package com.pig4cloud.pig.admin.api.vo;

import com.pig4cloud.pig.admin.api.entity.Institution;
import com.pig4cloud.pig.admin.api.entity.Outles;
import com.pig4cloud.pig.admin.api.entity.SysRole;
import lombok.Data;

/**
 * 查询当前登录用户机构、网点、角色信息
 */
@Data
public class ReselectInfoVO {
	/**
	 * 机构
	 */
	Institution institution;

	/**
	 * 网点
	 */
	Outles outles;

	/**
	 * 角色
	 */
	SysRole sysRole;
}
