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
package com.pig4cloud.pig.casee.dto.paifu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * 项目表
 *
 * @author pig code generator
 * @date 2022-02-10 17:30:36
 */
@Data
public class AssetsReModifyStatusDTO {

	/**
	 * 项目id
	 */
	@ApiModelProperty(value="项目id")
	private Integer projectId;

	/**
	 * 案件id
	 */
	@ApiModelProperty(value="案件id")
	private Integer caseeId;

	/**
	 * 财产关联表id
	 */
	@ApiModelProperty(value="财产关联表id")
	private Integer assetsReId;

	/**
	 * 状态（100-在办，200-拍卖中，300-暂缓，400-中止，500-已完成，600-其它，700-移送中）
	 */
	@ApiModelProperty(value="状态（100-在办，200-拍卖中，300-暂缓，400-中止，500-已完成，600-其它，700-移送中）")
	private Integer status;

	/**
	 * 状态名称
	 */
	@ApiModelProperty(value="状态名称")
	private String statusName;

	/**
	 * 变更时间
	 */
	@ApiModelProperty(value="变更时间")
	private LocalDate changeTime;

	/**
	 * 描述
	 */
	@ApiModelProperty(value="描述")
	private String describes;

	/**
	 * 附件
	 */
	@ApiModelProperty(value="附件")
	private String appendix;

}
