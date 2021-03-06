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
import com.pig4cloud.pig.casee.dto.InsOutlesDTO;
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
 * ?????????
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
	 * ????????????
	 *
	 * @return
	 */
	@Override
	@Transactional
	public Integer addCase(CaseeAddDTO caseeAddDTO) throws Exception{

//		//??????????????????
//		Project project = projectLiquiService.getById(caseeAddDTO.getProjectId());
//		PigUser pigUser=securityUtilsService.getCacheUser();
//
//		R<TaskNodeTemplate> taskNodeTemplate = remoteOutlesTemplateReService.queryTemplateByTemplateNature(caseeAddDTO.getCaseeType(),project.getOutlesId(), SecurityConstants.FROM);
//
//		//??????????????????????????????????????????id??????????????????????????????????????????
//		if (taskNodeTemplate.getData()==null){
//			//??????????????????????????????????????????????????????
//			return -1;
//		}
//
//		//????????????????????????
//		Casee casee = new Casee();
//		BeanUtils.copyProperties(caseeAddDTO, casee);
//		int insert = this.baseMapper.insert(casee);
//		//??????????????????????????????
//		if(caseeAddDTO.getCaseeSubjectReList()!=null&&caseeAddDTO.getCaseeSubjectReList().size()!=0){
//			caseeAddDTO.getCaseeSubjectReList().stream().forEach(val -> {
//				val.setCaseeId(casee.getCaseeId());
//			});
//			caseeSubjectReService.saveBatch(caseeAddDTO.getCaseeSubjectReList());
//		}
//
//		//????????????
//
//		TargetAddDTO targetAddDTO = new TargetAddDTO();
////		targetAddDTO.setCreateInsId(pigUser.getInsId());
////		targetAddDTO.setCreateOutlesId(pigUser.getOutlesId());
//		targetAddDTO.setCaseeId(casee.getCaseeId());
//		targetAddDTO.setProcedureNature(casee.getCaseeType());
//
//		TargetAddDTO addDTO = targetService.saveTargetAddDTO(targetAddDTO);
//		//??????????????????
//		configurationNodeTemplate(casee,addDTO,project,taskNodeTemplate.getData().getTemplateId());
		return 0;
	}

	/**
	 * ??????????????????
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
		taskNodeTemplateDTO.setInsId(project.getInsId());
		taskNodeTemplateDTO.setOutlesId(project.getOutlesId());
//		taskNodeTemplateDTO.setProjectId(casee.getProjectId());
		taskNodeTemplateDTO.setTargetId(targetAddDTO.getTargetId());
		taskNodeTemplateDTO.setTemplateId(templateId);

		String businessData = targetAddDTO.getBusinessData();
		if (businessData!=null){
			JSONObject jsonObject= JSONObject.fromObject(businessData);
			//????????????
			taskNodeService.queryNodeTemplateAddTaskNode(taskNodeTemplateDTO,jsonObject);
		}
	}

	@Override
	public 	List<CaseeVO> queryByPaifuProjectId(Integer projectId){
		return this.baseMapper.selectByPaifuProjectId(projectId);
	}
}
