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


import com.pig4cloud.pig.casee.entity.Assets;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 财产表
 *
 * @author ligt
 * @date 2022-01-11 10:29:44
 */
@Data
public class AssetsDTO extends Assets {

//	/**
//	 * 地址id
//	 */
//	private Integer addressAsId;

	/**
	 * 标记的抵押财产关联id
	 */
	private Integer mortgageAssetsReId;

	/**
	 * 省
	 */
	private String province;
	/**
	 * 市
	 */
	private String city;
	/**
	 * 区
	 */
	private String area;
	/**
	 * 信息地址
	 */
	private String informationAddress;
	/**
	 * 行政区划编号
	 */
	private String code;

	/**
	 * 地址来源（比如裁判文书网）
	 */
	private String source;

	/**
	 * 财产性质名称
	 */
	private String assetsTypeName;

	/**
	 * 债务人id
	 */
	private List<Integer> subjectId;

	/**
	 * 财产关联所有债务人名称
	 */
	private String subjectName;

	/**
	 * 身份证集合
	 */
	private List<String> unifiedIdentityList;
}
