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

package com.pig4cloud.pig.casee.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.casee.dto.ProjectLiquiPageDTO;
import com.pig4cloud.pig.casee.dto.ProjectModifyStatusDTO;
import com.pig4cloud.pig.casee.entity.Project;
import com.pig4cloud.pig.casee.dto.ProjectLiquiAddDTO;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.vo.ProjectLiquiDetailsVO;
import com.pig4cloud.pig.casee.vo.ProjectLiquiPageVO;
import com.pig4cloud.pig.casee.vo.ProjectSubjectVO;

import java.util.List;

/**
 * 清收项目表
 *
 * @author ligt
 * @date 2022-01-10 15:05:49
 */
public interface ProjectLiquiService extends IService<Project> {

	IPage<ProjectLiquiPageVO> queryPageLiqui(Page page, ProjectLiquiPageDTO projectLiquiPageDTO);

	Integer addProjectLiqui(ProjectLiquiAddDTO projectLiquiAddVO);

	ProjectLiquiDetailsVO getProjectDetails(Integer projectId);

	Integer modifyStatusById(ProjectModifyStatusDTO projectModifyStatusDTO);

	ProjectLiqui getByProjectId(Integer projectId);

	List<ProjectSubjectVO> queryProjectSubjectList(Integer projectId,String subjectName);

}
