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
package com.pig4cloud.pig.casee.entity.biz;

import com.alibaba.fastjson.JSON;
import com.pig4cloud.pig.casee.entity.ProjectStatus;
import com.pig4cloud.pig.casee.entity.biz.detail.ProjectStatusBizDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 项目/案件状态详情
 *
 * @author ligt
 * @date 2022-01-18 15:21:05
 */
@Data
public class ProjectStatusBiz extends ProjectStatus {

	@ApiModelProperty(value="案件状态详情")
	private ProjectStatusBizDetail projectStatusBizDetail;

	public void setProjectStatusBizDetail(ProjectStatusBizDetail projectStatusBizDetail) {
		this.projectStatusBizDetail = projectStatusBizDetail;
		this.setStatusDetail(JSON.toJSONString(projectStatusBizDetail));
	}

	public void initProjectStatusBizDetail(){
		projectStatusBizDetail = JSON.parseObject(this.getStatusDetail(), ProjectStatusBizDetail.class );
	}

	public ProjectStatusBizDetail getProjectStatusBizDetail() {
		if(projectStatusBizDetail==null && this.getStatusDetail()!=null){
			projectStatusBizDetail = JSON.parseObject(this.getStatusDetail(), ProjectStatusBizDetail.class );
		}
		return projectStatusBizDetail;
	}
}
