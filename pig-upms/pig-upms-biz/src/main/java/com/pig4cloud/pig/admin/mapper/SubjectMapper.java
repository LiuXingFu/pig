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

import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.vo.SubjectGetByIdVO;
import com.pig4cloud.pig.admin.api.vo.SubjectVO;
import org.apache.ibatis.annotations.Mapper;

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

}
