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
import com.pig4cloud.pig.casee.entity.Project;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.vo.*;
import com.pig4cloud.pig.casee.vo.count.*;

import java.util.List;

/**
 * 清收项目表
 *
 * @author ligt
 * @date 2022-01-10 15:05:49
 */
public interface ProjectLiquiService extends IService<Project> {

	IPage<ProjectLiquiPageVO> queryPageLiqui(Page page, ProjectLiquiPageDTO projectLiquiPageDTO);

	Integer addProjectLiqui(ProjectLiquiAddDTO projectLiquiAddVO);

	ProjectLiquiDetailsVO getProjectDetails(Integer projectId);

	Integer modifyStatusById(ProjectModifyStatusDTO projectModifyStatusDTO);

	ProjectLiqui getByProjectId(Integer projectId);

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
}
