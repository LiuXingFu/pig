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
import com.pig4cloud.pig.casee.dto.paifu.CaseeSubjectReListDTO;
import com.pig4cloud.pig.casee.dto.paifu.ProjectPaifuPageDTO;
import com.pig4cloud.pig.casee.dto.paifu.ProjectPaifuSaveDTO;
import com.pig4cloud.pig.casee.dto.paifu.ProjectSubjectReSaveDTO;
import com.pig4cloud.pig.casee.entity.Project;
import com.pig4cloud.pig.casee.vo.paifu.ProjectPaifuDetailVO;
import com.pig4cloud.pig.casee.vo.paifu.ProjectPaifuPageVO;
import com.pig4cloud.pig.casee.vo.paifu.ProjectSubjectReListVO;

import java.util.List;


/**
 * 拍辅项目表
 */
public interface ProjectPaifuService extends IService<Project> {

	Integer saveProjectCasee(ProjectPaifuSaveDTO projectPaifuSaveDTO);

	/**
	 * 分页查询列表
	 * @param page
	 * @param projectPaifuPageDTO
	 * @return
	 */
	IPage<ProjectPaifuPageVO> queryProjectCaseePage(Page page, ProjectPaifuPageDTO projectPaifuPageDTO);

	/**
	 * 查询项目案件详情
	 * @param projectId
	 * @return
	 */
	ProjectPaifuDetailVO queryProjectCaseeDetail(Integer projectId);

	/**
	 * 保存项目主体关联表
	 * @return
	 */
	Integer addProjectSubjectRe(ProjectSubjectReSaveDTO projectSubjectReSaveDTO);

	/**
	 * 验证项目主体
	 * @return
	 */
	ProjectSubjectReListVO queryProjectSubjectRe(Integer projectId,String unifiedIdentity);

}
