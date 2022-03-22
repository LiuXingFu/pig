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
package com.pig4cloud.pig.casee.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.casee.dto.*;
import com.pig4cloud.pig.casee.entity.Assets;
import com.pig4cloud.pig.casee.entity.AssetsRe;
import com.pig4cloud.pig.casee.entity.Project;
import com.pig4cloud.pig.casee.entity.assets.AssetsReLiqui;
import com.pig4cloud.pig.casee.mapper.AssetsReLiquiMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.*;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 财产关联表
 *
 * @author ligt
 * @date 2022-01-19 15:19:24
 */
@Service
public class AssetsReLiquiServiceImpl extends ServiceImpl<AssetsReLiquiMapper, AssetsRe> implements AssetsReLiquiService {

	@Autowired
	AssetsService assetsService;
	@Autowired
	private ProjectLiquiService projectLiquiService;
	@Autowired
	private TargetService targetService;
	@Autowired
	private JurisdictionUtilsService jurisdictionUtilsService;

	@Override
	@Transactional
	public Integer saveAssetsCasee(AssetsAddDTO assetsAddDTO)throws Exception{
		Integer assetsId = assetsAddDTO.getAssetsId();
		// 财产不存在保存财产信息及相关联信息
		if(Objects.isNull(assetsId)){
			Assets assets = new Assets();
			BeanCopyUtil.copyBean(assetsAddDTO,assets);
			assetsService.save(assets);
			assetsId = assets.getAssetsId();
			assetsAddDTO.setAssetsId(assets.getAssetsId());
			// 保存财产地址信息
			if(Objects.nonNull(assetsAddDTO.getCode())){
				Address address = new Address();
				// 4=财产地址
				address.setType(4);
				address.setUserId(assets.getAssetsId());
				BeanCopyUtil.copyBean(assetsAddDTO,address);
			}
		}
		AssetsReLiqui assetsReLiqui = new AssetsReLiqui();
		// 财产来源2=案件
		assetsReLiqui.setAssetsSource(2);
		assetsReLiqui.setCreateCaseeId(assetsAddDTO.getCaseeId());
		BeanCopyUtil.copyBean(assetsAddDTO, assetsReLiqui);

		//添加任务数据以及程序信息
		Project project = projectLiquiService.getById(assetsAddDTO.getProjectId());
		TargetAddDTO targetAddDTO=new TargetAddDTO();
		if (assetsAddDTO.getType()==20100){//资金财产
			targetAddDTO.setProcedureNature(4041);
		}else if (assetsAddDTO.getType()==20200){//实体财产
			targetAddDTO.setProcedureNature(4040);
		}
		targetAddDTO.setCaseeId(assetsAddDTO.getCaseeId());
		targetAddDTO.setOutlesId(project.getOutlesId());
		targetAddDTO.setProjectId(assetsAddDTO.getProjectId());
		targetAddDTO.setGoalType(20001);
		targetAddDTO.setGoalId(assetsId);
		targetService.saveTargetAddDTO(targetAddDTO);
		return this.baseMapper.insert(assetsReLiqui);
	}

	@Override
	public AssetsReLiqui getAssetsReCasee(AssetsRe assetsRe) {
		return this.baseMapper.getAssetsReCasee(assetsRe);
	}


	@Override
	public Subject queryAssetsSubject(Integer projectId, Integer caseeId, Integer assetsId) {
		return this.baseMapper.queryAssetsSubject(projectId,caseeId,assetsId);
	}

	@Override
	public AssetsReLiqui queryAssetsMortgage(Integer projectId, Integer caseeId, Integer assetsId) {
		return this.baseMapper.queryAssetsMortgage(projectId,caseeId,assetsId);
	}

	@Override
	public IPage<AssetsReLiquiFlowChartPageVO> queryAssetsNotSeizeAndFreeze(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectAssetsNotSeizeAndFreeze(page,assetsReLiquiFlowChartPageDTO,insOutlesDTO);
	}

	@Override
	public 	IPage<AssetsReLiquiFlowChartPageVO> queryBusinessTransfer(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectBusinessTransfer(page,assetsReLiquiFlowChartPageDTO,insOutlesDTO);
	}

	@Override
	public IPage<AssetsReLiquiFlowChartPageVO> queryFundDeduction(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectFundDeduction(page,assetsReLiquiFlowChartPageDTO,insOutlesDTO);
	}

	@Override
	public 	IPage<AssetsReLiquiFlowChartPageVO> queryPropertyToBeAuctioned(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectPropertyToBeAuctioned(page,assetsReLiquiFlowChartPageDTO,insOutlesDTO);
	}

	@Override
	public 	IPage<AssetsReLiquiFlowChartPageVO> queryPropertyFlowChartPage(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectPropertyFlowChartPage(page,assetsReLiquiFlowChartPageDTO,insOutlesDTO);
	}

	@Override
	public Long queryComparePropertyNumbersCount() {
		return this.baseMapper.queryComparePropertyNumbersCount(jurisdictionUtilsService.queryByInsId("PLAT_"), jurisdictionUtilsService.queryByOutlesId("PLAT_"));
	}

	/**
	 * 财产分类统计集合
	 * @return
	 */
	@Override
	public List<PropertyCategoryTotalVO> queryPropertyCategoryTotalList() {
		return this.baseMapper.queryPropertyCategoryTotalList(jurisdictionUtilsService.queryByInsId("PLAT_"), jurisdictionUtilsService.queryByOutlesId("PLAT_"));
	}

	/**
	 * 财产总数量
	 * @return
	 */
	@Override
	public Long queryTotalProperty() {
		return this.baseMapper.queryTotalProperty(jurisdictionUtilsService.queryByInsId("PLAT_"), jurisdictionUtilsService.queryByOutlesId("PLAT_"));
	}

	@Override
	public	IPage<AssetsReLiquiFlowChartPageVO> queryPropertyAuctionAnnouncementPeriod(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectPropertyAuctionAnnouncementPeriod(page,assetsReLiquiFlowChartPageDTO,insOutlesDTO);
	}

	@Override
	public AssetsReLiquiDetailsVO getAssetsReDetails(Integer assetsReId){
		return this.baseMapper.selectAssetsReDetails(assetsReId);
	}

	@Override
	public IPage<AssetsReLiquiFlowChartPageVO> queryDispositionRuling(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectDispositionRuling(page,assetsReLiquiFlowChartPageDTO,insOutlesDTO);
	}

	@Override
	public IPage<AssetsReLiquiFlowChartPageVO> queryPropertyAuctionDue(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectPropertyAuctionDue(page,assetsReLiquiFlowChartPageDTO,insOutlesDTO);
	}

	@Override
	public 	IPage<AssetsReLiquiFlowChartPageVO> queryPropertyAuctionSuccess(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectPropertyAuctionSuccess(page,assetsReLiquiFlowChartPageDTO,insOutlesDTO);
	}

	@Override
	public	IPage<AssetsReLiquiFlowChartPageVO> queryPropertyAuctionFailed(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectPropertyAuctionFailed(page,assetsReLiquiFlowChartPageDTO,insOutlesDTO);
	}

	@Override
	public	IPage<AssetsReLiquiFlowChartPageVO> queryPropertyAuctionAbnormal(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectPropertyAuctionAbnormal(page,assetsReLiquiFlowChartPageDTO,insOutlesDTO);
	}

	@Override
	public	List<AssetsReLiquiMortgageVO> queryAssetsReAddress(Integer projectId){
		return this.baseMapper.selectAssetsReAddress(projectId);
	}

	@Override
	public IPage<AssetsReLiquiFlowChartPageVO> queryCaseeAssetsNotFreeze(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectCaseeAssetsNotFreeze(page,assetsReLiquiFlowChartPageDTO,insOutlesDTO);
	}

	@Override
	public 	IPage<AssetsReLiquiSubjectVO> queryAssetsReBySubjectId(Page page,Integer subjectId){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.queryAssetsReBySubjectId(page,subjectId,insOutlesDTO);
	}

}
