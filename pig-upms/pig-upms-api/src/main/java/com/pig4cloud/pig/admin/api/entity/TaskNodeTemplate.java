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
	 * 模板性质 清收机构（10000:诉前保全案件 10001:诉讼保全案件 10002:一审诉讼案件 10003:二审诉讼案件 10004:其它案件 10005:首次执行案件 10006:执灰案件
	 * 20000:保全实体财产程序 20001:保全资金财产程序 20002:实体财产程序 20003:资金财产程序 20004:行为限制程序 20005:行为违法程序 20006:拍卖程序））
	 */
	@ApiModelProperty(value="模板性质 清收机构（10000:诉前保全案件 10001:诉讼保全案件 10002:一审诉讼案件 10003:二审诉讼案件 10004:其它案件 10005:首次执行案件 10006:执灰案件 " +
			"20000:保全实体财产程序 20001:保全资金财产程序 20002:实体财产程序 20003:资金财产程序 20004:行为限制程序 20005:行为违法程序 20006:拍卖程序）")
	private Integer templateNature;
}
