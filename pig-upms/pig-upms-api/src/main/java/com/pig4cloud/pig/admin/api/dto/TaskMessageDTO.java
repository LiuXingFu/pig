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
	 *  2.网点 如果群发网点加上机构id
	 *  3.个人
	 */
	private Integer messageGoalType;

	/**
	 * 发送消息目标权限
	 * 	发送目标类型
	 * 		1.机构
	 *
	 */
	private Integer messageGoalPermission;

	/**
	 * 发送消息DTO对象
	 */
	MessageRecordDTO messageRecordDTO;

}
