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
import com.pig4cloud.pig.casee.dto.InsOutlesDTO;
import com.pig4cloud.pig.casee.dto.ProjectLiquiPageDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.mapper.ProjectLiquiMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.dto.ProjectLiquiAddDTO;
import com.pig4cloud.pig.casee.vo.ProjectLiquiPageVO;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 清收项目表
 *
 * @author ligt
 * @date 2022-01-10 15:05:49
 */
@Service
public class ProjectLiquiServiceImpl extends ServiceImpl<ProjectLiquiMapper, Project> implements ProjectLiquiService {

	@Autowired
	private JurisdictionUtilsService jurisdictionUtilsService;

	@Override
	public IPage<ProjectLiquiPageVO> queryPageLiqui(Page page, ProjectLiquiPageDTO projectLiquiPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectPageLiqui(page,projectLiquiPageDTO,insOutlesDTO);
	}

	@Override
	public Integer addProjectLiqui(ProjectLiquiAddDTO projectLiquiAddVO){
		ProjectOutlesDealRe projectOutlesDealRe = new ProjectOutlesDealRe();
		projectOutlesDealRe.setInsId(projectLiquiAddVO.getInsId());
		projectOutlesDealRe.setOutlesId(projectLiquiAddVO.getOutlesId());
		projectOutlesDealRe.setUserId(projectLiquiAddVO.getUserId());



		return 0;
	}


}
