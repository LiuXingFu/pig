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

import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.casee.dto.AssetsReDTO;
import com.pig4cloud.pig.casee.entity.LiquiTransferRecord;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

/**
 * 清收移交记录表
 *
 * @author Mjh
 * @date 2022-04-06 15:21:31
 */
@Data
@TableName("p10_liqui_transfer_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "清收移交记录表")
public class LiquiTransferRecordDetailsVO extends LiquiTransferRecord {

	/**
	 * 机构名称
	 */
	private String insName;

	/**
	 * 网点名称
	 */
	private String outlesName;

    /**
     * 申请人
     */
    private String applicantSubjectName;

    /**
     * 被执行人
     */
    private String executorSubjectName;

    /**
     * 申请提交时间
     */
    private LocalDate applicationSubmissionTime;

	/**
	 * 拍卖申请书
	 */
	private String auctionApplicationFile;


	/**
	 * 移交的财产信息
	 */
	private List<AssetsVO> assetsVOList;



}
