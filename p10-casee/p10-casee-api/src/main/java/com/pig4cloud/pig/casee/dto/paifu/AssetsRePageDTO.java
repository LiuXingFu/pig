package com.pig4cloud.pig.casee.dto.paifu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AssetsRePageDTO {

	@ApiModelProperty(value="标的名称")
	private String assetsName;

	@ApiModelProperty(value="所有权人")
	private String owner;

	@ApiModelProperty(value="资产编号")
	private String accountNumber;

	@ApiModelProperty(value="开始时间")
	private LocalDate beginDate;

	@ApiModelProperty(value="结束时间")
	private LocalDate endDate;

	@ApiModelProperty(value="项目id")
	private Integer projectId;

	@ApiModelProperty(value="案件id")
	private Integer caseeId;

}
