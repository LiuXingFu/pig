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
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.casee.entity.Behavior;
import com.pig4cloud.pig.casee.entity.liquientity.detail.BehaviorLiquiDetail;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 行为限制详情
 *
 * @author yuanduo
 * @date 2022-02-14 15:51:27
 */
@Data
public class BehaviorLiqui extends Behavior {

	private BehaviorLiquiDetail behaviorLiquiDetail;

	public void setBehaviorLiquiDetail(BehaviorLiquiDetail behaviorLiquiDetail) {
		this.behaviorLiquiDetail = behaviorLiquiDetail;
		this.setBehaviorDetail(JSON.toJSONString(behaviorLiquiDetail));
	}

	public void initBehaviorLiquiDetail(){
		behaviorLiquiDetail = JSON.parseObject(this.getBehaviorDetail(), BehaviorLiquiDetail.class );
	}

	public BehaviorLiquiDetail getBehaviorLiquiDetail() {
		if(behaviorLiquiDetail==null && this.getBehaviorDetail()!=null){
			behaviorLiquiDetail = JSON.parseObject(this.getBehaviorDetail(), BehaviorLiquiDetail.class );
		}
		return behaviorLiquiDetail;
	}

}
