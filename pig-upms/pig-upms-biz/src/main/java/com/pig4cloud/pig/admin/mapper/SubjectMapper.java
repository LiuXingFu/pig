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

package com.pig4cloud.pig.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.dto.SubjectPageDTO;
import com.pig4cloud.pig.admin.api.dto.SubjectProjectCaseeDTO;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.vo.SubjectDetailsVO;
import com.pig4cloud.pig.admin.api.vo.SubjectGetByIdVO;
import com.pig4cloud.pig.admin.api.vo.SubjectProjectCaseeVO;
import com.pig4cloud.pig.admin.api.vo.SubjectVO;
import com.pig4cloud.pig.casee.dto.InsOutlesDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 主体
 *
 * @author yy
 * @date 2021-09-17 16:55:57
 */
@Mapper
public interface SubjectMapper extends BaseMapper<Subject> {

	SubjectVO getByUnifiedIdentity(String unifiedIdentity);

	SubjectGetByIdVO getBySubjectId(Integer subjectId);

	IPage<Subject> pageSubject(Page page, @Param("query") SubjectPageDTO subjectPageDTO);

	SubjectVO selectSubjectById(Integer subjectId);

	IPage<SubjectProjectCaseeVO> selectPageByProjectId(Page page, @Param("query") SubjectProjectCaseeDTO subjectProjectCaseeDTO);

	IPage<SubjectProjectCaseeVO> selectPageByCaseeId(Page page, @Param("query") SubjectProjectCaseeDTO subjectProjectCaseeDTO);

	SubjectDetailsVO getSubjectDetailBySubjectId(Integer subjectId);

	int getIsThereASubjectByUnifiedIdentity(@Param("unifiedIdentity") String unifiedIdentity, @Param("insId") Integer insId);

	Subject getByInsId(@Param("insId") Integer insId);

	List<Subject> getSubjectByBankLoanId(Integer bankLoanId);

	IPage<Subject> selectPageList(Page page, @Param("query") SubjectPageDTO subjectPageDTO,@Param("login") InsOutlesDTO insOutlesDTO);

	SubjectVO getByPhone(@Param("phone") String phone, @Param("name") String name);

	SubjectVO getByUnifiedIdentityAndPhone(@Param("unifiedIdentity") String unifiedIdentity, @Param("phone") String phone);
}
