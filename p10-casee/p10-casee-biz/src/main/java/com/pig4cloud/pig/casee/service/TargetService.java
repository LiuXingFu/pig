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

package com.pig4cloud.pig.casee.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.casee.dto.*;
import com.pig4cloud.pig.casee.entity.Target;
import com.pig4cloud.pig.casee.vo.TargetCaseeProjectPageVO;
import com.pig4cloud.pig.casee.vo.TargetPageVO;
import com.pig4cloud.pig.casee.vo.TaskNodeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 标的表
 *
 * @author yy
 * @date 2021-09-15 10:03:23
 */
public interface TargetService extends IService<Target> {

	/**
	 * 分页查询标的列表
	 * @param page
	 * @param targetPageDTO
	 * @return
	 */
	IPage<TargetPageVO> queryPageList(Page page, TargetPageDTO targetPageDTO);

	/**
	 * 新增标的
	 * @return
	 */
	Integer addTarget(TargetThingAddDTO targetAddDTO);

	Boolean updateBusinessData(AuditTargetDTO auditTargetDTO);

	/**
	 * 根据添加程序DTO添加相应程序
	 * @param targetAddDTO
	 * @return
	 * @throws Exception
	 */
	int saveTargetAddDTO(TargetAddDTO targetAddDTO) throws Exception;

	/**
	 * 根据案件id,程序性质查询模板数据
	 * @param caseeId 案件id
	 * @param id 财产或程序id
	 * @return R
	 */
	List<TaskNodeVO> getTarget(Integer projectId,Integer caseeId,Integer procedureNature,Integer id);
}
