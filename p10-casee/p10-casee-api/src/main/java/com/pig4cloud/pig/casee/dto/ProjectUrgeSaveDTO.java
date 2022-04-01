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
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 项目督促表
 *
 * @author pig code generator
 * @date 2022-03-10 20:53:32
 */
@Data
public class ProjectUrgeSaveDTO {

    /**
     * 项目id
     */
    @ApiModelProperty(value="项目id")
    private Integer projectId;

    /**
     * 案件id
     */
    @ApiModelProperty(value="案件id")
    private Integer caseeId;

    /**
     * 待履行记录id
     */
    @ApiModelProperty(value="待履行记录id")
    private Integer fulfillmentRecordId;

    /**
     * 债务人
     */
    @ApiModelProperty(value="债务人")
    private Integer subjectId;

    /**
     * 债务人名称
     */
    @ApiModelProperty(value="债务人名称")
    private String subjectName;

    /**
     * 督促电话
     */
    @ApiModelProperty(value="督促电话")
    private String urgePhone;
    /**
     * 督促日期
     */
    @ApiModelProperty(value="督促日期")
    private LocalDate urgeTime;

    /**
     * 督促形式（1-当面，2-电话，3-短信）
     */
    @ApiModelProperty(value="督促形式（1-当面，2-电话，3-短信）")
    private Integer urgeType;

    /**
     * 督促依据（100-借贷合同，200-判决书/调解书，300-和解协议）
     */
    @ApiModelProperty(value="督促依据（100-借贷合同，200-判决书/调解书，300-和解协议）")
    private Integer urgeAccording;

    /**
     * 督促结果（1-达成履行协议，2-不能履行）
     */
    @ApiModelProperty(value="督促结果（1-达成履行协议，2-不能履行）")
    private Integer urgeResult;

    /**
     * 附件
     */
    @ApiModelProperty(value="附件")
    private String appendix;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;


}
