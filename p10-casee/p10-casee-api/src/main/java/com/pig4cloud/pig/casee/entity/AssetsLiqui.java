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
package com.pig4cloud.pig.casee.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.casee.entity.detail.AssetsLiquiDetail;
import com.pig4cloud.pig.casee.entity.detail.AssetsLiquiDetail;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 清收移交财产表
 *
 * @author ligt
 * @date 2022-01-11 10:29:44
 */
@Data
@TableName("p10_assets")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "清收移交财产表")
public class AssetsLiqui extends Assets {

	@ApiModelProperty(value="地址")
	private Address address;

	@ApiModelProperty(value="清收移交财产详情")
	private AssetsLiquiDetail assetsLiquiDetail;

	@ApiModelProperty(value="身份证唯一标识")
	private String unifiedIdentity;

//	@ApiModelProperty(value="财产关联表(新增传值用)")
//	private AssetsReLiqui assetsRe;

	public void setAssetsLiquiDetail(AssetsLiquiDetail AssetsLiquiDetail) {
		this.assetsLiquiDetail = AssetsLiquiDetail;
		this.setAssetsDetail(JSON.toJSONString(AssetsLiquiDetail));
	}

	public void initCaseeLiquiDetail(){
		assetsLiquiDetail = JSON.parseObject(this.getAssetsDetail(), AssetsLiquiDetail.class );
	}

	public AssetsLiquiDetail getAssetsLiquiDetail() {
		if(assetsLiquiDetail==null && this.getAssetsDetail()!=null){
			assetsLiquiDetail = JSON.parseObject(this.getAssetsDetail(), AssetsLiquiDetail.class );
		}
		return assetsLiquiDetail;
	}
}
