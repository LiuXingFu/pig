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
import com.pig4cloud.pig.casee.dto.CaseeLiquiAddDTO;
import com.pig4cloud.pig.casee.dto.TargetAddDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.liquientity.CaseeLiqui;
import com.pig4cloud.pig.casee.mapper.CaseeLiquiMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.CaseeListVO;
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
	private RemoteOutlesTemplateReService remoteOutlesTemplateReService;

	@Autowired
	private ProjectLiquiService projectLiquiService;

	@Autowired
	private TargetService targetService;

	@Autowired
	private TaskNodeService taskNodeService;

	@Autowired
	private AssetsReService assetsReService;

	@Autowired
	private CaseeSubjectReService caseeSubjectReService;

	@Autowired
	private RemoteSubjectService subjectService;

	@Autowired
	private RemoteUserService userService;



	@Override
	@Transactional
	public Integer addCaseeLiqui(CaseeLiquiAddDTO caseeLiquiAddDTO) throws Exception{
		// 保存清收案件
		CaseeLiqui caseeLiqui = new CaseeLiqui();
		BeanCopyUtil.copyBean(caseeLiquiAddDTO,caseeLiqui);
		// 查询被告/被执行人/被上诉人等名称
		R<List<Subject>> subjectIdList = subjectService.queryBySubjectIdList(caseeLiquiAddDTO.getSubjectIdList(),SecurityConstants.FROM);
		if(subjectIdList.getData().size()>0){
			String executedName = "";
			for(Subject subject:subjectIdList.getData()){
				if(executedName.equals("")){
					executedName = subject.getName();
				}else{
					executedName = executedName+","+subject.getName();
				}
			}
			caseeLiqui.setExecutedName(executedName);
		}
		Project project = projectLiquiService.getById(caseeLiquiAddDTO.getProjectId());
		caseeLiqui.setApplicantName(project.getProposersNames());
		Integer save = this.baseMapper.insert(caseeLiqui);
		// 保存项目案件关联表
		ProjectCaseeRe projectCaseeRe = new ProjectCaseeRe();
		BeanCopyUtil.copyBean(caseeLiquiAddDTO,projectCaseeRe);
		projectCaseeRe.setCaseeId(caseeLiqui.getCaseeId());
		projectCaseeReLiquiService.save(projectCaseeRe);
		// 查询抵押财产，更新财产关联表
		QueryWrapper<AssetsRe> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(AssetsRe::getDelFlag, CommonConstants.STATUS_NORMAL);
		queryWrapper.lambda().eq(AssetsRe::getProjectId, caseeLiquiAddDTO.getParentId());
		queryWrapper.lambda().eq(AssetsRe::getAssetsSource, 1);
		queryWrapper.lambda().isNull(AssetsRe::getCaseeId);
		List<AssetsRe> assetsRes = assetsReService.list(queryWrapper);
		// 遍历保存财产关联
		if(assetsRes.size()>0){
			List<AssetsRe> assetsReList = new ArrayList<>();
			assetsRes.stream().forEach(item->{
				AssetsRe assetsRe = new AssetsRe();
				assetsRe.setAssetsId(item.getAssetsId());
				assetsRe.setCaseeId(caseeLiqui.getCaseeId());
				assetsReList.add(assetsRe);
			});
			assetsReService.saveBatch(assetsReList);
		}
		// 保存案件人员关联
		List<CaseeSubjectRe> caseeSubjectReList = new ArrayList<>();
		caseeLiquiAddDTO.getSubjectIdList().stream().forEach(item->{
			CaseeSubjectRe caseeSubjectRe = new CaseeSubjectRe();
			caseeSubjectRe.setCaseeId(caseeLiqui.getCaseeId());
			caseeSubjectRe.setSubjectId(item);
			caseeSubjectRe.setType(1);
			caseeSubjectReList.add(caseeSubjectRe);
		});
		caseeSubjectReService.saveBatch(caseeSubjectReList);
//		// 查询
//		R<TaskNodeTemplate> taskNodeTemplate = remoteOutlesTemplateReService.queryTemplateByTemplateNature(caseeLiquiAddDTO.getCaseeType(),project.getOutlesId(), SecurityConstants.FROM);
//		//根据案件类型以及项目受托网点id查询该网点是否配置了对应模板
//		if (taskNodeTemplate.getData()==null){
//			//如果当前受托网点没有配置模板直接返回
//			return -1;
//		}
//		//增加程序
//		TargetAddDTO targetAddDTO = new TargetAddDTO();
////		targetAddDTO.setCreateInsId(pigUser.getInsId());
////		targetAddDTO.setCreateOutlesId(pigUser.getOutlesId());
//		targetAddDTO.setCaseeId(caseeLiqui.getCaseeId());
//		targetAddDTO.setProcedureNature(caseeLiquiAddDTO.getCaseeType());
//
//		TargetAddDTO addDTO = targetService.saveTargetAddDTO(targetAddDTO);
//		//添加任务数据
//		configurationNodeTemplate(caseeLiqui.getCaseeId(),addDTO,project,taskNodeTemplate.getData().getTemplateId());
		return save;
	}

	public void configurationNodeTemplate(Integer caseeId,TargetAddDTO targetAddDTO,Project project, Integer templateId) {
		TaskNodeTemplateDTO taskNodeTemplateDTO=new TaskNodeTemplateDTO();
		taskNodeTemplateDTO.setCaseeId(caseeId);
		taskNodeTemplateDTO.setInsId(project.getInsId());
		taskNodeTemplateDTO.setOutlesId(project.getOutlesId());
//		taskNodeTemplateDTO.setProjectId(casee.getProjectId());
		taskNodeTemplateDTO.setTargetId(targetAddDTO.getTargetId());
		taskNodeTemplateDTO.setTemplateId(templateId);

		String businessData = targetAddDTO.getBusinessData();
		if (businessData!=null){
			JSONObject jsonObject= JSONObject.fromObject(businessData);
			//生成任务
			taskNodeService.queryNodeTemplateAddTaskNode(taskNodeTemplateDTO,jsonObject);
		}
	}

	@Override
	public List<CaseeListVO> queryByIdList(Integer caseeId, List<Integer> caseeType){
		return this.baseMapper.selectByIdList(caseeId,caseeType);
	}

	@Override
	public CaseeLiqui getCaseeParentId(Integer projectId,Integer caseeType){
		return this.baseMapper.getCaseeParentId(projectId,caseeType);
	}

}
