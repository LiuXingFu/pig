/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pig4cloud.pig.admin.api.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SysUserInsOutlesVO extends SysUser {

	/**
	 * 机构名称
	 */
	@ApiModelProperty(value = "机构名称")
	private String insName;

	/**
	 * 网点名称
	 */
	@ApiModelProperty(value = "网点名称")
	private String outlesName;

	/**
	 * 机构网点用户id
	 */
	@ApiModelProperty("机构网点用户id")
	private Integer insOutlesUserId;

	/**
	 * 职位
	 */
	@ApiModelProperty(value = "职位")
	private String position;

	/**
	 * 入职时间
	 */
	@ApiModelProperty(value = "入职时间")
	private LocalDateTime entryTime;

}
