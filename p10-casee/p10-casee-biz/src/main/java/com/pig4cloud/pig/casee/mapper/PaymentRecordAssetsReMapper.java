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

package com.pig4cloud.pig.casee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig4cloud.pig.casee.entity.PaymentRecordAssetsRe;
import com.pig4cloud.pig.casee.vo.AssetsReVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 回款记录财产关联表
 *
 * @author pig code generator
 * @date 2022-05-05 21:23:53
 */
@Mapper
public interface PaymentRecordAssetsReMapper extends BaseMapper<PaymentRecordAssetsRe> {

	List<AssetsReVO> selectAssetsList(@Param("paymentRecordId") Integer paymentRecordId);
}
