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
import com.pig4cloud.pig.casee.dto.CaseeLawsuitsDTO;
import com.pig4cloud.pig.casee.dto.CaseeLiquiAddDTO;
import com.pig4cloud.pig.casee.dto.CaseeLiquiPageDTO;
import com.pig4cloud.pig.casee.dto.CaseeReinstatementDTO;
import com.pig4cloud.pig.casee.entity.Casee;
import com.pig4cloud.pig.casee.entity.liquientity.CaseeLiqui;
import com.pig4cloud.pig.casee.vo.CaseeLiquiDetailsVO;
import com.pig4cloud.pig.casee.vo.CaseeLiquiPageVO;
import com.pig4cloud.pig.casee.vo.CaseeListVO;

import java.util.List;

/**
 * 案件表
 *
 * @author yy
 * @date 2021-09-15 10:03:22
 */
public interface CaseeLiquiService extends IService<Casee> {

	/**
	 * 添加基本案件
	 * @param caseeLiquiAddDTO
	 * @return
	 * @throws Exception
	 */
	Integer addCaseeLiqui(CaseeLiquiAddDTO caseeLiquiAddDTO) throws Exception;

	/**
	 * 添加保全案件
	 * @return
	 * @throws Exception
	 */
	Integer addPreservationCasee(CaseeLiquiAddDTO caseeLiquiAddDTO) throws Exception;

	/**
	 * 添加诉讼案件
	 * @return
	 * @throws Exception
	 */
	Integer addLawsuitsCasee(CaseeLawsuitsDTO caseeLawsuitsDTO) throws Exception;

	/**
	 * 添加首执案件
	 * @return
	 * @throws Exception
	 */
	Integer addExecutionCasee(CaseeLiquiAddDTO caseeLiquiAddDTO) throws Exception;

	/**
	 * 添加执恢案件
	 * @return
	 * @throws Exception
	 */
	Integer addReinstatementCasee(CaseeReinstatementDTO caseeReinstatementDTO) throws Exception;

	/**
	 * 根据项目id、案件类型集合查询案件信息
	 * @param projectId
	 * @param caseeType
	 * @return
	 */
	List<CaseeListVO> queryByIdList(Integer projectId,List<Integer> caseeType);

	/**
	 * 根据项目id、案件类型查询一审或首执案件信息
	 * @param projectId
	 * @param caseeType
	 * @return
	 */
	CaseeLiqui getCaseeParentId(Integer projectId,Integer caseeType);

	/**
	 * 根据项目id、案件状态集合查询案件信息
	 * @param projectId
	 * @param statusList
	 * @return
	 */
	CaseeLiqui queryByStatusList(Integer projectId,List<Integer> statusList);

	/**
	 * 分页查询清收案件
	 * @param page
	 * @param caseeLiquiPageDTO
	 * @return
	 */
	IPage<CaseeLiquiPageVO> queryPage(Page page, CaseeLiquiPageDTO caseeLiquiPageDTO);

	CaseeLiquiDetailsVO queryByCaseeId(Integer caseeId);
}
