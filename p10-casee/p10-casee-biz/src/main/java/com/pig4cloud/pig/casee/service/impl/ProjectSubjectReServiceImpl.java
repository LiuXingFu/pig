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

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteAddressService;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.ProjectSubjectReModifyDTO;
import com.pig4cloud.pig.casee.entity.Project;
import com.pig4cloud.pig.casee.entity.ProjectSubjectRe;
import com.pig4cloud.pig.casee.mapper.ProjectSubjectReMapper;
import com.pig4cloud.pig.casee.service.ProjectService;
import com.pig4cloud.pig.casee.service.ProjectSubjectReService;
import com.pig4cloud.pig.casee.vo.ProjectSubjectVO;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目主体关联表
 *
 * @author ligt
 * @date 2022-01-11 14:52:12
 */
@Service
public class ProjectSubjectReServiceImpl extends ServiceImpl<ProjectSubjectReMapper, ProjectSubjectRe> implements ProjectSubjectReService {

	@Autowired
	private RemoteSubjectService remoteSubjectService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private RemoteAddressService addressService;

	@Override
	@Transactional
	public Integer modifySubjectBySubjectReId(ProjectSubjectReModifyDTO projectSubjectReModifyDTO){
		Subject subject = new Subject();
		BeanCopyUtil.copyBean(projectSubjectReModifyDTO,subject);
		R<Integer> subjectId =  remoteSubjectService.saveOrUpdateById(subject, SecurityConstants.FROM);

		ProjectSubjectRe projectSubjectRe = new ProjectSubjectRe();
		projectSubjectRe.setSubjectReId(projectSubjectReModifyDTO.getSubjectReId());
		projectSubjectRe.setType(projectSubjectReModifyDTO.getType());
		projectSubjectRe.setProjectId(projectSubjectReModifyDTO.getProjectId());
		projectSubjectRe.setSubjectId(subjectId.getData());
		projectSubjectRe.setDescribes(projectSubjectReModifyDTO.getDescribes());
		this.saveOrUpdate(projectSubjectRe);

		updateProjectExecutedName(projectSubjectReModifyDTO.getProjectId());

		if(projectSubjectReModifyDTO.getAddressList().size()>0){
			List<Address> addressList = new ArrayList<>();
			for(Address address : projectSubjectReModifyDTO.getAddressList()){
				// 保存地址
				address.setUserId(subjectId.getData());
				address.setType(1);
				addressList.add(address);
			}
			addressService.saveOrUpdateBatch(addressList,SecurityConstants.FROM);
		}
		return 1;
	}

	public void updateProjectExecutedName(Integer projectId){
		List<ProjectSubjectVO>  projectSubjectVOS = this.baseMapper.queryByProjectId(projectId,-1);
		String executedName = "";
		for (ProjectSubjectVO projectSubjectVO : projectSubjectVOS) {
			String name = projectSubjectVO.getName();
			if (projectSubjectVO.getType() != 0) {
				if (executedName.equals("")) {
					executedName = name;
				} else {
					executedName = executedName + "," + name;
				}
			}
		}
		Project project = new Project();
		project.setProjectId(projectId);
		project.setSubjectPersons(executedName);
		projectService.updateById(project);
	}

	@Override
	public ProjectSubjectVO getProjectSubjectDetail(Integer projectId, Integer subjectId){
		return this.baseMapper.getProjectSubjectDetail(projectId,subjectId);
	}

	@Override
	@Transactional
	public Integer removeProjectSubjectRe(Integer projectId,Integer subjectReId){
		Integer remove = this.baseMapper.deleteById(subjectReId);
		updateProjectExecutedName(projectId);
		return remove;
	}
}
