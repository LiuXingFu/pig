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
import com.pig4cloud.pig.casee.dto.InsOutlesDTO;
import com.pig4cloud.pig.casee.dto.paifu.ProjectPaifuPageDTO;
import com.pig4cloud.pig.casee.dto.paifu.count.AssetsRePaifuFlowChartPageDTO;
import com.pig4cloud.pig.casee.entity.Project;
import com.pig4cloud.pig.casee.entity.paifuentity.ProjectPaifu;
import com.pig4cloud.pig.casee.vo.paifu.ProjectPaifuDetailVO;
import com.pig4cloud.pig.casee.vo.paifu.ProjectPaifuExportVO;
import com.pig4cloud.pig.casee.vo.paifu.ProjectPaifuPageVO;
import com.pig4cloud.pig.casee.vo.paifu.ProjectSubjectReListVO;
import com.pig4cloud.pig.casee.vo.paifu.count.AssetsRePaifuFlowChartPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 拍辅项目表
 *
 * @author ligt
 * @date 2022-01-10 15:05:49
 */
@Mapper
public interface ProjectPaifuMapper extends BaseMapper<Project> {

	IPage<ProjectPaifuPageVO> selectPagePaifu(Page page, @Param("query") ProjectPaifuPageDTO projectPaifuPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	ProjectPaifuDetailVO selectByProjectId(@Param("projectId") Integer projectId);

	List<ProjectSubjectReListVO> selectProjectSubjectReList(@Param("projectId") Integer projectId,@Param("type") Integer type);

	ProjectSubjectReListVO selectProjectSubjectRe(@Param("projectId") Integer projectId,@Param("unifiedIdentity") String unifiedIdentity);

	IPage<AssetsRePaifuFlowChartPageVO> queryFlowChartPage(Page page, @Param("query") AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	IPage<AssetsRePaifuFlowChartPageVO> queryRealEstateNotSurveyedPage(Page page, @Param("query") AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	IPage<AssetsRePaifuFlowChartPageVO> queryAnnouncementPeriodNotAuctioned(Page page, @Param("query") AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	IPage<AssetsRePaifuFlowChartPageVO> queryAuctionExpiresWithoutResults(Page page, @Param("query") AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	IPage<AssetsRePaifuFlowChartPageVO> queryAuctionTransactionNotProcessed(Page page, @Param("query") AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	IPage<AssetsRePaifuFlowChartPageVO> queryAuctionExceptionNotCancelled(Page page, @Param("query") AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	IPage<AssetsRePaifuFlowChartPageVO> queryRulingNotService(Page page, @Param("query") AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	IPage<AssetsRePaifuFlowChartPageVO> queryAuctionTransactionFailedNotProcessed(Page page, @Param("query") AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	IPage<AssetsRePaifuFlowChartPageVO> queryArrivalCompensationNotAdjudicated(Page page, @Param("query") AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	ProjectPaifu getByPorjectId(@Param("projectId") Integer projectId);

	List<ProjectPaifuExportVO> projectPaifuExport(@Param("query") ProjectPaifuPageDTO projectPaifuPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);
}
