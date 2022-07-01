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
import com.pig4cloud.pig.casee.entity.AssetsReSubject;
import com.pig4cloud.pig.casee.mapper.AssetsReSubjectMapper;
import com.pig4cloud.pig.casee.service.AssetsReSubjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 项目案件财产关联主体
 *
 * @author pig code generator
 * @date 2022-04-14 17:09:52
 */
@Service
public class AssetsReSubjectServiceImpl extends ServiceImpl<AssetsReSubjectMapper, AssetsReSubject> implements AssetsReSubjectService {

	@Override
	public List<Integer> queryByAssetsIdList(List<Integer> assetsReIdList){
		return this.baseMapper.selectByAssetsIdList(assetsReIdList);
	}

	@Override
	@Transactional
	public Integer removeByProjectId(Integer projectId,Integer mortgageAssetsRecordsId){
		return this.baseMapper.removeByProjectId(projectId,mortgageAssetsRecordsId);
	}
}
