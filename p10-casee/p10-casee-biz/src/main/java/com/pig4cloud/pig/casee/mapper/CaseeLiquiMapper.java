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

package com.pig4cloud.pig.casee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.casee.dto.*;
import com.pig4cloud.pig.casee.entity.Casee;
import com.pig4cloud.pig.casee.entity.liquientity.CaseeLiqui;
import com.pig4cloud.pig.casee.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 案件表
 *
 * @author yy
 * @date 2022-01-10 14:51:59
 */
@Mapper
public interface CaseeLiquiMapper extends BaseMapper<Casee> {

	List<CaseeListVO> selectByIdList(@Param("projectId") Integer projectId,@Param("caseeType")List<Integer> caseeType);

	CaseeLiqui getCaseeParentId(@Param("projectId") Integer projectId,@Param("caseeType")Integer caseeType);

	CaseeLiqui selectByStatusList(@Param("projectId") Integer projectId,@Param("status")Integer status);

	IPage<CaseeLiquiPageVO> selectPage(Page page, @Param("query")CaseeLiquiPageDTO caseeLiquiPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	CaseeLiquiDetailsVO queryByCaseeId(Integer caseeId);

	List<CaseeOrSubjectVO> selectCaseeOrSubject(@Param("query") CaseeOrSubjectDTO caseeOrSubjectDTO);

	List<Subject> selectCaseeSubject(@Param("query")CaseeSubjectDTO caseeSubjectDTO);

	List<SubjectAssetsBehaviorListVO> selectAssetsBehavior(@Param("caseeId") Integer caseeId,@Param("caseePersonnelType") Integer caseePersonnelType);

	IPage<CaseeLiquiPageVO> selectAssetNotAddedPage(Page page, @Param("query")CaseeLiquiPageDTO caseeLiquiPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	IPage<CaseeLiquiFlowChartPageVO> selectFlowChartPage(Page page, @Param("query")CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	/**
	 * 查询在办一审、二审、其它案件裁判结果生效日期
	 * @return
	 */
	List<Casee> selectJudgmentTakesEffect();

	IPage<CaseeLiquiFlowChartPageVO> selectLitigationFirstInstanceAppealExpired(Page page, @Param("query")CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	IPage<CaseeLiquiFlowChartPageVO> selectAddReinstatementCase(Page page, @Param("query")CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	IPage<CaseeLiquiFlowChartPageVO> selectCourtPayment(Page page, @Param("query")CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	IPage<CaseeLiquiFlowChartPageVO> selectPaymentCompleted(Page page, @Param("query")CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	IPage<CaseeLiquiFlowChartPageVO> selectNotAddBehavior(Page page, @Param("query")CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	IPage<CaseeLiquiFlowChartPageVO> caseeSubjectNotAddAssets(Page page, @Param("query")CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	IPage<CaseeLiquiFlowChartPageVO> selectPropertyPreservationCompleted(Page page, @Param("query")CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	Long queryCompareTheNumberOfCasesCount(@Param("insId") Integer insId, @Param("outlesId") Integer outlesId);

	CaseeLiquiPageVO selectCaseeDetails(@Param("caseeId") Integer caseeId);

	Map<String, BigDecimal> getCaseeMap(@Param("polylineActive") Integer polylineActive, @Param("differenceList") List<String> differenceList, @Param("insId") Integer insId, @Param("outlesId") Integer outlesId);

	CaseeLiqui getCaseeLiqui(@Param("query") Casee casee);

	CaseeLiqui selectLastCasee(@Param("projectId") Integer projectId);

	IPage<QueryCaseeLiquiPageVO> queryCaseeLiquiPage(Page page, @Param("query") QueryCaseeLiquiPageDTO queryCaseeLiquiPageDTO);
}
