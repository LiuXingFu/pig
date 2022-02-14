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

package com.pig4cloud.pig.casee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.casee.dto.InsOutlesDTO;
import com.pig4cloud.pig.casee.dto.ProjectLiquiPageDTO;
import com.pig4cloud.pig.casee.entity.Project;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.vo.ProjectLiquiDetailsVO;
import com.pig4cloud.pig.casee.vo.ProjectLiquiOrBehaviorPageVO;
import com.pig4cloud.pig.casee.vo.ProjectLiquiPageVO;
import com.pig4cloud.pig.casee.vo.ProjectSubjectVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 清收项目表
 *
 * @author ligt
 * @date 2022-01-10 15:05:49
 */
@Mapper
public interface ProjectLiquiMapper extends BaseMapper<Project> {

	IPage<ProjectLiquiPageVO> selectPageLiqui(Page page, @Param("query") ProjectLiquiPageDTO projectLiquiPageDTO, @Param("login")InsOutlesDTO insOutlesDTO);

	ProjectLiquiDetailsVO selectByProjectId(@Param("projectId") Integer projectId);

	List<ProjectSubjectVO> selectProjectSubject(@Param("projectId") Integer projectId,@Param("subjectName")String subjectName);

	ProjectLiqui getByProjectId(@Param("projectId") Integer projectId);

	IPage<ProjectLiquiOrBehaviorPageVO> queryPageProjectLiqui(Page page, @Param("subjectId") Integer subjectId);
}
