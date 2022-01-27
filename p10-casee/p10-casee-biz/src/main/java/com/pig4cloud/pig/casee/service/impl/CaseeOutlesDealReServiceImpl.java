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
import com.pig4cloud.pig.casee.entity.CaseeOutlesDealRe;
import com.pig4cloud.pig.casee.mapper.CaseeOutlesDealReMapper;
import com.pig4cloud.pig.casee.service.CaseeOutlesDealReService;
import com.pig4cloud.pig.casee.vo.CaseeOutlesDealReVo;
import org.springframework.stereotype.Service;



import java.util.List;

/**
 * 案件机构关联表
 *
 * @author yy
 *
 * @date 2021-09-15 10:03:23
 */
@Service
public class CaseeOutlesDealReServiceImpl extends ServiceImpl<CaseeOutlesDealReMapper, CaseeOutlesDealRe> implements CaseeOutlesDealReService {

	@Override
	public List<CaseeOutlesDealReVo> queryCaseeOutlesDealRe(Integer caseeId,Integer insId,Integer outlesId,Integer type){
		return this.baseMapper.queryCaseeOutlesDealRe(caseeId,insId,outlesId,type);
	}
}
