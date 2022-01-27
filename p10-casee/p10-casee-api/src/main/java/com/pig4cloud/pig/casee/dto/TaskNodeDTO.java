/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pig.casee.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.casee.entity.TaskNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 流程节点表
 *
 * @author xiaojun
 * @date 2021-09-07 17:01:38
 */
@Data
@TableName("p10_task_node")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "流程节点表")
public class TaskNodeDTO extends TaskNode {

	/**
	 * 子节点
	 */
	@ApiModelProperty(value="子节点")
	private List<TaskNodeDTO> children;


}
