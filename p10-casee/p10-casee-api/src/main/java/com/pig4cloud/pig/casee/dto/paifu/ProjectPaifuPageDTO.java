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

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.pig4cloud.pig.casee.entity.paifuentity.detail.ProjectPaifuDetail;
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
public class ProjectPaifuPageDTO {

	/**
	 * 项目状态(1000-在办、2000-暂缓、3000-和解、4000-退出)
	 */
	@ApiModelProperty(value="项目状态(1000-在办、2000-暂缓、3000-和解、4000-退出)")
	private Integer status;

    /**
     * 公司业务案号/执行案号
     */
    @ApiModelProperty(value="公司业务案号/执行案号")
    private String caseeNumber;

    /**
     * 办理人名称
     */
    @ApiModelProperty(value="办理人名称")
    private String userNickName;

    /**
     * 申请人/被执行人
     */
    @ApiModelProperty(value="申请人/被执行人")
    private String subjectName;

	/**
	 * 法官名称
	 */
	@ApiModelProperty(value="法官名称")
	private String judgeName;

	/**
	 * 开始时间
	 */
	@ApiModelProperty(value="开始时间")
	private LocalDate beginDate;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(value="结束时间")
	private LocalDate endDate;

	/**
	 * 年份
	 */
	@ApiModelProperty(value="年份")
	private String year;

	/**
	 * 项目状态(1000-在办、2000-暂缓、3000-和解、4000-退出)
	 */
	@ApiModelProperty(value="项目状态(1000-在办、2000-暂缓、3000-和解、4000-退出)")
	private Integer projectStatus;

	/**
	 * 承办法院
	 */
	@ApiModelProperty(value="承办法院")
	private String courtName;

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
	 * 排序json
	 */
	private String ordersJson;

	/**
	 * 排序列集合
	 */
	private List<OrderItem> orders;
}
