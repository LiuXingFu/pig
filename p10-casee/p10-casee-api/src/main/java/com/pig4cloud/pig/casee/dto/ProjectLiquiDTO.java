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
package com.pig4cloud.pig.casee.dto;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.detail.CaseeLiQuiDateDetail;
import com.pig4cloud.pig.casee.entity.detail.ProjectLiQuiDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 项目表DTO
 *
 * @author ligt
 * @date 2022-01-10 15:05:49
 */
@Data
@TableName("p10_project")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "清收项目表DTO")
public class ProjectLiquiDTO extends ProjectLiqui {

	// 债务人
	@ApiModelProperty(value="债务人列表")
	private List<ProjectSubject> subjectList;

	// 抵押物
	@ApiModelProperty(value="抵押物列表")
	private List<AssetsLiqui> assetsList;

	//项目机构关联表
	@ApiModelProperty(value="关联网点信息")
	List<ProjectOutlesDealRe> outlesList;

	@ApiModelProperty(value="委托机构")
	private Integer  entrustInsId;


	@ApiModelProperty(value="委托网点")
	private Integer  entrustOutlesId;

}
