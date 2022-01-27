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


import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.casee.dto.CaseeOrTargetTaskFlowDTO;
import com.pig4cloud.pig.casee.dto.TargetAddDTO;
import com.pig4cloud.pig.casee.dto.TargetCloseTimeDTO;
import com.pig4cloud.pig.casee.dto.TargetThingAddDTO;

import com.pig4cloud.pig.casee.entity.TargetBizLiqui;
import com.pig4cloud.pig.casee.vo.TargetByIdVO;
import com.pig4cloud.pig.casee.vo.TargetThingByIdVO;

import java.time.LocalDateTime;


/**
 * 标的表
 *
 * @author yy
 * @date 2021-09-15 10:03:23
 */
public interface TargetBizLiquiService extends IService<TargetBizLiqui> {



//	/**
//	 * 新增标的
//	 * @return
//	 */
//	Integer addTarget(TargetAddDTO targetAddDTO);
//	/**
//	 * 新增标的物
//	 * @return
//	 */
//	Integer addTargetThing(TargetThingAddDTO targetThingAddDTO);
//
//	/**
//	 * 根据标的ID查询标的详情
//	 * @param targetId
//	 * @return
//	 */
//	TargetBizLiqui queryById(Integer targetId);
//
//	/**
//	 * 根据标的ID查询标的物详情
//	 * @param targetId
//	 * @return
//	 */
//	TargetThingByIdVO queryTargetThingById(Integer targetId);
//
//	/**
//	 * 修改标的和标的物状态
//	 * @param targetId 标的id
//	 * @param status 状态（101：毁损，102：灭失）
//	 * @param explain 说明
//	 * @return
//	 */
//	Integer modifyStatusById(Integer targetId, Integer status,String explain);
//
//	/**
//	 * 完成标的和标的物
//	 * @param targetCloseTimeDTO
//	 * @return
//	 */
//	Integer closeTargetById(TargetCloseTimeDTO targetCloseTimeDTO);
//
//	/**
//	 * 根据标的ID查询标的详情
//	 * @param targetId
//	 * @return
//	 */
//	TargetByIdVO queryTargetById(Integer targetId);
//
//	/**
//	 * 委托方标的状态确认(添加消息中心推送)
//	 * @param caseeOrTargetTaskFlowDTO
//	 * @param statusConfirm 状态确认：2-已确认，3-已拒绝
//	 * @return
//	 */
//	Integer confirmCaseStatus(CaseeOrTargetTaskFlowDTO caseeOrTargetTaskFlowDTO, Integer statusConfirm);
//
//	/**
//	 * 新增标的和标的物损毁、灭失任务
//	 * @return
//	 */
//	void addTargetThingTask(TargetBizLiqui targetBizLiqui);
//
//	void configurationNodeTemplate(TargetBizLiqui targetBizLiqui,Integer templateId);

}
