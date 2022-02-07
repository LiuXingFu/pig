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


import com.pig4cloud.pig.casee.entity.TransferRecord;
import lombok.Data;


/**
 * 移送记录VO
 *
 * @author Mjh
 * @date 2022-01-28 18:52:40
 */
@Data
public class TransferRecordVO extends TransferRecord {

	/**
	 * 移送机构
	 */
	private String entrustedInsName;

	/**
	 * 移送网点
	 */
	private String entrustedOutlesName;

}
