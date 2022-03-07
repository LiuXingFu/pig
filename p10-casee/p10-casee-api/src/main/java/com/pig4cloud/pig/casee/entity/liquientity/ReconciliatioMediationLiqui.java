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
package com.pig4cloud.pig.casee.entity.liquientity;

import com.alibaba.fastjson.JSON;
import com.pig4cloud.pig.casee.entity.ReconciliatioMediation;
import com.pig4cloud.pig.casee.entity.liquientity.detail.ReconciliatioMediationLiquiDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 和解/调解表
 *
 * @author Mjh
 * @date 2022-03-01 20:36:17
 */
@Data
public class ReconciliatioMediationLiqui extends ReconciliatioMediation {
	@ApiModelProperty(value="和解/调解详情")
	private ReconciliatioMediationLiquiDetail reconciliatioMediationLiquiDetail;

	public void setReconciliatioMediationLiquiDetail(ReconciliatioMediationLiquiDetail reconciliatioMediationLiquiDetail) {
		this.reconciliatioMediationLiquiDetail = reconciliatioMediationLiquiDetail;
		this.setDetails(JSON.toJSONString(reconciliatioMediationLiquiDetail));
	}

	public void initDetails(){
		reconciliatioMediationLiquiDetail = JSON.parseObject(this.getDetails(), ReconciliatioMediationLiquiDetail.class );
	}

	public ReconciliatioMediationLiquiDetail getReconciliatioMediationLiquiDetail() {
		if(reconciliatioMediationLiquiDetail==null && this.getDetails()!=null){
			reconciliatioMediationLiquiDetail = JSON.parseObject(this.getDetails(), ReconciliatioMediationLiquiDetail.class );
		}
		return reconciliatioMediationLiquiDetail;
	}

}
