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
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 项目案件财产关联主体
 *
 * @author pig code generator
 * @date 2022-04-14 17:09:52
 */
@Data
@TableName("p10_assets_re_subject")
@ApiModel(value = "项目案件财产关联主体")
public class AssetsReSubject {

    /**
     * 主键id
     */
    @TableId
    @ApiModelProperty(value="主键id")
    private Integer assetsReSubjectId;

    /**
     * 项目案件财产关联id
     */
    @ApiModelProperty(value="项目案件财产关联id")
    private Integer assetsReId;

    /**
     * 主体id
     */
    @ApiModelProperty(value="主体id")
    private Integer subjectId;


}
