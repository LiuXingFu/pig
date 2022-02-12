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
import com.pig4cloud.pig.casee.entity.TransferRecord;
import com.pig4cloud.pig.casee.entity.liquientity.detail.TransferRecordLiquiDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 移送记录表
 *
 * @author Mjh
 * @date 2022-01-28 18:52:40
 */
@Data
public class TransferRecordLiqui extends TransferRecord {
	@ApiModelProperty(value="移送详情表")
	private TransferRecordLiquiDetail transferRecordLiquiDetail;

	public void setTransferRecordLiquiDetail(TransferRecordLiquiDetail transferRecordLiquiDetail) {
		this.transferRecordLiquiDetail = transferRecordLiquiDetail;
		this.setTransferDetail(JSON.toJSONString(transferRecordLiquiDetail));
	}

	public void initTransferRecordLiquiDetail(){
		transferRecordLiquiDetail = JSON.parseObject(this.getTransferDetail(), TransferRecordLiquiDetail.class );
	}

	public TransferRecordLiquiDetail getTransferRecordLiquiDetail() {
		if(transferRecordLiquiDetail==null && this.getTransferDetail()!=null){
			transferRecordLiquiDetail = JSON.parseObject(this.getTransferDetail(), TransferRecordLiquiDetail.class );
		}
		return transferRecordLiquiDetail;
	}


}
