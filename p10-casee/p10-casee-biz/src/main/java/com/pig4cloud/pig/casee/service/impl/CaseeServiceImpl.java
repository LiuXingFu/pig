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
import com.pig4cloud.pig.admin.api.dto.TaskNodeTemplateDTO;
import com.pig4cloud.pig.admin.api.entity.TaskNodeTemplate;
import com.pig4cloud.pig.admin.api.feign.RemoteOutlesTemplateReService;
import com.pig4cloud.pig.casee.dto.CaseeAddDTO;
import com.pig4cloud.pig.casee.dto.CaseeGetListDTO;
import com.pig4cloud.pig.casee.dto.TargetAddDTO;
import com.pig4cloud.pig.casee.entity.Casee;
import com.pig4cloud.pig.casee.entity.Project;
import com.pig4cloud.pig.casee.mapper.CaseeMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.CaseeVO;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.service.PigUser;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 案件表
 *
 * @author yy
 * @date 2022-01-10 14:51:59
 */
@Service
public class CaseeServiceImpl extends ServiceImpl<CaseeMapper, Casee> implements CaseeService {
	@Autowired
	private SecurityUtilsService securityUtilsService;
	@Autowired
	private CaseeSubjectReService caseeSubjectReService;
	@Autowired
	private TargetService targetService;
	@Autowired
	private RemoteOutlesTemplateReService remoteOutlesTemplateReService;
	@Autowired
	private ProjectLiquiService projectLiquiService;
	@Autowired
	private TaskNodeService taskNodeService;

	/**
	 * 新增案件
	 *
	 * @return
	 */
	@Override
	@Transactional
	public Integer addCase(CaseeAddDTO caseeAddDTO) throws Exception{

		//查询项目信息
		Project project = projectLiquiService.getById(caseeAddDTO.getProjectId());
		PigUser pigUser=securityUtilsService.getCacheUser();

		R<TaskNodeTemplate> taskNodeTemplate = remoteOutlesTemplateReService.queryTemplateByTemplateNature(caseeAddDTO.getCaseeType(),project.getCreateOutlesId(), SecurityConstants.FROM);

		//根据案件类型以及项目受托网点id查询该网点是否配置了对应模板
		if (taskNodeTemplate.getData()==null){
			//如果当前受托网点没有配置模板直接返回
			return -1;
		}

		//添加案件基础信息
		Casee casee = new Casee();
		BeanUtils.copyProperties(caseeAddDTO, casee);
		int insert = this.baseMapper.insert(casee);
		//添加案件主体关联信息
		if(caseeAddDTO.getCaseeSubjectReList()!=null&&caseeAddDTO.getCaseeSubjectReList().size()!=0){
			caseeAddDTO.getCaseeSubjectReList().stream().forEach(val -> {
				val.setCaseeId(casee.getCaseeId());
			});
			caseeSubjectReService.saveBatch(caseeAddDTO.getCaseeSubjectReList());
		}

		//增加程序

		TargetAddDTO targetAddDTO = new TargetAddDTO();
//		targetAddDTO.setCreateInsId(pigUser.getInsId());
//		targetAddDTO.setCreateOutlesId(pigUser.getOutlesId());
		targetAddDTO.setCaseeId(casee.getCaseeId());
		targetAddDTO.setProcedureNature(casee.getCaseeType());

		TargetAddDTO addDTO = targetService.saveTargetAddDTO(targetAddDTO);
		//添加任务数据
		configurationNodeTemplate(casee,addDTO,project,taskNodeTemplate.getData().getTemplateId());
		return insert;
	}

	/**
	 * 查询案件列表
	 *
	 * @return
	 */
	@Override
	public List<CaseeVO> getCaseeList(CaseeGetListDTO caseeGetListDTO) {
		return this.baseMapper.selectCaseeList(caseeGetListDTO);
	}

	@Override
	public void configurationNodeTemplate(Casee casee,TargetAddDTO targetAddDTO,Project project, Integer templateId) {
		TaskNodeTemplateDTO taskNodeTemplateDTO=new TaskNodeTemplateDTO();
		taskNodeTemplateDTO.setCaseeId(casee.getCaseeId());
		taskNodeTemplateDTO.setInsId(project.getCreateInsId());
		taskNodeTemplateDTO.setOutlesId(project.getCreateOutlesId());
		taskNodeTemplateDTO.setProjectId(casee.getProjectId());
		taskNodeTemplateDTO.setTargetId(targetAddDTO.getTargetId());
		taskNodeTemplateDTO.setTemplateId(templateId);

		String businessData = targetAddDTO.getBusinessData();
		if (businessData!=null){
			JSONObject jsonObject= JSONObject.fromObject(businessData);
			//生成任务
			taskNodeService.queryNodeTemplateAddTaskNode(taskNodeTemplateDTO,jsonObject);
		}
	}
}
