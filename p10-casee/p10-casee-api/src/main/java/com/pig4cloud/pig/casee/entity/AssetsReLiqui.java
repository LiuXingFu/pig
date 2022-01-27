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
import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.casee.entity.detail.AssetsReLiquiDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 项目移交财产关联表
 *
 * @author ligt
 * @date 2022-01-19 15:19:24
 */
@Data
@TableName("p10_assets_re")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "项目移交财产关联表")
public class AssetsReLiqui extends AssetsRe {

    @ApiModelProperty(value="移交财产关联表详情")
    private AssetsReLiquiDetail assetsReLiquiDetail;

	public void setAssetsReLiquiDetail(AssetsReLiquiDetail assetsReLiquiReDetail) {
		this.assetsReLiquiDetail = assetsReLiquiReDetail;
		this.setAssetsReDetail(JSON.toJSONString(assetsReLiquiReDetail));
	}

	public AssetsReLiquiDetail getAssetsReLiquiDetail() {
		if(assetsReLiquiDetail==null && this.getAssetsReDetail()!=null){
			assetsReLiquiDetail = JSON.parseObject(this.getAssetsReDetail(), AssetsReLiquiDetail.class );
		}
		return assetsReLiquiDetail;
	}
}
