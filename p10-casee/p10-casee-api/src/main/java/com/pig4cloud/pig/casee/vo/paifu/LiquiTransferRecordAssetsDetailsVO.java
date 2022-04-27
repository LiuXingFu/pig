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

import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.casee.vo.LiquiTransferRecordDetailsVO;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * 清收移交记录表
 *
 * @author Mjh
 * @date 2022-04-06 15:21:31
 */
@Data
@TableName("p10_liqui_transfer_record")
@ApiModel(value = "清收移交记录表")
public class LiquiTransferRecordAssetsDetailsVO {

	List<LiquiTransferRecordDetailsVO> liquiTransferRecordDetailsVOList;
}
