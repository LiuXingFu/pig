package com.pig4cloud.pig.casee.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LiquiTransferRecordPageDTO {

	@ApiModelProperty(value = "清收机构")
	private String insName;

	@ApiModelProperty(value = "清收网点")
	private String outlesName;

	@ApiModelProperty(value = "状态")
	private Integer status;

	@ApiModelProperty(value = "申请人/被执行人")
	private String subjectName;

	@ApiModelProperty(value="开始时间")
	private LocalDate beginDate;

	@ApiModelProperty(value="结束时间")
	private LocalDate endDate;

}
