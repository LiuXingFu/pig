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
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.casee.dto.*;
import com.pig4cloud.pig.casee.entity.Casee;
import com.pig4cloud.pig.casee.entity.liquientity.CaseeLiqui;
import com.pig4cloud.pig.casee.vo.*;

import java.util.List;

/**
 * 案件表
 *
 * @author yy
 * @date 2021-09-15 10:03:22
 */
public interface CaseeLiquiService extends IService<Casee> {

	/**
	 * 添加基本案件(其它案件)
	 * @param caseeLiquiAddDTO
	 * @return
	 * @throws Exception
	 */
	Integer addCaseeLiqui(CaseeLiquiAddDTO caseeLiquiAddDTO) throws Exception;

	/**
	 * 添加保全案件
	 * @return
	 * @throws Exception
	 */
	Integer addPreservationCasee(CaseeLiquiAddDTO caseeLiquiAddDTO) throws Exception;

	/**
	 * 添加一审诉讼案件
	 * @return
	 * @throws Exception
	 */
	Integer addLawsuitsCasee(CaseeLawsuitsDTO caseeLawsuitsDTO) throws Exception;

	/**
	 * 添加二审诉讼案件
	 * @return
	 * @throws Exception
	 */
	Integer saveSecondInstanceCasee(CaseeSecondInstanceDTO caseeSecondInstanceDTO) throws Exception;

	/**
	 * 添加首执案件
	 * @return
	 * @throws Exception
	 */
	Integer addExecutionCasee(CaseeLiquiAddDTO caseeLiquiAddDTO) throws Exception;

	/**
	 * 添加执恢案件
	 * @return
	 * @throws Exception
	 */
	Integer addReinstatementCasee(CaseeReinstatementDTO caseeReinstatementDTO) throws Exception;

	/**
	 * 根据项目id、案件类型集合查询案件信息
	 * @param projectId
	 * @param caseeType
	 * @return
	 */
	List<CaseeListVO> queryByIdList(Integer projectId,List<Integer> caseeType);

	/**
	 * 根据项目id、案件类型查询一审或首执案件信息
	 * @param projectId
	 * @param caseeType
	 * @return
	 */
	CaseeLiqui getCaseeParentId(Integer projectId,Integer caseeType);

	/**
	 * 根据项目id、案件状态集合查询案件信息
	 * @param projectId
	 * @param statusList
	 * @return
	 */
	CaseeLiqui queryByStatusList(Integer projectId,List<Integer> statusList);

	/**
	 * 更新案件状态
	 * @return
	 */
	Integer modifyCaseeStatusById(CaseeLiquiDTO caseeLiquiDTO);

	Integer actualExecution(CaseeLiquiDTO caseeLiquiDTO);
	/**
	 * 分页查询清收案件
	 * @param page
	 * @param caseeLiquiPageDTO
	 * @return
	 */
	IPage<CaseeLiquiPageVO> queryPage(Page page, CaseeLiquiPageDTO caseeLiquiPageDTO);

	CaseeLiquiDetailsVO queryByCaseeId(Integer caseeId);

	List<Subject> queryCaseeSubjectList(CaseeSubjectDTO caseeSubjectDTO);

	/**
	 * 查询债务人信息及财产和行为集合
	 * @param caseeId
	 * @param caseePersonnelType
	 * @return
	 */
	List<SubjectAssetsBehaviorListVO> queryAssetsBehavior(Integer caseeId,Integer caseePersonnelType);

	/**
	 * 分页查询财产未添加案件
	 * @param page
	 * @param caseeLiquiPageDTO
	 * @return
	 */
	IPage<CaseeLiquiPageVO> queryAssetNotAddedPage(Page page, CaseeLiquiPageDTO caseeLiquiPageDTO);

	/**
	 * 分页查询案件流程图接口
	 * @param page
	 * @param caseeLiquiFlowChartPageDTO
	 * @return
	 */
	IPage<CaseeLiquiFlowChartPageVO> queryFlowChartPage(Page page, CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO);

	/**
	 * 根据案件id查询案件详情
	 * @param caseeId
	 * @return
	 */
	CaseeLiquiDetailsVO queryByCaseeIdDetails(Integer caseeId);

	/**
	 * 诉讼案件自动结案
	 */
	void litigationCaseeClose();

	/**
	 * 更改案件状态为撤案、结案与终极
	 * @param caseeId
	 * @param status
	 */
	void caseeModify(Integer caseeId, Integer status);

	/**
	 * 分页查询上诉到期未确认列表
	 * @param page
	 * @param caseeLiquiFlowChartPageDTO
	 * @return
	 */
	IPage<CaseeLiquiFlowChartPageVO> queryLitigationFirstInstanceAppealExpired(Page page, CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO);

	/**
	 * 分页查询执恢案件待立案列表
	 * @return
	 */
	IPage<CaseeLiquiFlowChartPageVO> queryAddReinstatementCase(Page page, CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO);

	/**
	 * 分页查询法院到款未领款
	 * @param page
	 * @param caseeLiquiFlowChartPageDTO
	 * @return
	 */
	IPage<CaseeLiquiFlowChartPageVO> queryCourtPayment(Page page, CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO);

	/**
	 * 分页查询款项结清未实际执结
	 * @param page
	 * @param caseeLiquiFlowChartPageDTO
	 * @return
	 */
	IPage<CaseeLiquiFlowChartPageVO> queryPaymentCompleted(Page page, CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO);

	/**
	 * 分页查询执行案件债务人未添加行为
	 * @param page
	 * @param caseeLiquiFlowChartPageDTO
	 * @return
	 */
	IPage<CaseeLiquiFlowChartPageVO> queryNotAddBehavior(Page page, CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO);

	/**
	 * 案件债务人未添加财产
	 * @param page
	 * @param caseeLiquiFlowChartPageDTO
	 * @return
	 */
	IPage<CaseeLiquiFlowChartPageVO> caseeSubjectNotAddAssets(Page page, CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO);

	/**
	 * 较去年案件数
	 * @return
	 */
	Long queryCompareTheNumberOfCasesCount();
}
