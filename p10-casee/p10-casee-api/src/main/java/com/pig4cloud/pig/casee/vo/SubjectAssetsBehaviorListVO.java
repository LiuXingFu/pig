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

import com.pig4cloud.pig.casee.entity.Behavior;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 债务人信息及财产、行为集合
 *
 * @author yy
 * @date 2021-09-17 16:55:57
 */
@Data
public class SubjectAssetsBehaviorListVO {

	/**
	 * 主体id
	 */
	@ApiModelProperty(value = "主体id")
	private Integer subjectId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 案件id
	 */
	@ApiModelProperty(value = "案件id")
	private Integer caseeId;

	/**
	 *	财产集合
	 */
	@ApiModelProperty(value = "财产集合")
	List<AssetsPageVO> assetsList;

	/**
	 *	行为集合
	 */
	@ApiModelProperty(value = "行为集合")
	List<Behavior> behaviorList;

}
