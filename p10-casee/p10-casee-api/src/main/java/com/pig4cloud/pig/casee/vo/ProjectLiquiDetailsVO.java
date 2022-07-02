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

import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 项目表
 *
 * @author ligt
 * @date 2022-01-10 15:05:49
 */
@Data
public class ProjectLiquiDetailsVO extends ProjectLiqui {

	/**
	 * 机构名称
	 */
	@ApiModelProperty(value="机构名称")
	private String insName;

	/**
	 * 网点名称
	 */
	@ApiModelProperty(value="网点名称")
	private String outlesName;

	/**
	 * 银行借贷和移交信息
	 */
	@ApiModelProperty(value="银行借贷和移交信息")
	TransferRecordBankLoanVO transferRecordBankLoanVO;

	/**
	 * 债务人集合
	 */
	@ApiModelProperty(value="债务人集合")
	List<ProjectSubjectVO> projectSubjectVOList;

	@ApiModelProperty(value="诉讼情况(0-未诉讼 1-已诉讼)")
	private Integer litigation;
}
