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
package com.pig4cloud.pig.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.InstitutionSubjectRe;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.mapper.InstitutionSubjectReMapper;
import com.pig4cloud.pig.admin.service.InstitutionSubjectReService;
import org.springframework.stereotype.Service;

/**
 * 机构主体关联关系
 *
 * @author yuanduo
 * @date 2022-02-25 00:26:06
 */
@Service
public class InstitutionSubjectReServiceImpl extends ServiceImpl<InstitutionSubjectReMapper, InstitutionSubjectRe> implements InstitutionSubjectReService {

	/**
	 * 根据机构id查询主体信息
	 * @param insId
	 * @return
	 */
	@Override
	public Subject getSubjectByInsId(Integer insId) {
		return this.baseMapper.getSubjectByInsId(insId);
	}
}
