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
import com.pig4cloud.pig.casee.dto.count.CountLineChartColumnarChartDTO;
import com.pig4cloud.pig.casee.dto.count.CountPolylineLineChartDTO;
import com.pig4cloud.pig.casee.dto.count.ExpirationReminderDTO;
import com.pig4cloud.pig.casee.entity.Project;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.vo.*;
import com.pig4cloud.pig.casee.vo.count.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 清收项目表
 *
 * @author ligt
 * @date 2022-01-10 15:05:49
 */
public interface ProjectLiquiService extends IService<Project> {

	IPage<ProjectLiquiPageVO> queryPageLiqui(Page page, ProjectLiquiPageDTO projectLiquiPageDTO);

	/**
	 * 统计项目金额合计
	 * @param projectLiquiPageDTO
	 * @return
	 */
	ProjectLiquiSumAmountDTO getProjectSumAmount(ProjectLiquiPageDTO projectLiquiPageDTO);

	Integer addProjectLiqui(ProjectLiquiAddDTO projectLiquiAddVO);

	ProjectLiquiDetailsVO getProjectDetails(Integer projectId);

	Integer modifyStatusById(ProjectModifyStatusDTO projectModifyStatusDTO);

	ProjectLiqui getByProjectId(Integer projectId);

	/**
	 * 添加项目总金额
	 * @param projectLiqui
	 * @return
	 */
	boolean addProjectAmount(ProjectLiqui projectLiqui, BigDecimal amount);

	/**
	 * 扣除项目总金额
	 * @param projectLiqui
	 * @return
	 */
	boolean subtractProjectAmount(ProjectLiqui projectLiqui,BigDecimal amount);

	/**
	 * 添加项目回款总金额
	 * @param projectLiqui
	 * @return
	 */
	boolean addRepaymentAmount(ProjectLiqui projectLiqui, BigDecimal amount);

	/**
	 * 扣除项目回款总金额
	 * @param projectLiqui
	 * @return
	 */
	boolean subtractRepaymentAmount(ProjectLiqui projectLiqui,BigDecimal amount);

	IPage<ProjectLiquiOrBehaviorPageVO> queryPageProjectLiqui(Page page, Integer subjectId);

	List<ProjectSubjectVO> queryProjectSubjectList(ProjectSubjectDTO projectSubjectDTO);

	/**
	 * 通过项目id查询办理信息
	 * @param projectId
	 * @return
	 */
	ProjectLiquiDealtVO queryDealt(Integer projectId);

	List<SubjectAssetsBehaviorListVO> queryAssetsBehavior(Integer projectId,Integer caseePersonnelType);

	/*******************************************************/
	// 接收未处理分页查询
	IPage<ProjectLiquiPageVO> queryNotProcessedPage(Page page, ProjectNoProcessedDTO projectNoProcessedDTO);

	// 项目接收处理统计接口
	CountProjectStatisticsVO countProject();

	// 诉前阶段统计接口
	CountPreLitigationStageVO countPreLitigationStage();

	// 诉讼阶段统计接口
	CountLitigationVO countlitigation();

	// 履行阶段统计接口
	CountFulfillVO countFulfill();

	// 执行阶段统计接口
	CountImplementVO countImplement();

	// 统计债务人统计接口
	CountDebtorVO countDebtor();

	// 财产查控统计接口
	CountPropertySearchVO countPropertySearch();

	// 可处置财产统计接口
	CountAuctionPropertyVO countAuctionProperty();

	//首页项目、事项统计接口
	CountProjectMattersVO countProjectMatters();

	//年比较去年相关数量如：项目、回款额、案件数和财产数
	CountCompareQuantityVO countCompareQuantity();

	//本月回款额月排名、回款总额、财产类型数量、财产总数量和提醒事项集合
	CountMoneyBackMonthlyRankVO countMoneyBackMonthlyRank();

	/**
	 * 查询项目案件折线图
	 * @param countPolylineLineChartDTO
	 * @return
	 */
	CountPolylineLineChartVO countPolylineLineChart(CountPolylineLineChartDTO countPolylineLineChartDTO);

	/**
	 * 履行阶段首执待立案
	 * @param page
	 * @param projectNoProcessedDTO
	 * @return
	 */
	IPage<ProjectLiquiPageVO> queryFulfillFirstExecutionPending(Page page, ProjectNoProcessedDTO projectNoProcessedDTO);

	/**
	 * 回款额折线柱状图
	 * @param countLineChartColumnarChartDTO
	 * @return
	 */
	CountLineChartColumnarChartVO countLineChartColumnarChart(CountLineChartColumnarChartDTO countLineChartColumnarChartDTO);


	/**
	 * 提醒事项
	 * @param page
	 * @param expirationReminderDTO
	 * @return
	 */
	IPage<ExpirationReminderVO> queryStatisticsReminder(Page page, ExpirationReminderDTO expirationReminderDTO);

	/**
	 * 更新项目总金额
	 * @param projectId
	 * @return
	 */
	Integer modifyProjectAmount(Integer projectId);

	/**
	 * 添加项目
	 * @param projectLiquiSaveDTO
	 * @return
	 */
	Integer saveProject(ProjectLiquiSaveDTO projectLiquiSaveDTO);

	/**
	 * 根据项目id更新项目基本信息
	 * @param projectLiquiModifyDTO
	 * @return
	 */
	Integer modifyProjectById(ProjectLiquiModifyDTO projectLiquiModifyDTO);

	/**
	 * 修改项目银行借贷信息
	 * @param projectLiquiModifyBankLoanDTO
	 * @return
	 */
	Integer modifyProjectBankLoan(ProjectLiquiModifyBankLoanDTO projectLiquiModifyBankLoanDTO);

	/**
	 * 根据项目id查询项目详细信息
	 * @param projectId 项目id
	 * @return
	 */
	ProjectLiQuiAndSubjectListVO selectProjectLiquiAndSubject(Integer projectId);
	/**
	 * 更新项目抵押财产
	 * @return
	 */
	Integer modifyProjectMortgagedProperty(ProjectLiquiModifyMortgagedPropertyDTO projectLiquiModifyMortgagedPropertyDTO);



}
