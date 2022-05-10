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
package com.pig4cloud.pig.casee.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务人员关联引领看样表
 *
 * @author Mjh
 * @date 2022-04-29 10:49:17
 */
@Data
@TableName("p10_lead_the_way_user_re")
@ApiModel(value = "业务人员关联引领看样表")
public class LeadTheWayUserRe {

    /**
     * 业务人员关联引领看样表id
     */
    @TableId
    @ApiModelProperty(value="业务人员关联引领看样表id")
    private Integer leadTheWayUserReId;

    /**
     * 引领看样表id
     */
    @ApiModelProperty(value="引领看样表id")
    private Integer leadTheWayId;

    /**
     * 业务人员表id
     */
    @ApiModelProperty(value="业务人员表id")
    private Integer userId;


}
