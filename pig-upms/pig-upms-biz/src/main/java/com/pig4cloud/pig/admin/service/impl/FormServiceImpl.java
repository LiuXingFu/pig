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

import com.alibaba.csp.sentinel.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.pig4cloud.pig.admin.api.entity.Form;
import  com.pig4cloud.pig.admin.mapper.FormMapper;
import  com.pig4cloud.pig.admin.service.FormService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 表单表
 *
 * @author yy
 * @date 2021-09-06 16:49:33
 */
@Service
public class FormServiceImpl extends ServiceImpl<FormMapper, Form> implements FormService {

	@Override
	public IPage<Form> pageSearch(Page<Form> page, Form form) {
		QueryWrapper<Form> queryWrapper = Wrappers.query(form);
		if(StringUtil.isNotEmpty(form.getName())){
			queryWrapper.like("name", form.getName());
		}


		form.setName(null);
		return this.getBaseMapper().selectPage(page, queryWrapper);
	}


	/**
	 * 通过id集合查询表单集合
	 * @param ids
	 * @return
	 */
	@Override
	public List<Form> getByIds(List<Integer> ids) {
		return this.listByIds(ids);
	}
}
