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

import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.casee.entity.CaseeBizLiqui;
import com.pig4cloud.pig.casee.entity.CaseeOutlesDealRe;
import com.pig4cloud.pig.casee.entity.CaseePersonnel;
import com.pig4cloud.pig.casee.entity.Project;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 清收项目表
 *
 * @author yy
 * @date 2021-09-15 10:03:22
 */
@Data
@TableName("p10_project")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "清收项目查询DTO")
public class ProjectQueryLiquiDTO extends Project {

	@ApiModelProperty(value="委托机构")
	private Integer  entrustInsId;


	@ApiModelProperty(value="委托网点")
	private Integer  entrustOutlesId;



	private String beginDate;//开始时间

	private String endDate;//结束时间


	@ApiModelProperty(value="当前登录机构")
	private Integer   insId;

	@ApiModelProperty(value="当前登录网点")
	private Integer   outlesId;

	@ApiModelProperty(value = "所属网点Id集合")
	private List<Integer> belongOutlesIds;

	private String sequence;//案收案时间排序

	@ApiModelProperty(value="查询关键字")
	private String keyword;

}
