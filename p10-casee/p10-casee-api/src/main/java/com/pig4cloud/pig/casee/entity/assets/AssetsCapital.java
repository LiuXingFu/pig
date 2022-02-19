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
import com.pig4cloud.pig.casee.entity.assets.detail.AssetsCapitalDetail;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 资金财产表
 *
 * @author ligt
 * @date 2022-01-11 10:29:44
 */
@TableName("p10_assets")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "财产表")
public class AssetsCapital extends Assets {

	@ApiModelProperty(value="资金财产详情")
	private AssetsCapitalDetail assetsCapitalDetail;

	public void setAssetsCapitalDetail(AssetsCapitalDetail assetsCapitalDetail) {
		this.assetsCapitalDetail = assetsCapitalDetail;
		this.setAssetsDetail(JSON.toJSONString(assetsCapitalDetail));
	}

	public void initAssetsCapitalDetail(){
		assetsCapitalDetail = JSON.parseObject(this.getAssetsDetail(), AssetsCapitalDetail.class );
	}

	public AssetsCapitalDetail getAssetsCapitalDetail() {
		if(assetsCapitalDetail==null && this.getAssetsDetail()!=null){
			assetsCapitalDetail = JSON.parseObject(this.getAssetsDetail(), AssetsCapitalDetail.class );
		}
		return assetsCapitalDetail;
	}

}
