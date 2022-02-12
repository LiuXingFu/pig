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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.admin.api.feign.RemoteAddressService;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.ProjectLiquiDTO;
import com.pig4cloud.pig.casee.dto.ProjectQueryLiquiDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.mapper.ProjectLiquiMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.ProjectLiquiVO;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 清收项目表
 *
 * @author ligt
 * @date 2022-01-10 15:05:49
 */
@Service
public class ProjectLiquiServiceImpl extends ServiceImpl<ProjectLiquiMapper, Project> implements ProjectLiquiService {


	@Autowired
	private AssetsService assetsService;

	@Autowired
	private RemoteSubjectService remoteSubjectService;

	@Autowired
	private ProjectSubjectReService projectSubjectReService;

	@Autowired
	private RemoteAddressService remoteAddressService;

	@Autowired
	private ProjectOutlesDealReService projectOutlesDealReService;

	@Autowired
	private AssetsReService assetsReService;
	/**
	 * 新增清收项目
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public ProjectLiqui addProject(ProjectLiquiDTO projectDTO){
	  	ProjectLiqui projectLiqui = new ProjectLiqui();
		String executedNames="";//所有债务人
		for (int i=0;i<projectDTO.getSubjectList().size();i++){
			if(i==0){
				executedNames= projectDTO.getSubjectList().get(i).getName();
			}else {
				executedNames=executedNames+","+ projectDTO.getSubjectList().get(i).getName();
			}
		}
		projectDTO.setSubjectPersons(executedNames);
		BeanUtils.copyProperties(projectDTO,projectLiqui);
		this.baseMapper.insert(projectLiqui);
		ObjectMapper objectMapper = new ObjectMapper();
		//添加债务人
		for (ProjectSubject subject:projectDTO.getSubjectList()){
			R rest=remoteSubjectService.saveSubject(subject, SecurityConstants.FROM);
			if(rest!=null && rest.getData()!=null){
				ProjectSubject remoSubject= objectMapper.convertValue(rest.getData(), ProjectSubject.class);
				ProjectSubjectRe projectSubjectRe = new ProjectSubjectRe();
				projectSubjectRe.setSubjectId(remoSubject.getSubjectId());
				projectSubjectRe.setProjectId(projectLiqui.getProjectId());
				projectSubjectRe.setType(1);//债务人
				subject.setSubjectId(remoSubject.getSubjectId());
				projectSubjectReService.save(projectSubjectRe);//保存关联表
				for (Address address:subject.getAddressList()){
					address.setUserId(remoSubject.getSubjectId());
					address.setType(1);//类型(0-账号注册地址 1-主体关联地址 2-机构地址 3-网点地址 4-财产地址 )
					address.setCode("0");
					remoteAddressService.saveAddress(address, SecurityConstants.FROM);//保存地址表
				}
			}
		}
		//添加抵押物
//		List<TargetBizLiqui> targetBizLiquiList = new ArrayList<>();
		for (AssetsLiqui assetsLiqui:projectDTO.getAssetsList()){
			Address address  = assetsLiqui.getAddress();
			if(address != null){
				address.setType(4);
				address.setCode("0");
//				address.setUserId(projectLiqui.getProjectId());
				R rest=remoteAddressService.saveAddress(address, SecurityConstants.FROM);//保存财产地址
				if(rest!=null && rest.getData()!=null){
					address= objectMapper.convertValue( rest.getData(), Address.class);
//					assetsLiqui.setAddressId(address.getAddressId());
				}
			}
			assetsLiqui.setType(20200);
			assetsLiqui.getAssetsRe().setProjectId(projectLiqui.getProjectId());
			for (ProjectSubject subject:projectDTO.getSubjectList()){
				if(subject.getUnifiedIdentity().equals(assetsLiqui.getUnifiedIdentity())){
					assetsLiqui.getAssetsRe().setSubjectId(subject.getSubjectId());
				}
			}
			assetsService.save(assetsLiqui);//保存财产表
			assetsLiqui.getAssetsRe().setAssetsId(assetsLiqui.getAssetsId());
			assetsReService.save(assetsLiqui.getAssetsRe());//保存财产关联表
		}

		//添加委托机构和网点信息
		ProjectOutlesDealRe entrustCaseeOutlesDealRe =new ProjectOutlesDealRe();
		entrustCaseeOutlesDealRe.setInsId(projectDTO.getEntrustInsId());
		entrustCaseeOutlesDealRe.setOutlesId(projectDTO.getEntrustOutlesId());
		entrustCaseeOutlesDealRe.setType(1);//1-委托机构
		projectDTO.getOutlesList().add(entrustCaseeOutlesDealRe);
		for (ProjectOutlesDealRe projectOutlesDealRe : projectDTO.getOutlesList()) {
			projectOutlesDealRe.setProjectId(projectLiqui.getProjectId());//添加项目id
		}
		projectOutlesDealReService.saveBatch(projectDTO.getOutlesList());

		return projectLiqui;
	}

	/**
	 * 修改项目状态
	 * @param project
	 * @return
	 */
	@Override
	public Integer modifyProjectStatus(Project project){
//		if(project.getStatus() != null && project.getStatus()==1){
//			project.setTakeTime(LocalDateTime.now());//接收时间获取后台时间
//		}
		Integer result = this.baseMapper.updateById(project);
		return result;
	}

	/**
	 * 查询清收项目列表
	 * @param page
	 * @param projectQueryLiquiDTO
	 * @return
	 */
	@Override
	public IPage<ProjectLiquiVO> queryLiquiProjectPage(Page page, ProjectQueryLiquiDTO projectQueryLiquiDTO){
		return this.baseMapper.selectLiquiProject(page,projectQueryLiquiDTO);
	}

	/**
	 * 查询清收项目明细
	 * @param projectId
	 * @return
	 */
	@Override
	public ProjectLiquiVO queryLiquiProjectById(Integer projectId){
		return this.baseMapper.selectLiquiProjectById(projectId);
	}
}
