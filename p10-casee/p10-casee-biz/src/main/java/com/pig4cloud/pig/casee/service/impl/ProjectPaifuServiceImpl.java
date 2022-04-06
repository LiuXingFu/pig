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
import com.pig4cloud.pig.casee.dto.paifu.ProjectPaifuSaveDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.paifuentity.ProjectPaifu;
import com.pig4cloud.pig.casee.mapper.ProjectPaifuMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import org.springframework.stereotype.Service;

/**
 * 拍辅项目表
 */
@Service
public class ProjectPaifuServiceImpl extends ServiceImpl<ProjectPaifuMapper, Project> implements ProjectPaifuService {

	@Override
	public Integer saveProjectCasee(ProjectPaifuSaveDTO projectPaifuSaveDTO){
		ProjectPaifu projectPaifu = new ProjectPaifu();
		projectPaifu.setProjectType(200);
		BeanCopyUtil.copyBean(projectPaifuSaveDTO,projectPaifu);
		Casee casee = new Casee();
		BeanCopyUtil.copyBean(projectPaifuSaveDTO,casee);

		ProjectCaseeRe projectCaseeRe = new ProjectCaseeRe();
		projectCaseeRe.setProjectId(projectPaifu.getProjectId());
		projectCaseeRe.setCaseeId(casee.getCaseeId());



		return 0;
	}

}
