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
package com.pig4cloud.pig.admin.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 *
 * @author yuanduo
 * @date 2021-12-08 16:21:04
 */
@Data
@TableName("p10_client_user_re")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "client_user_re")
public class ClientUserRe extends Model<ClientUserRe> {

	/**
	 * clientUserId
	 */
	@ApiModelProperty(value="clientUserId")
	@TableId(value = "client_user_id", type = IdType.AUTO)
	private Integer clientUserId;

    /**
     * clientId
     */
    @ApiModelProperty(value="clientId")
    private String clientId;

    /**
     * userId
     */
    @ApiModelProperty(value="userId")
    private String userName;

    /**
     * platform
     */
    @ApiModelProperty(value="platform")
    private String platform;


}
