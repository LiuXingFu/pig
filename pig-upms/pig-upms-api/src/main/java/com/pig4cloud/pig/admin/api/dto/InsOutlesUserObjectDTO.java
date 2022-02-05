package com.pig4cloud.pig.admin.api.dto;

import com.pig4cloud.pig.admin.api.entity.SysUser;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.admin.api.dto
 * @ClassNAME: InsOutlesUserObjectDTO
 * @Author: yd
 * @DATE: 2022/2/3
 * @TIME: 22:22
 * @DAY_NAME_SHORT: 周四
 */
@Data
public class InsOutlesUserObjectDTO {

	/**
	 * 机构id
	 */
	private Integer insId;

	/**
	 * 网点id
	 */
	private Integer outlesId;

	/**
	 * 类型：1-管理员，2-普通员工
	 */
	private Integer type;

	/**
	 * 入职时间
	 */
	private LocalDateTime entryTime;

	/**
	 * 职位
	 */
	private String position;

	/**
	 * 用户集合
	 */
	private List<SysUser> userList;

}
