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
package com.pig4cloud.pig.casee.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 项目主体关联表
 *
 * @author ligt
 * @date 2022-01-11 14:52:12
 */
@Data
public class ProjectSubjectReModifyDTO {

    /**
     * id
     */
    @ApiModelProperty(value="id")
    private Integer subjectReId;

    /**
     * 类型（0-申请人，1-借款人，2-共同借款人，3-担保人）
     */
    @ApiModelProperty(value="类型（0-申请人，1-借款人，2-共同借款人，3-担保人）")
    private Integer type;

	/**
	 * 主体id
	 */
	@ApiModelProperty(value = "主体id")
	private Integer subjectId;

	/**
	 * 性质类型 0-个人 1-企业/公司
	 */
	@ApiModelProperty(value = "性质类型 0-个人 1-企业/公司")
	private Integer natureType;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 联系方式
	 */
	@ApiModelProperty(value = "联系方式")
	private String phone;

	/**
	 * 法人
	 */
	@ApiModelProperty(value = "法人")
	private String legalPerson;

	/**
	 * 电子邮件
	 */
	@ApiModelProperty(value = "电子邮件")
	private String email;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	/**
	 * 工作单位
	 */
	@ApiModelProperty(value = "工作单位")
	private String employer;

	/**
	 * 性别（1-男，2-女）默认值0-不详
	 */
	@ApiModelProperty(value = "性别（1-男，2-女）默认值0-不详")
	private Integer gender;

	/**
	 * 民族
	 */
	@ApiModelProperty(value = "民族")
	private String ethnic;




}
