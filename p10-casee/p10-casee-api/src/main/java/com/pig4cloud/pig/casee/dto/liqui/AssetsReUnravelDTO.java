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
package com.pig4cloud.pig.casee.dto.liqui;


import com.pig4cloud.pig.casee.entity.liquientity.detail.detailentity.AssetsReUnravel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 财产表
 *
 * @author ligt
 * @date 2022-01-11 10:29:44
 */
@Data
public class AssetsReUnravelDTO extends AssetsReUnravel {

	/**
	 * 财产id
	 */
	private Integer assetsReId;

	/**
	 * 状态（100-在办，200-拍卖中，300-暂缓，400-中止，500-已完成，600-其它，700-移送中）
	 */
	@ApiModelProperty(value="状态（100-在办，200-拍卖中，300-暂缓，400-中止，500-已完成，600-其它，700-移送中，800解除）")
	private Integer status;

}
