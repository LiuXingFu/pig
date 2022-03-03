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

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.casee.entity.Project;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.liquientity.detail.ProjectLiQuiDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 项目表
 *
 * @author ligt
 * @date 2022-01-10 15:05:49
 */

@Data
public class ProjectLiquiDealtVO {

	@ApiModelProperty(value="清收项目详情表")
	private ProjectLiqui projectLiqui;

	/**
	 * 诉前阶段
	 */
	List<CaseeListVO> prePleadingList;

	/**
	 * 诉讼阶段
	 */
	List<CaseeListVO> litigationList;

	/**
	 * 履行阶段
	 */
	CaseeListVO carryList;

	/**
	 * 执行阶段
	 */
	List<CaseeListVO> executeList;

	/**
	 * 添加案件按钮控制
	 */
	Boolean addCaseeBtn;

	/**
	 * 诉讼情况
	 */
	Integer litigation;

	/**
	 * 法院到款总额
	 */
	BigDecimal sumCourtPayment;
}
