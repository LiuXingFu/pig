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
import com.pig4cloud.pig.casee.dto.ReconciliatioMediationDTO;
import com.pig4cloud.pig.casee.entity.ReconciliatioMediation;
import com.pig4cloud.pig.casee.entity.liquientity.ReconciliatioMediationLiqui;
import com.pig4cloud.pig.casee.vo.ReconciliatioMediationVO;

/**
 * 和解/调解表
 *
 * @author Mjh
 * @date 2022-03-01 20:36:17
 */
public interface ReconciliatioMediationLiquiService extends IService<ReconciliatioMediation> {
	IPage<ReconciliatioMediationVO> getReconciliatioMediationPage(Page page, ReconciliatioMediationDTO reconciliatioMediationDTO);

	ReconciliatioMediationVO getByReconciliatioMediationId(Integer reconciliatioMediationId);

	boolean saveReconciliatioMediation(ReconciliatioMediationDTO reconciliatioMediationDTO);

	boolean cancellation(ReconciliatioMediationLiqui reconciliatioMediation);

	/**
	 * 查询较去年和解数
	 * @return
	 */
	Long queryCompareReconciliationCount();

}
