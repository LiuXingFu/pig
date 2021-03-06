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
import com.pig4cloud.pig.admin.api.dto.InsOutlesUserByOutlesDTO;
import com.pig4cloud.pig.admin.api.entity.InsOutlesUser;
import com.pig4cloud.pig.admin.api.vo.InsOutlesUserInsOutlesVO;
import com.pig4cloud.pig.admin.api.vo.InsOutlesUserListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 机构/网点用户关联表
 *
 * @author xls
 * @date 2022-01-27 19:30:49
 */
@Mapper
public interface InsOutlesUserMapper extends BaseMapper<InsOutlesUser> {

	List<InsOutlesUserListVO> selectUserList(@Param("type") Integer type,@Param("insId") Integer insId,@Param("outlesId") Integer outlesId);

	List<InsOutlesUserInsOutlesVO> selectOutlesName(@Param("userId") Integer userId,@Param("insId")Integer insId);

	List<InsOutlesUserInsOutlesVO> selectInsName(@Param("userId") Integer userId);

	IPage<InsOutlesUserInsOutlesVO> queryInsOutlesUserByOutles(Page page, @Param("query") InsOutlesUserByOutlesDTO insOutlesUserByOutlesDTO);

	InsOutlesUserInsOutlesVO queryById(Integer insOutlesUserId);
}
