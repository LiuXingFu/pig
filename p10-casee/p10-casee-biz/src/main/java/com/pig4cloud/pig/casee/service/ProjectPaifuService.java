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
import com.pig4cloud.pig.casee.dto.InsOutlesDTO;
import com.pig4cloud.pig.casee.dto.paifu.*;
import com.pig4cloud.pig.casee.dto.paifu.count.AssetsRePaifuFlowChartPageDTO;
import com.pig4cloud.pig.casee.dto.paifu.excel.ImportPaifuDTO;
import com.pig4cloud.pig.casee.entity.Project;
import com.pig4cloud.pig.casee.entity.paifuentity.ProjectPaifu;
import com.pig4cloud.pig.casee.vo.paifu.*;
import com.pig4cloud.pig.casee.vo.paifu.count.AssetsRePaifuFlowChartPageVO;
import com.pig4cloud.pig.casee.vo.paifu.count.CountFlowChartVO;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;


/**
 * 拍辅项目表
 */
public interface ProjectPaifuService extends IService<Project> {

	Integer saveProjectCasee(ProjectPaifuSaveDTO projectPaifuSaveDTO);

	/**
	 * 分页查询列表
	 * @param page
	 * @param projectPaifuPageDTO
	 * @return
	 */
	IPage<ProjectPaifuPageVO> queryProjectCaseePage(Page page, ProjectPaifuPageDTO projectPaifuPageDTO);

	/**
	 * 查询项目案件详情及债务人等集合
	 * @param projectId
	 * @return
	 */
	ProjectPaifuDetailVO queryProjectCaseeDetailList(Integer projectId);

	/**
	 * 保存项目主体关联表
	 * @return
	 */
	Integer addProjectSubjectRe(ProjectSubjectReSaveDTO projectSubjectReSaveDTO);

	/**
	 * 验证项目主体
	 * @return
	 */
	ProjectSubjectReListVO queryProjectSubjectRe(Integer projectId,String unifiedIdentity);

	/**
	 * 根据项目id修改项目和案件基本信息
	 * @param projectPaifuModifyDTO
	 * @return
	 */
	Integer modifyByProjectId(ProjectPaifuModifyDTO projectPaifuModifyDTO);

	/**
	 * 修改项目主体关联表
	 * @return
	 */
	Integer modifyProjectSubjectRe(ProjectSubjectReSaveDTO projectSubjectReSaveDTO);

	/**
	 * 删除项目主体关联表
	 * @return
	 */
	Integer removeProjectSubjectRe(ProjectSubjectReRemoveDTO projectSubjectReRemoveDTO);

	/**
	 * 保存项目接收
	 * @return
	 */
	Integer saveProjectReceipt(ProjectPaifuReceiptDTO projectPaifuReceiptDTO);

	/**
	 * 查询项目案件详情
	 * @param projectId
	 * @return
	 */
	ProjectPaifuDetailVO queryProjectCaseeDetail(Integer projectId);

	/**
	 * 统计业务流程图
	 * @return
	 */
	CountFlowChartVO countProjectFlowChart();

	/**
	 * 分页查询业务流程图节点列表
	 * @param page
	 * @param assetsRePaifuFlowChartPageDTO
	 * @return
	 */
	IPage<AssetsRePaifuFlowChartPageVO> queryFlowChartPage(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO);

	/**
	 * 分页查询不动产现勘未入户
	 * @param page
	 * @param assetsRePaifuFlowChartPageDTO
	 * @return
	 */
	IPage<AssetsRePaifuFlowChartPageVO> queryRealEstateNotSurveyedPage(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO);

	/**
	 * 公告期未拍卖
	 * @param page
	 * @param assetsRePaifuFlowChartPageDTO
	 * @return
	 */
	IPage<AssetsRePaifuFlowChartPageVO> queryAnnouncementPeriodNotAuctioned(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO);

	/**
	 * 拍卖到期无结果
	 * @param page
	 * @param assetsRePaifuFlowChartPageDTO
	 * @return
	 */
	IPage<AssetsRePaifuFlowChartPageVO> queryAuctionExpiresWithoutResults(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO);

	/**
	 * 拍卖成交未处理
	 * @param page
	 * @param assetsRePaifuFlowChartPageDTO
	 * @return
	 */
	IPage<AssetsRePaifuFlowChartPageVO> queryAuctionTransactionNotProcessed(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO);

	/**
	 * 拍卖异常未撤销
	 * @param page
	 * @param assetsRePaifuFlowChartPageDTO
	 * @return
	 */
	IPage<AssetsRePaifuFlowChartPageVO> queryAuctionExceptionNotCancelled(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO);

	/**
	 * 裁定未送达
	 * @param page
	 * @param assetsRePaifuFlowChartPageDTO
	 * @return
	 */
	IPage<AssetsRePaifuFlowChartPageVO> queryRulingNotService(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO);

	/**
	 * 拍卖不成交未处理
	 * @param page
	 * @param assetsRePaifuFlowChartPageDTO
	 * @return
	 */
	IPage<AssetsRePaifuFlowChartPageVO> queryAuctionTransactionFailedNotProcessed(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO);

	/**
	 * 到款/抵偿未裁定
	 * @param page
	 * @param assetsRePaifuFlowChartPageDTO
	 * @return
	 */
	IPage<AssetsRePaifuFlowChartPageVO> queryArrivalCompensationNotAdjudicated(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO);

	/**
	 * 根据id查询拍辅项目
	 * @param projectId
	 * @return
	 */
	ProjectPaifu queryById(Integer projectId);

	/**
	 * 更新项目总金额
	 * @param projectId
	 * @return
	 */
	Integer updateProjectAmount(Integer projectId);

	/**
	 * 更新项目回款金额
	 * @param projectId
	 * @return
	 */
	Integer updateRepaymentAmount(Integer projectId);

	/**
	 * excel文件导入
	 * @param importPaifuDTO
	 * @return
	 */
	Integer excelImport(ImportPaifuDTO importPaifuDTO);

	/**
	 * excel文件导出
	 * @param projectPaifuPageDTO
	 * @return
	 */
	void projectPaifuExport(HttpServletResponse response, ProjectPaifuPageDTO projectPaifuPageDTO);
}
