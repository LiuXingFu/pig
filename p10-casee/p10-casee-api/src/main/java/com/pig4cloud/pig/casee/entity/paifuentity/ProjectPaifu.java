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
package com.pig4cloud.pig.casee.entity.paifuentity;

import com.alibaba.fastjson.JSON;
import com.pig4cloud.pig.casee.entity.Project;
import com.pig4cloud.pig.casee.entity.paifuentity.detail.ProjectPaifuDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 项目表
 *
 * @author ligt
 * @date 2022-01-10 15:05:49
 */

@Data
public class ProjectPaifu extends Project {

	@ApiModelProperty(value="拍辅项目详情表")
	private ProjectPaifuDetail projectPaifuDetail;

	public void setProjectPaifuDetail(ProjectPaifuDetail projectPaifuDetail) {
		this.projectPaifuDetail = projectPaifuDetail;
		this.setProjectDetail(JSON.toJSONString(projectPaifuDetail));
	}

	public void initProjectPaifuDetail(){
		projectPaifuDetail = JSON.parseObject(this.getProjectDetail(), ProjectPaifuDetail.class );
	}

	public ProjectPaifuDetail getProjectPaifuDetail() {
		if(projectPaifuDetail==null && this.getProjectDetail()!=null){
			projectPaifuDetail = JSON.parseObject(this.getProjectDetail(), ProjectPaifuDetail.class );
		}
		return projectPaifuDetail;
	}
}
