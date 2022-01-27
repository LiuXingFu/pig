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
import com.pig4cloud.pig.casee.dto.CaseeBizLiquiDTO;
import com.pig4cloud.pig.casee.dto.CaseeOrTargetTaskFlowDTO;
import com.pig4cloud.pig.casee.dto.CaseeRepaymentAddDTO;
import com.pig4cloud.pig.casee.entity.CaseeBizBase;
import com.pig4cloud.pig.casee.entity.CaseeBizLiqui;
import com.pig4cloud.pig.casee.vo.CaseeBizLiquiVO;
import com.pig4cloud.pig.casee.vo.CaseePersonnelVO;
import com.pig4cloud.pig.common.core.util.R;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

/**
 * 案件表
 *
 * @author yy
 * @date 2021-09-15 10:03:22
 */
public interface CaseeBizLiquiService extends IService<CaseeBizBase>{
	/**
	 * 新增案件
	 * @return
	 */
	CaseeBizLiqui addCasee(CaseeBizLiquiDTO caseeDto) throws Exception;

	/**
	 * 修改案件
	 * @return
	 */
	CaseeBizLiqui updateCasee(CaseeBizLiquiDTO caseeDto);
	/**
	 * 查询清收列表
	 * @param page
	 * @param caseeBizLiquiDTO
	 * @return
	 */
	IPage<CaseeBizLiquiVO> queryLiquidateCaseePage(Page page, CaseeBizLiquiDTO caseeBizLiquiDTO);
	/**
	 * 查询清收列表
	 * @param page
	 * @param caseeBizLiquiDTO
	 * @return
	 */
	IPage<CaseeBizLiquiVO> queryLiquidateCaseeEntrustPage(Page page, CaseeBizLiquiDTO caseeBizLiquiDTO);


	/**
	 * 查询协办网点相关信息
	 * @param caseeId
	 * @return
	 */
	CaseePersonnelVO queryCaseeAssistOutlets(Integer caseeId);

	/**
	 * 查询案件被执行人、担保人列表
	 * @param caseeId
	 * @return
	 */
	CaseePersonnelVO queryCaseeDetails(Integer caseeId);

	/**
	 * 查询案件审核详情信息
	 * @param caseeId 案件id
	 * @return R
	 */
	CaseeBizLiquiVO queryAuditCaseeDetails(Integer caseeId);

	/**
	 * 财产信息修改
	 * @param caseeBizLiquiDTO 财产信息修改
	 * @return R
	 */
	CaseeBizLiqui updateProperty(CaseeBizLiquiDTO caseeBizLiquiDTO);
	/**
	 * 协助网点修改
	 * @param caseeBizLiquiDTO 协助网点修改
	 * @return R
	 */
	Boolean updateOutles(CaseeBizLiquiDTO caseeBizLiquiDTO);


	/**
	 * 修改案件待确认状态(添加消息中心推送)
	 * @param caseeId 案件id
	 * @param status 案件状态：1-开启、2-暂缓、3-中止
	 * @param explain 说明
	 * @return
	 */
	Integer modifyCaseStatus(Integer caseeId,Integer status,String explain);


	/**
	 * 案件结案开启(添加消息中心推送)
	 * @param caseeId 案件id
	 * @param closeTime 结案时间
	 * @param explain 说明
	 * @return
	 */
	Integer caseClosedOpen(Integer caseeId, LocalDateTime closeTime, String explain);

	/**
	 * 委托方案件状态确认(添加消息中心推送)
	 * @param statusConfirm 状态确认：2-已确认，3-已拒绝
	 * @return
	 */
	Integer confirmCaseStatus(CaseeOrTargetTaskFlowDTO caseeOrTargetTaskFlowDTO,Integer statusConfirm);

	Integer addRepaymentDetail(CaseeRepaymentAddDTO caseeRepaymentAddDTO) throws Exception;

}
