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
import com.pig4cloud.pig.admin.api.entity.OutlesTemplateRe;
import com.pig4cloud.pig.admin.api.entity.TaskNodeTemplate;
import com.pig4cloud.pig.admin.api.entity.UserOutlesStaffRe;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 网点任务模板关联表
 *
 * @author Mjh
 * @date 2021-12-17 11:31:08
 */
@Mapper
public interface OutlesTemplateReMapper extends BaseMapper<OutlesTemplateRe> {

	List<OutlesTemplateRe> getByOutlesId(Integer outlesId);

	List<Integer>getTemplateIdByOutlesId(Integer outlesId);

	TaskNodeTemplate queryTemplateByTemplateNature(@Param("templateNature") Integer templateNature,@Param("outlesId") Integer outlesId);

	void updateByOutlesOrRoleType(@Param("query") UserOutlesStaffRe updateRoleType, @Param("userId") Integer userId, @Param("roleType") Integer roleType);
}
