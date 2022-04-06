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
import com.pig4cloud.pig.casee.dto.paifu.ProjectPaifuPageDTO;
import com.pig4cloud.pig.casee.entity.Project;
import com.pig4cloud.pig.casee.vo.paifu.ProjectPaifuPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 拍辅项目表
 *
 * @author ligt
 * @date 2022-01-10 15:05:49
 */
@Mapper
public interface ProjectPaifuMapper extends BaseMapper<Project> {

	IPage<ProjectPaifuPageVO> selectPagePaifu(Page page, @Param("query") ProjectPaifuPageDTO projectPaifuPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);
}
