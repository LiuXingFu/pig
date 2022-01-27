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
import com.pig4cloud.pig.admin.api.entity.Region;
import com.pig4cloud.pig.admin.api.vo.RegionVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 地址级联关系表
 *
 * @author yuanduo
 * @date 2021-09-23 15:37:44
 */
@Mapper
public interface RegionMapper extends BaseMapper<Region> {

	List<RegionVO> getProvinceList(Integer parentId);
}
