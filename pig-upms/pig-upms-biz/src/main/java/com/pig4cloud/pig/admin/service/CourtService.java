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

package com.pig4cloud.pig.admin.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.admin.api.entity.Court;
import com.pig4cloud.pig.admin.api.entity.Outles;
import com.pig4cloud.pig.admin.api.vo.InsOutlesCourtReVO;

import java.util.List;


/**
 *
 * @author Mjh
 * @date 2021-08-17 16:22:59
 */
public interface CourtService extends IService<Court> {

	/**
	 * 通过地区code或法院名称查询法院信息
	 *
	 * @param regionCode 地区code
	 * @return R
	 */
	List<Court> getByRegionCodeOrCourtName(Integer regionCode,String courtName);

	/**
	 * 通过名称查询相应法院
	 * @param page
	 * @param court
	 * @return
	 */
	IPage<Court> getCourtPageList(Page page, Court court);

}
