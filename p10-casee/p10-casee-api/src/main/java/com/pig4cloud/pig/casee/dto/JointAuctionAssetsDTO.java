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


import com.pig4cloud.pig.casee.entity.AssetsRe;
import lombok.Data;

/**
 * 财产表
 *
 * @author ligt
 * @date 2022-01-11 10:29:44
 */
@Data
public class JointAuctionAssetsDTO extends AssetsRe {

	/**
	 * 财产id
	 */
	private Integer assetsId;

	/**
	 * 财产名称
	 */
	private String assetsName;

	/**
	 * 财产性质（资金财产：(20101:银行存款，20102：住房公积金)，实体财产：（20201：房产，20202：车辆，20203：股权，20204：土地，20205：其它））
	 */
	private Integer assetsType;

	/**
	 * 所有权人
	 */
	private String owner;

	/**
	 * 财产账号/编号
	 */
	private String accountNumber;

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
}
