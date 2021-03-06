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
package com.pig4cloud.pig.casee.vo.paifu;

import com.pig4cloud.pig.casee.entity.paifuentity.ProjectPaifu;
import com.pig4cloud.pig.casee.vo.CaseeVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * 项目表
 *
 * @author pig code generator
 * @date 2022-02-10 17:30:36
 */
@Data
public class ProjectPaifuDetailVO extends ProjectPaifu {

	/**
	 * 案件id
	 */
	@ApiModelProperty(value="案件id")
	private Integer caseeId;

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
	 * 案号
	 */
	@ApiModelProperty(value="案号")
	private String caseeNumber;

	/**
	 * 立案日期
	 */
	@ApiModelProperty(value="立案日期")
	private LocalDate startTime;

	/**
	 * 法院id
	 */
	@ApiModelProperty(value="法院id")
	private Integer courtId;

	/**
	 * 法院名称
	 */
	@ApiModelProperty(value="法院名称")
	private String courtName;

	/**
	 * 法官名称
	 */
	@ApiModelProperty(value="法官名称")
	private String judgeName;

	/**
	 * 申请人列表
	 */
	@ApiModelProperty(value="申请人列表")
	private List<ProjectSubjectReListVO> applicantList;

	/**
	 * 被执行人列表
	 */
	@ApiModelProperty(value="被执行人列表")
	private List<ProjectSubjectReListVO> executedList;

	/**
	 * 标的物列表
	 */
	@ApiModelProperty(value="标的物列表")
	private String assetsList;

	/**
	 * 案件集合
	 */
	@ApiModelProperty(value="案件集合")
	private List<CaseeVO> caseeList;
}
