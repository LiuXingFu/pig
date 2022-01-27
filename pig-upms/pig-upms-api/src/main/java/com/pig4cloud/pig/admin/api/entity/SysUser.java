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

package com.pig4cloud.pig.admin.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author lengleng
 * @since 2019/2/1
 */
@Data
public class SysUser extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "user_id", type = IdType.AUTO)
	@ApiModelProperty(value = "主键id")
	private Integer userId;

	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名")
	private String username;

	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	private String password;

	/**
	 * 昵称
	 */
	@ApiModelProperty(value = "昵称")
	private String nickName;

	/**
	 * 用户类型 0-普通 1-机构
	 */
	@ApiModelProperty(value = "用户类型 0-普通 1-机构")
	private Integer userType;

	/**
	 * 随机盐
	 */
	@JsonIgnore
	@ApiModelProperty(value = "随机盐")
	private String salt;

	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "手机号")
	private String phone;

	/**
	 * 座机
	 */
	@ApiModelProperty(value = "座机")
	private String landLine;

	/**
	 * 性别1-男,2-女
	 */
	@ApiModelProperty(value = "性别1-男,2-女")
	private String sex;

	/**
	 * 头像
	 */
	@ApiModelProperty(value = "头像地址")
	private String avatar;

	/**
	 * 部门ID
	 */
	@ApiModelProperty(value = "用户所属部门id")
	private Integer deptId;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	/**
	 * 电子邮件
	 */
	@ApiModelProperty(value = "电子邮件")
	private String email;

	/**
	 * 锁定标记
	 */
	@ApiModelProperty(value = "锁定标记")
	private String lockFlag;

	/**
	 * 0-正常，1-删除
	 */
	@TableLogic
	private String delFlag;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;/**

	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	private LocalDateTime updateTime;

	/**
	 * 创建者
	 */
	@ApiModelProperty(value = "创建者")
	private Integer createBy;/**

	 /**
	 * 更新人
	 */
	@ApiModelProperty(value = "更新人")
	private Integer updateBy;

}
