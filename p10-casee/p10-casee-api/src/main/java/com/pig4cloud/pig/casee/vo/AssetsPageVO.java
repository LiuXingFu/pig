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
package com.pig4cloud.pig.casee.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.casee.entity.Assets;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 财产表
 *
 * @author ligt
 * @date 2022-01-11 10:29:44
 */
@Data
public class AssetsPageVO extends Assets {

	/**
	 * 财产来源（1-抵押财产，2-案件）
	 */
	@ApiModelProperty(value="财产来源（1-抵押财产，2-案件）")
	private Integer assetsSource;

	/**
	 * 抵押权人
	 */
	@ApiModelProperty(value = "抵押权人")
	private Integer mortgagee;

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
