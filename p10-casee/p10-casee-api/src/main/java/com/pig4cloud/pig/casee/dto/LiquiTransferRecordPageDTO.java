package com.pig4cloud.pig.casee.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LiquiTransferRecordPageDTO {

	@ApiModelProperty(value = "状态")
	private Integer status;

	@ApiModelProperty(value = "债务人名称")
	private String subjectName;

	@ApiModelProperty(value="开始时间")
	private LocalDate beginDate;

	@ApiModelProperty(value="结束时间")
	private LocalDate endDate;

}
