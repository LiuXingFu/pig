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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.admin.api.feign.RemoteAddressService;
import com.pig4cloud.pig.casee.dto.*;
import com.pig4cloud.pig.casee.dto.liqui.AssetsReUnravelDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.liquientity.AssetsReLiqui;
import com.pig4cloud.pig.casee.entity.liquientity.detail.detailentity.AssetsReUnravel;
import com.pig4cloud.pig.casee.entity.project.entityzxprocedure.EntityZX_STZX_CCZXCF_CCZXCF;
import com.pig4cloud.pig.casee.entity.project.entityzxprocedure.EntityZX_STZX_CCZXSQYS_CCZXSQYS;
import com.pig4cloud.pig.casee.mapper.AssetsReLiquiMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.*;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ???????????????
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
	@Autowired
	AssetsLiquiTransferRecordReService assetsLiquiTransferRecordReService;
	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	AssetsReSubjectService assetsReSubjectService;
	@Autowired
	CaseeHandlingRecordsService caseeHandlingRecordsService;
	@Autowired
	AssetsReLiquiService assetsReLiquiService;
	@Autowired
	RemoteAddressService addressService;

	@Override
	@Transactional
	public Integer saveAssetsCasee(AssetsAddDTO assetsAddDTO)throws Exception{
		Integer assetsId = assetsAddDTO.getAssetsId();
		// ???????????????????????????????????????????????????
		if(Objects.isNull(assetsId)){
			Assets assets = new Assets();
			BeanCopyUtil.copyBean(assetsAddDTO,assets);
			assetsService.save(assets);
			assetsId = assets.getAssetsId();
			assetsAddDTO.setAssetsId(assets.getAssetsId());

		}
		AssetsReLiqui assetsReLiqui = new AssetsReLiqui();
		// ????????????2=??????
		assetsReLiqui.setAssetsSource(2);
		assetsReLiqui.setCreateCaseeId(assetsAddDTO.getCaseeId());
		BeanCopyUtil.copyBean(assetsAddDTO, assetsReLiqui);

		//????????????????????????????????????
		Project project = projectLiquiService.getById(assetsAddDTO.getProjectId());
		TargetAddDTO targetAddDTO=new TargetAddDTO();
		if (assetsAddDTO.getType()==20100){//????????????
			targetAddDTO.setProcedureNature(4041);
		}else if (assetsAddDTO.getType()==20200){//????????????
			targetAddDTO.setProcedureNature(4040);
		}
		targetAddDTO.setCaseeId(assetsAddDTO.getCaseeId());
		targetAddDTO.setOutlesId(project.getOutlesId());
		targetAddDTO.setProjectId(assetsAddDTO.getProjectId());
		targetAddDTO.setGoalType(20001);
		targetAddDTO.setGoalId(assetsId);
		targetService.saveTargetAddDTO(targetAddDTO);

		Integer save = this.baseMapper.insert(assetsReLiqui);
		List<AssetsReSubject> assetsReSubjects = new ArrayList<>();
		for(Integer subjectId:assetsAddDTO.getSubjectIdList()){
			AssetsReSubject assetsReSubject = new AssetsReSubject();
			assetsReSubject.setSubjectId(subjectId);
			assetsReSubject.setAssetsReId(assetsReLiqui.getAssetsReId());
			assetsReSubjects.add(assetsReSubject);
		}
		assetsReSubjectService.saveBatch(assetsReSubjects);

		// ????????????????????????
		if(Objects.nonNull(assetsAddDTO.getCode())){
			R<Address> addressR = addressService.queryAssetsByTypeIdAndType(assetsId,4,SecurityConstants.FROM);
			Address address = new Address();
			BeanCopyUtil.copyBean(assetsAddDTO,address);
			// 4=????????????
			address.setType(4);
			address.setUserId(assetsId);
			if(addressR.getData()!=null){
				address.setAddressId(addressR.getData().getAddressId());
			}
			addressService.saveOrUpdate(address, SecurityConstants.FROM);
		}
		return save;
	}

	@Override
	public List<AssetsReDTO> getTransferAssetsByProjectId(Integer projectId) {
		//?????????????????????????????????????????????
		List<AssetsReDTO> assetsRes = this.baseMapper.getAssetsByProjectId(projectId,null);

		List<AssetsReDTO> assetsReLiquis=new ArrayList<>();

		List<AssetsReDTO> transferableAssets=new ArrayList<>();

		for (AssetsReDTO assetsReDTO : assetsRes) {//?????????????????????????????????
			List<AssetsLiquiTransferRecordRe> assetsLiquiTransferRecordReList = assetsLiquiTransferRecordReService.getByTransferRecordAssets(projectId,assetsReDTO.getAssetsReId());
			if (assetsLiquiTransferRecordReList.size()<=0){
				assetsReLiquis.add(assetsReDTO);
			}
		}

		for (AssetsReDTO assetsReDTO : assetsReLiquis) {
			Integer mortgagee = assetsReDTO.getAssetsReCaseeDetail().getMortgagee();//0: ?????????,1: ?????????,2: ???????????????
			//??????????????????????????????
			Target target = targetService.getOne(new LambdaQueryWrapper<Target>().eq(Target::getCaseeId, assetsReDTO.getCaseeId()).eq(Target::getGoalId, assetsReDTO.getAssetsId()).eq(Target::getGoalType, 20001).eq(Target::getProcedureNature, 4040).eq(Target::getDelFlag, 0));
			if (target!=null){
				//????????????????????????????????????
				List<TaskNode> list = taskNodeService.list(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getProjectId, assetsReDTO.getProjectId()).eq(TaskNode::getCaseeId, assetsReDTO.getCaseeId()).eq(TaskNode::getTargetId, target.getTargetId()));
				for (TaskNode taskNode : list) {
					if (taskNode.getNodeKey().equals("entityZX_STZX_CCZXSQYS_CCZXSQYS")){//????????????
						EntityZX_STZX_CCZXSQYS_CCZXSQYS entityZX_stzx_cczxsqys_cczxsqys = JsonUtils.jsonToPojo(taskNode.getFormData(), EntityZX_STZX_CCZXSQYS_CCZXSQYS.class);
						//????????????????????????
						TaskNode taskNodeCCCF = taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeKey, "entityZX_STZX_CCZXCF_CCZXCF").eq(TaskNode::getProjectId, assetsReDTO.getProjectId()).eq(TaskNode::getCaseeId, assetsReDTO.getCaseeId()).eq(TaskNode::getTargetId, target.getTargetId()));
						EntityZX_STZX_CCZXCF_CCZXCF entityZX_stzx_cczxcf_cczxcf = JsonUtils.jsonToPojo(taskNodeCCCF.getFormData(), EntityZX_STZX_CCZXCF_CCZXCF.class);
						if (entityZX_stzx_cczxcf_cczxcf != null) {
							if ((mortgagee == 0 && entityZX_stzx_cczxcf_cczxcf.getSealUpCondition() == 0) ||//??????????????????????????????
									(mortgagee == 0 && entityZX_stzx_cczxcf_cczxcf.getSealUpCondition() == 1 && entityZX_stzx_cczxsqys_cczxsqys != null && entityZX_stzx_cczxsqys_cczxsqys.getBusinessPleaseTransfer() == 0) ||//????????????????????????????????????????????????(????????????)
									(mortgagee == 1 && entityZX_stzx_cczxcf_cczxcf.getSealUpCondition() == 0 && entityZX_stzx_cczxsqys_cczxsqys != null && entityZX_stzx_cczxsqys_cczxsqys.getBusinessPleaseTransfer() == 2)) {//???????????????????????????????????????
								transferableAssets.add(assetsReDTO);
							}
						}
					}
				}

			}

		}
		return transferableAssets;
	}

	@Override
	public AssetsReLiqui getAssetsReCasee(AssetsRe assetsRe) {
		return this.baseMapper.getAssetsReCasee(assetsRe);
	}


	@Override
	public AssetsReSubjectDTO queryAssetsSubject(Integer projectId, Integer caseeId, Integer assetsId) {
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
	public 	IPage<AssetsReLiquiFlowChartPageVO> queryRealEstateSurveyNotRegistered(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectRealEstateSurveyNotRegistered(page,assetsReLiquiFlowChartPageDTO,insOutlesDTO);
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
	 * ????????????????????????
	 * @return
	 */
	@Override
	public List<PropertyCategoryTotalVO> queryPropertyCategoryTotalList() {
		return this.baseMapper.queryPropertyCategoryTotalList(jurisdictionUtilsService.queryByInsId("PLAT_"), jurisdictionUtilsService.queryByOutlesId("PLAT_"));
	}

	/**
	 * ???????????????
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
	public IPage<AssetsReLiquiFlowChartPageVO> queryRulingNotService(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectRulingNotService(page,assetsReLiquiFlowChartPageDTO,insOutlesDTO);
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

	@Override
	public 	IPage<AssetsReLiquiProjectVO> queryByAssetsId(Page page,Integer assetsId){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectByAssetsId(page,assetsId,insOutlesDTO);
	}

	@Override
	public Integer updateAssetsRe(Integer projectId,Integer newCaseeId){
		return this.baseMapper.updateAssetsRe(projectId,newCaseeId);
	}

	@Override
	public 	List<AssetsReDTO> queryTransferableAssetsReList(Integer projectId){
		return this.baseMapper.queryTransferableAssetsReList(projectId);
	}

	@Override
	public 	List<AssetsReDTO> getAssetsByProjectId(Integer projectId,Integer caseeId){
		return this.baseMapper.getAssetsByProjectId(projectId,caseeId);
	}

	@Override
	public List<AssetsReDTO> queryAssetsReByProjectId(Integer projectId,Integer caseeId){
		return this.baseMapper.queryAssetsReByProjectId(projectId,caseeId);
	}

	@Override
	@Transactional
	public Integer removeNotInAssetsId(Integer projectId,Integer mortgageAssetsRecordsId,List<Integer> assetsIdList){
		return this.baseMapper.removeNotInAssetsId(projectId,mortgageAssetsRecordsId,assetsIdList);
	}

	@Override
	@Transactional
	public Integer assetsUnravelByAssetsReId(AssetsReUnravelDTO assetsReUnravelDTO){
		AssetsReLiqui assetsReLiqui = this.baseMapper.selectByAssetsReId(assetsReUnravelDTO.getAssetsReId());
		assetsReLiqui.setStatus(assetsReUnravelDTO.getStatus());
		AssetsReUnravel assetsReUnravel = new AssetsReUnravel();
		BeanCopyUtil.copyBean(assetsReUnravelDTO,assetsReUnravel);
		assetsReLiqui.getAssetsReCaseeDetail().setAssetsReUnravel(assetsReUnravel);
		return this.baseMapper.updateById(assetsReLiqui);
	}

	@Override
	public 	AssetsReLiqui getByAssetsReId(Integer assetsReId){
		return this.baseMapper.selectByAssetsReId(assetsReId);
	}

}
