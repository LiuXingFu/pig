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

import com.pig4cloud.pig.casee.entity.ExpenseRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 费用产生记录拍辅保存dto
 *
 * @author Mjh
 * @date 2022-02-17 17:53:07
 */
@Data
public class ExpenseRecordPaifuAssetsReListVO extends ExpenseRecord {

	/**
	 * 关联财产id集合
	 */
	@ApiModelProperty(value="关联财产id集合")
	private List<Integer> assetsReIdList;

}
