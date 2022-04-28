package com.pig4cloud.pig.admin.api.dto;

import com.pig4cloud.pig.admin.api.entity.TaskNode;
import lombok.Data;

@Data
public class TaskMessageDTO {

	/**
	 * 机构id
	 */
	private Integer insId;

	/**
	 * 网点id
	 */
	private Integer outlesId;

	/**
	 * 指定清收用户id 如果消息机构类型为机构或网点不采用此用户id
	 */
	private Integer userId;

	/**
	 * 发送目标类型
	 *  1.机构
	 *  2.网点 必须有机构id与网点id
	 *  3.个人 必须有机构id与网点id加用户id
	 */
	private Integer messageGoalType;

	/**
	 * 发送消息目标权限
	 * 	发送目标类型
	 * 		1.机构
	 *			1001 所有机构用户
	 *			1101 所有机构管理
	 *			1201 所有机构管理与网点管理
	 *	    2.网点
	 *	    	2001 所有网点用户
	 *	    	2101 所有网点管理员
	 *	    3.个人
	 *	    	设置为null即可
	 */
	private Integer messageGoalPermission;

	/**
	 * 发送消息DTO对象
	 */
	MessageRecordDTO messageRecordDTO;

}
