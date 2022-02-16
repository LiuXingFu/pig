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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.casee.entity.Behavior;
import com.pig4cloud.pig.casee.mapper.BehaviorMapper;
import com.pig4cloud.pig.casee.service.BehaviorService;
import com.pig4cloud.pig.casee.vo.BehaviorOrProjectPageVO;
import org.springframework.stereotype.Service;

/**
 * 行为表
 *
 * @author yuanduo
 * @date 2022-02-14 15:51:27
 */
@Service
public class BehaviorServiceImpl extends ServiceImpl<BehaviorMapper, Behavior> implements BehaviorService {

	/**
	 * 根据主体id分页查询行为数据
	 * @param page
	 * @param subjectId
	 * @return
	 */
	@Override
	public IPage<BehaviorOrProjectPageVO> queryPageBehaviorOrProject(Page page, Integer subjectId) {
		return this.baseMapper.queryPageBehaviorOrProject(page, subjectId);
	}
}
