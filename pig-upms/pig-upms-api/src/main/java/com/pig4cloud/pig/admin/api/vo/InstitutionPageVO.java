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

package com.pig4cloud.pig.admin.api.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 机构分页VO
 */
@Data
public class InstitutionPageVO {

	/**
	 * 机构id 主键自增
	 */
	@TableId
	@ApiModelProperty(value = "机构id 主键自增")
	private Integer insId;
	/**
	 * 机构名称
	 */
	@ApiModelProperty(value = "机构名称")
	private String insName;
	/**
	 * 机构类型
	 */
	@ApiModelProperty(value = "机构类型")
	private Integer insType;
	/**
	 * 是否禁用 0-否 1-是
	 */
	@ApiModelProperty(value = "是否禁用 0-否 1-是")
	private Integer canDisable;
	/**
	 * 传真
	 */
	@ApiModelProperty(value = "传真")
	private String insFax;
	/**
	 * 座机电话
	 */
	@ApiModelProperty(value = "座机电话")
	private String insLandLinePhone;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String insRemark;
	/**
	 * 删除状态(0-正常,1-已删除)
	 */
	@TableLogic
	private String delFlag;

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


}