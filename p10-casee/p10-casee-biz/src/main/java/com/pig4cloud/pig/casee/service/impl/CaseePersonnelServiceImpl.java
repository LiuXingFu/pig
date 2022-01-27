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
package com.pig4cloud.pig.casee.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.casee.entity.CaseePersonnel;
import com.pig4cloud.pig.casee.mapper.CaseePersonnelMapper;
import com.pig4cloud.pig.casee.service.CaseePersonnelService;
import com.pig4cloud.pig.casee.vo.CaseePerformanceVO;
import com.pig4cloud.pig.casee.vo.CaseePersonnelTypeVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 案件人员表
 *
 * @author yy
 * @date 2021-09-15 10:03:23
 */
@Service
public class CaseePersonnelServiceImpl extends ServiceImpl<CaseePersonnelMapper, CaseePersonnel> implements CaseePersonnelService {

	@Override
	public List<CaseePersonnelTypeVO> queryByCaseeId(Integer caseeId, Integer type){
		return this.baseMapper.selectByCaseeId(caseeId, type);
	}
}
