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
import com.pig4cloud.pig.casee.entity.CaseeBizBase;
import com.pig4cloud.pig.casee.entity.CaseeOutlesDealRe;
import com.pig4cloud.pig.casee.entity.TaskNode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件表
 *
 * @author yy
 * @date 2021-09-15 10:03:22
 */
public interface CaseeBizService extends IService<CaseeBizBase> {


	/**
	 * 修改案件待确认状态
	 * @param caseeId 案件id
	 * @param status 案件状态：1-开启、2-暂缓、3-中止
	 * @return
	 */
	Integer modifyCaseStatus(Integer caseeId,Integer status);


	/**
	 * 案件结案开启
	 * @param caseeId 案件id
	 * @param closeTime 结案时间
	 * @return
	 */
	Integer caseClosedOpen(Integer caseeId, LocalDateTime closeTime);

	/**
	 * 委托方案件状态确认
	 * @param statusConfirm 状态确认：2-已确认，3-已拒绝
	 * @return
	 */
	Integer confirmCaseStatus(CaseeOrTargetTaskFlowDTO caseeOrTargetTaskFlowDTO, Integer statusConfirm);

	/**
	 * 消息推送公用接口
	 * @param caseeId 案件id
	 * @param targetId 标的id
	 * @param statusName 状态名称
	 * @param closeTime 结案日期
	 * @param explain 说明
	 */
	List<CaseeOutlesDealRe> messagePush(Integer caseeId, Integer targetId, String statusName, LocalDateTime closeTime, String explain);


	/**
	 * 启动案件或标的任务流完成审核申请人任务设置审核人
	 * @param taskNode 任务信息
	 * @param caseeOutlesDealReList 案件机构关联信息
	 * @param explain 说明
	 * @return
	 */
	void caseeOrTagetFlowStart(TaskNode taskNode, List<CaseeOutlesDealRe> caseeOutlesDealReList,String explain);


}
