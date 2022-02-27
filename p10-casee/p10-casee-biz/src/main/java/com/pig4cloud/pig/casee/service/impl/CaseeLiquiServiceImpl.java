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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.dto.TaskNodeTemplateDTO;
import com.pig4cloud.pig.admin.api.entity.Institution;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.entity.TaskNodeTemplate;
import com.pig4cloud.pig.admin.api.feign.RemoteInstitutionService;
import com.pig4cloud.pig.admin.api.feign.RemoteOutlesTemplateReService;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.admin.api.feign.RemoteUserService;
import com.pig4cloud.pig.admin.api.vo.InstitutionVO;
import com.pig4cloud.pig.admin.api.vo.UserVO;
import com.pig4cloud.pig.casee.dto.*;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.liquientity.CaseeLiqui;
import com.pig4cloud.pig.casee.mapper.CaseeLiquiMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.CaseeLiquiDetailsVO;
import com.pig4cloud.pig.casee.vo.CaseeLiquiPageVO;
import com.pig4cloud.pig.casee.vo.CaseeListVO;
import com.pig4cloud.pig.casee.vo.SubjectAssetsBehaviorListVO;
import com.pig4cloud.pig.common.core.constant.CommonConstants;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.core.util.R;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * 案件表
 *
 * @author yy
 * @date 2022-01-10 14:51:59
 */
@Service
public class CaseeLiquiServiceImpl extends ServiceImpl<CaseeLiquiMapper, Casee> implements CaseeLiquiService {
	@Autowired
	private ProjectCaseeReLiquiService projectCaseeReLiquiService;
	@Autowired
	private ProjectLiquiService projectLiquiService;
	@Autowired
	private TargetService targetService;
	@Autowired
	private AssetsReService assetsReService;
	@Autowired
	private CaseeSubjectReService caseeSubjectReService;
	@Autowired
	private RemoteSubjectService subjectService;
	@Autowired
	private CaseeLawyerReService caseeLawyerReService;
	@Autowired
	private ProjectStatusService projectStatusService;

	@Override
	@Transactional
	public Integer addCaseeLiqui(CaseeLiquiAddDTO caseeLiquiAddDTO) throws Exception {
		// 保存清收案件
		CaseeLiqui caseeLiqui = new CaseeLiqui();
		BeanCopyUtil.copyBean(caseeLiquiAddDTO, caseeLiqui);
		this.baseMapper.insert(caseeLiqui);
		// 保存项目案件关联表
		ProjectCaseeRe projectCaseeRe = new ProjectCaseeRe();
		BeanCopyUtil.copyBean(caseeLiquiAddDTO, projectCaseeRe);
		projectCaseeRe.setCaseeId(caseeLiqui.getCaseeId());
		projectCaseeReLiquiService.save(projectCaseeRe);
		// 保存案件人员关联
		List<CaseeSubjectRe> caseeSubjectReList = new ArrayList<>();
		// 遍历申请人/原告/上诉人/申请执行人等集合
		caseeLiquiAddDTO.getApplicantList().stream().forEach(item -> {
			CaseeSubjectRe caseeSubjectRe = new CaseeSubjectRe();
			caseeSubjectRe.setCaseeId(caseeLiqui.getCaseeId());
			caseeSubjectRe.setSubjectId(item.getSubjectId());
			caseeSubjectRe.setType(item.getType());
			caseeSubjectRe.setCaseePersonnelType(0);
			caseeSubjectReList.add(caseeSubjectRe);
		});
		// 遍历被告/被执行人/被上述人等集合
		caseeLiquiAddDTO.getExecutedList().stream().forEach(item -> {
			CaseeSubjectRe caseeSubjectRe = new CaseeSubjectRe();
			caseeSubjectRe.setCaseeId(caseeLiqui.getCaseeId());
			caseeSubjectRe.setSubjectId(item.getSubjectId());
			caseeSubjectRe.setType(item.getType());
			caseeSubjectRe.setCaseePersonnelType(1);
			caseeSubjectReList.add(caseeSubjectRe);
		});
		caseeSubjectReService.saveBatch(caseeSubjectReList);

		ProjectStatus projectStatus = new ProjectStatus();
		projectStatus.setType(2);
		projectStatus.setSourceId(caseeLiqui.getCaseeId());
		projectStatus.setStatusName("案件立案");
		projectStatus.setUserName(caseeLiquiAddDTO.getActualName());
		projectStatus.setChangeTime(caseeLiquiAddDTO.getStartTime());
		projectStatusService.save(projectStatus);

		//添加任务数据以及程序信息
		TargetAddDTO targetAddDTO=new TargetAddDTO();
		targetAddDTO.setCaseeId(caseeLiqui.getCaseeId());
		targetAddDTO.setProcedureNature(caseeLiqui.getCaseeType());
		targetService.saveTargetAddDTO(targetAddDTO);
		return caseeLiqui.getCaseeId();
	}

	@Override
	@Transactional
	public Integer addPreservationCasee(CaseeLiquiAddDTO caseeLiquiAddDTO) throws Exception{
		Integer caseeId = addCaseeLiqui(caseeLiquiAddDTO);
		saveAssetsReService(caseeLiquiAddDTO.getProjectId(),caseeId);
		return caseeId;
	}

	@Override
	@Transactional
	public Integer addLawsuitsCasee(CaseeLawsuitsDTO caseeLawsuitsDTO) throws Exception{
		CaseeLiquiAddDTO caseeLiquiAddDTO = new CaseeLiquiAddDTO();
		BeanCopyUtil.copyBean(caseeLawsuitsDTO,caseeLiquiAddDTO);
		Integer caseeId = addCaseeLiqui(caseeLiquiAddDTO);
		// 保存案件代理律师
		if (Objects.nonNull(caseeLawsuitsDTO.getLawyerName())) {
			CaseeLawyerRe caseeLawyerRe = new CaseeLawyerRe();
			caseeLawyerRe.setActualName(caseeLawsuitsDTO.getLawyerName());
			caseeLawyerRe.setCaseeId(caseeId);
			caseeLawyerReService.save(caseeLawyerRe);
		}
		return caseeId;
	}

	@Override
	@Transactional
	public Integer saveSecondInstanceCasee(CaseeSecondInstanceDTO caseeSecondInstanceDTO) throws Exception{
		CaseeLiquiAddDTO caseeLiquiAddDTO = new CaseeLiquiAddDTO();
		BeanCopyUtil.copyBean(caseeSecondInstanceDTO,caseeLiquiAddDTO);
		if(Objects.nonNull(caseeSecondInstanceDTO.getSubjectList())){
			List<CaseeSubjectReDTO> applicantList = new ArrayList<>();
			caseeSecondInstanceDTO.getSubjectList().stream().forEach(item->{
				Integer subjectId = 0;
				if(Objects.nonNull(item.getSubjectId())){
					subjectId = item.getSubjectId();
				}else{
					R subject = subjectService.saveOrUpdateById(item,SecurityConstants.FROM);
					subjectId = Integer.valueOf(subject.getData().toString());
				}
				CaseeSubjectReDTO caseeSubjectReDTO = new CaseeSubjectReDTO();
				caseeSubjectReDTO.setSubjectId(subjectId);
				// 案件人员关系4=案外人
				caseeSubjectReDTO.setType(4);
				applicantList.add(caseeSubjectReDTO);
			});
			caseeLiquiAddDTO.setApplicantList(applicantList);
		}

		Integer caseeId = addCaseeLiqui(caseeLiquiAddDTO);
		// 保存案件代理律师
		if (Objects.nonNull(caseeSecondInstanceDTO.getLawyerName())) {
			CaseeLawyerRe caseeLawyerRe = new CaseeLawyerRe();
			caseeLawyerRe.setActualName(caseeSecondInstanceDTO.getLawyerName());
			caseeLawyerRe.setCaseeId(caseeId);
			caseeLawyerReService.save(caseeLawyerRe);
		}
		return caseeId;
	}

	@Override
	@Transactional
	public Integer addExecutionCasee(CaseeLiquiAddDTO caseeLiquiAddDTO) throws Exception{
		Integer caseeId = addCaseeLiqui(caseeLiquiAddDTO);
		// 更新案件类别
		CaseeLiqui caseeLiqui = new CaseeLiqui();
		caseeLiqui.setCaseeId(caseeId);
		// 30101=首执
		caseeLiqui.setCategory(30101);
		this.baseMapper.updateById(caseeLiqui);

		// 查询所有财产，更新财产关联表
		QueryWrapper<AssetsRe> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(AssetsRe::getDelFlag, CommonConstants.STATUS_NORMAL);
		queryWrapper.lambda().eq(AssetsRe::getProjectId, caseeLiquiAddDTO.getProjectId());
		List<AssetsRe> assetsRes = assetsReService.list(queryWrapper);
		// 遍历修改财产案件关联
		if (assetsRes.size() > 0) {
			List<AssetsRe> assetsReList = new ArrayList<>();
			assetsRes.stream().forEach(item -> {
				AssetsRe assetsRe = new AssetsRe();
				assetsRe.setAssetsReId(item.getAssetsReId());
				assetsRe.setCaseeId(caseeId);
				// 假如没有创建案件id，将首执案件id保存
				if(Objects.isNull(item.getCreateCaseeId())){
					assetsRe.setCreateCaseeId(caseeId);
				}
				assetsReList.add(assetsRe);
			});
			assetsReService.updateBatchById(assetsReList);
		}
		return caseeId;
	}

	@Override
	@Transactional
	public Integer addReinstatementCasee(CaseeReinstatementDTO caseeReinstatementDTO) throws Exception{
		CaseeLiquiAddDTO caseeLiquiAddDTO = new CaseeLiquiAddDTO();
		BeanCopyUtil.copyBean(caseeReinstatementDTO,caseeLiquiAddDTO);
		Integer caseeId = addCaseeLiqui(caseeLiquiAddDTO);
		// 更新案件类别
		CaseeLiqui caseeLiqui = new CaseeLiqui();
		caseeLiqui.setCaseeId(caseeId);
		// 30311=执恢
		caseeLiqui.setCategory(30311);
		this.baseMapper.updateById(caseeLiqui);
		return caseeId;
	}

	@Transactional
	public void saveAssetsReService(Integer projectId,Integer caseeId){
		// 查询抵押财产，更新财产关联表
		QueryWrapper<AssetsRe> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(AssetsRe::getDelFlag, CommonConstants.STATUS_NORMAL);
		queryWrapper.lambda().eq(AssetsRe::getProjectId, projectId);
		queryWrapper.lambda().eq(AssetsRe::getAssetsSource, 1);
		queryWrapper.lambda().isNull(AssetsRe::getCaseeId);
		List<AssetsRe> assetsRes = assetsReService.list(queryWrapper);
		// 遍历保存财产关联
		if (assetsRes.size() > 0) {
			List<AssetsRe> assetsReList = new ArrayList<>();
			assetsRes.stream().forEach(item -> {
				AssetsRe assetsRe = new AssetsRe();
				assetsRe.setAssetsReId(item.getAssetsReId());
				assetsRe.setCaseeId(caseeId);
				assetsRe.setCreateCaseeId(caseeId);
				assetsReList.add(assetsRe);
			});
			assetsReService.updateBatchById(assetsReList);
		}
	}

	@Override
	public List<CaseeListVO> queryByIdList(Integer caseeId, List<Integer> caseeType) {
		return this.baseMapper.selectByIdList(caseeId, caseeType);
	}

	@Override
	public CaseeLiqui getCaseeParentId(Integer projectId, Integer caseeType) {
		return this.baseMapper.getCaseeParentId(projectId, caseeType);
	}

	@Override
	public CaseeLiqui queryByStatusList(Integer projectId,List<Integer> statusList){
		return this.baseMapper.selectByStatusList(projectId,statusList);
	}

	@Override
	public IPage<CaseeLiquiPageVO> queryPage(Page page, CaseeLiquiPageDTO caseeLiquiPageDTO){
		return this.baseMapper.selectPage(page,caseeLiquiPageDTO);
	}

	/**
	 * 根据案件id查询案件信息
	 * @param caseeId
	 * @return
	 */
	@Override
	public CaseeLiquiDetailsVO queryByCaseeId(Integer caseeId) {
		CaseeLiquiDetailsVO caseeLiquiDetailsVO = this.baseMapper.queryByCaseeId(caseeId);
		CaseeOrSubjectDTO caseeOrSubjectDTO = new CaseeOrSubjectDTO();
		caseeOrSubjectDTO.setCaseeId(caseeId);
		caseeLiquiDetailsVO.setCaseeOrSubjectVOList(this.baseMapper.selectCaseeOrSubject(caseeOrSubjectDTO));
		caseeLiquiDetailsVO.setCaseeOrAssetsVOList(this.assetsReService.selectCaseeOrAssets(caseeId));
		return caseeLiquiDetailsVO;
	}

	@Override
	public List<Subject> queryCaseeSubjectList(CaseeSubjectDTO caseeSubjectDTO){
		return this.baseMapper.selectCaseeSubject(caseeSubjectDTO);
	}

	@Override
	public List<SubjectAssetsBehaviorListVO> queryAssetsBehavior(Integer caseeId){
		return this.baseMapper.selectAssetsBehavior(caseeId);
	}

}
