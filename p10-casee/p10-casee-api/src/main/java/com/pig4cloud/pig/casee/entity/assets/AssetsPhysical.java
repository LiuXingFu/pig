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
package com.pig4cloud.pig.casee.entity.assets;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.casee.entity.Assets;
import com.pig4cloud.pig.casee.entity.assets.detail.AssetsPhysicalDetail;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实体财产表
 *
 * @author ligt
 * @date 2022-01-11 10:29:44
 */
@TableName("p10_assets")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "财产表")
public class AssetsPhysical extends Assets {

	@ApiModelProperty(value="实体财产详情")
	private AssetsPhysicalDetail assetsPhysicalDetail;

	public void setCaseeLiquiDetail(AssetsPhysicalDetail assetsPhysicalDetail) {
		this.assetsPhysicalDetail = assetsPhysicalDetail;
		this.setAssetsDetail(JSON.toJSONString(assetsPhysicalDetail));
	}

	public void initCaseeLiquiDetail(){
		assetsPhysicalDetail = JSON.parseObject(this.getAssetsDetail(), AssetsPhysicalDetail.class );
	}

	public AssetsPhysicalDetail assetsPhysicalDetail() {
		if(assetsPhysicalDetail==null && this.getAssetsDetail()!=null){
			assetsPhysicalDetail = JSON.parseObject(this.getAssetsDetail(), AssetsPhysicalDetail.class );
		}
		return assetsPhysicalDetail;
	}

}
