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

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 流程节点模板表
 *
 * @author xiaojun
 * @date 2021-09-07 17:01:38
 */
@Data
@TableName("p10_task_node_template")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "流程节点模板表")
public class TaskNodeTemplate extends BaseEntity {

    /**
     * 模板ID
     */
    @TableId
    @ApiModelProperty(value="模板ID")
    private Integer templateId;

    /**
     * 模板名称
     */
    @ApiModelProperty(value="模板名称")
    private String templateName;

    /**
     * 模板内容
     */
    @ApiModelProperty(value="模板内容")
    private String templateContent;

    /**
     * 模板类型（100-拍辅机构 200-清收机构）
     */
    @ApiModelProperty(value="模板类型（100-拍辅机构 200-清收机构）")
    private Integer templateType;

    /**
     * 创建者所属机构ID
     */
    @ApiModelProperty(value="创建者所属机构ID")
    private Integer insUserId;

    /**
     * 是否默认模板（0：否, 1:是）
     */
    @ApiModelProperty(value="是否默认模板（0：否, 1:是）")
    private Integer isDefault;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 0-正常，1-删除
     */
    @ApiModelProperty(value="0-正常，1-删除")
    private String delFlag;

	/**
	 * 模板性质 清收机构（程序性质1010:诉前保全案件 2010:诉讼保全案件 2020:一审诉讼案件 2021:二审诉讼案件 2030:其它案件 3010:首次执行案件 3031:执灰案件  4040:实体财产程序 4041:资金财产程序5050:行为限制程序 5051:行为违法程序 6060:拍卖程序 7070:履行程序）））
	 */
	@ApiModelProperty(value="模板性质 清收机构（程序性质1010:诉前保全案件 2010:诉讼保全案件 2020:一审诉讼案件 2021:二审诉讼案件 2030:其它案件 3010:首次执行案件 3031:执灰案件  4040:实体财产程序 4041:资金财产程序5050:行为限制程序 5051:行为违法程序 6060:拍卖程序 7070:履行程序））")
	private Integer templateNature;
}
