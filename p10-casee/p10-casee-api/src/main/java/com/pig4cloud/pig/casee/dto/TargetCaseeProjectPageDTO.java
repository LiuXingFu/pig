package com.pig4cloud.pig.casee.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.dto
 * @ClassNAME: TargeOrCaseeOrProject
 * @Author: yd
 * @DATE: 2022/2/21
 * @TIME: 17:30
 * @DAY_NAME_SHORT: 周一
 */
@Data
public class TargetCaseeProjectPageDTO {

	/**
	 * 案件类型(1010:诉前保全案件，2010:诉讼保全案件，2020:一审诉讼案件,，2021:二审诉讼案件，2030:其它案件，3010:首次执行案件，3031:执恢案件)
	 */
	@ApiModelProperty(value="案件类型(1010:诉前保全案件，2010:诉讼保全案件，2020:一审诉讼案件,，2021:二审诉讼案件，2030:其它案件，3010:首次执行案件，3031:执恢案件)")
	private Integer caseeType;

	/**
	 * 公司业务案号/案件案号
	 */
	@ApiModelProperty(value = "公司业务案号/案件案号")
	private String caseeNumber;

	/**
	 * 债务人
	 */
	@ApiModelProperty(value="债务人")
	private String executedName;

	// 立案时间范围搜索
	/**
	 * 开始时间
	 */
	@ApiModelProperty(value="开始时间")
	private LocalDate beginDate;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(value="结束时间")
	private LocalDate endDate;

	/**
	 * 办理人名称
	 */
	@ApiModelProperty(value="办理人名称")
	private String actualName;

}
