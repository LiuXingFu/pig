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
package com.pig4cloud.pig.casee.entity.liquientity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.casee.entity.Project;
import com.pig4cloud.pig.casee.entity.liquientity.detail.ProjectLiQuiDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;

/**
 * 项目表
 *
 * @author ligt
 * @date 2022-01-10 15:05:49
 */

@TableName("p10_project")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "清收项目表")
public class ProjectLiqui extends Project {

	@ApiModelProperty(value="清收项目详情表")
	private ProjectLiQuiDetail projectLiQuiDetail;

	public void setProjectLiQuiDetail(ProjectLiQuiDetail projectLiQuiDetail) {
		this.projectLiQuiDetail = projectLiQuiDetail;
		this.setProjectDetail(JSON.toJSONString(projectLiQuiDetail));
	}

	public void initProjectLiQuiDetail(){
		projectLiQuiDetail = JSON.parseObject(this.getProjectDetail(), ProjectLiQuiDetail.class );
	}

	public ProjectLiQuiDetail getProjectLiQuiDetail() {
		if(projectLiQuiDetail==null && this.getProjectDetail()!=null){
			projectLiQuiDetail = JSON.parseObject(this.getProjectDetail(), ProjectLiQuiDetail.class );
		}
		return projectLiQuiDetail;
	}
}
