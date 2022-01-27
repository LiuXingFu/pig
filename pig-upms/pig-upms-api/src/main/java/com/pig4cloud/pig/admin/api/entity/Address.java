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

package com.pig4cloud.pig.admin.api.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 地址表
 *
 * @author yuanduo
 * @date 2021-09-03 16:26:59
 */
@Data
@TableName("p10_address")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "地址表")
public class Address extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 地址id 主键自增
	 */
	@TableId
	@ApiModelProperty(value = "地址id 主键自增")
	private Integer addressId;
	/**
	 * 省
	 */
	@ApiModelProperty(value = "省")
	private String province;
	/**
	 * 市
	 */
	@ApiModelProperty(value = "市")
	private String city;
	/**
	 * 区
	 */
	@ApiModelProperty(value = "区")
	private String area;
	/**
	 * 信息地址
	 */
	@ApiModelProperty(value = "信息地址")
	private String informationAddress;
	/**
	 * 行政区划编号
	 */
	@ApiModelProperty(value = "行政区划编号")
	private String code;
	/**
	 * 类型id(根据类型存的关联id)(在原有用户id上做修改)
	 */
	@ApiModelProperty(value = "类型id(根据类型存的关联id)(在原有用户id上做修改)")
	private Integer userId;
	/**
	 * 联系电话
	 */
	@ApiModelProperty(value = "联系电话")
	private String contactPhone;
	/**
	 * 联系人姓名
	 */
	@ApiModelProperty(value = "联系人姓名")
	private String contactName;
	/**
	 * 是否默认
	 */
	@ApiModelProperty(value = "是否默认")
	private Integer isDefault;

	/**
	 * 类型(0-账号注册地址 1-主体关联地址 2-机构地址 3-网点地址 4-财产地址 )
	 */
	@ApiModelProperty(value = "类型(0-账号注册地址 1-主体关联地址 2-机构地址 3-网点地址 4-财产地址 )")
	private Integer type;

	/**
	 * 0-正常，1-删除
	 */
	@TableLogic
	private String delFlag;

	/**
	 * 地址来源（比如裁判文书网）
	 */
	@ApiModelProperty(value = "地址来源（比如裁判文书网）")
	private String source;
}
