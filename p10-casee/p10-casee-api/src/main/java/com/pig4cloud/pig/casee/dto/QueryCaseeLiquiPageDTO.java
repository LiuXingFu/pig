package com.pig4cloud.pig.casee.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class QueryCaseeLiquiPageDTO {

	/**
	 * 公司业务案号/案号
	 */
	private String caseeNumber;

	/**
	 * 申请人/被执行人
	 */
	private String subjectName;

	/**
	 * 法院
	 */
	private String courtName;

	/**
	 * 法官
	 */
	private String judgeName;

	/**
	 * 案件类型(1010:诉前保全案件，2010:诉讼保全案件，2020:一审诉讼案件,2021:二审诉讼案件，2030:其它案件，3010:首次执行案件，3031:执恢案件)
	 */
	private Integer caseeType;

	/**
	 * 案件状态(0-待立案 1-在办 2- 撤案 3-结案 4-终结)
	 */
	private Integer status;

	/**
	 * 类型(-1-全部 0-保全案件 1-诉讼案件 2-执行案件）
	 */
	private Integer totalType;

	/**
	 * 案件类型集合(1010:诉前保全案件，2010:诉讼保全案件，2020:一审诉讼案件，2021:二审诉讼案件，2030:其它案件，3010:首次执行案件，3031:执恢案件)
	 */
	private List<Integer> caseeTypeList;

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

}
