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
import com.pig4cloud.pig.casee.entity.CaseeSubjectRe;
import com.pig4cloud.pig.casee.mapper.CaseeSubjectReMapper;
import com.pig4cloud.pig.casee.service.CaseeSubjectReService;
import com.pig4cloud.pig.casee.vo.SubjectOptionVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 案件主体关联案件表
 *
 * @author yy
 * @date 2022-01-10 15:13:16
 */
@Service
public class CaseeSubjectReServiceImpl extends ServiceImpl<CaseeSubjectReMapper, CaseeSubjectRe> implements CaseeSubjectReService {

	@Override
	public List<SubjectOptionVO> getByCaseeId(Integer caseeId, Integer type) {
		return this.baseMapper.getByCaseeId(caseeId, type);
	}
}
