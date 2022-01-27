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
import com.pig4cloud.pig.admin.api.entity.StaffDeptRe;
import com.pig4cloud.pig.admin.api.entity.SysDept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 员工部门表
 *
 * @author yuanduo
 * @date 2021-09-22 11:25:02
 */
@Mapper
public interface StaffDeptReMapper extends BaseMapper<StaffDeptRe> {

	List<SysDept> listDeptListByStaffId(Integer staffId);

	List<Integer> listDeptIdListByStaffId(Integer staffId);

}
