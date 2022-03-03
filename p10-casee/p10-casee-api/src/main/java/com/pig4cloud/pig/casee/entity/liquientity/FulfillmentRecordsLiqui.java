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
import com.pig4cloud.pig.casee.entity.FulfillmentRecords;
import com.pig4cloud.pig.casee.entity.liquientity.detail.FulfillmentRecordsDetail;
import com.pig4cloud.pig.casee.entity.liquientity.detail.TransferRecordLiquiDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 待履行记录表
 *
 * @author Mjh
 * @date 2022-03-01 20:36:31
 */
@Data
public class FulfillmentRecordsLiqui extends FulfillmentRecords {
	@ApiModelProperty(value="待履行记录详情")
	private FulfillmentRecordsDetail fulfillmentRecordsDetail;

	public void setFulfillmentRecordsDetail(FulfillmentRecordsDetail fulfillmentRecordsDetail) {
		this.fulfillmentRecordsDetail = fulfillmentRecordsDetail;
		this.setDetails(JSON.toJSONString(fulfillmentRecordsDetail));
	}

	public void initDetails(){
		fulfillmentRecordsDetail = JSON.parseObject(this.getDetails(), FulfillmentRecordsDetail.class );
	}

	public FulfillmentRecordsDetail getFulfillmentRecordsDetail() {
		if(fulfillmentRecordsDetail==null && this.getDetails()!=null){
			fulfillmentRecordsDetail = JSON.parseObject(this.getDetails(), FulfillmentRecordsDetail.class );
		}
		return fulfillmentRecordsDetail;
	}
}
