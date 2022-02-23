package com.pig4cloud.pig.casee.dto;

import com.alibaba.fastjson.JSON;
import com.pig4cloud.pig.casee.entity.Casee;
import com.pig4cloud.pig.casee.entity.liquientity.detail.CaseeLiquiDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class CaseeLiquiAddDTO {

	/**
	 * 案号
	 */
	@ApiModelProperty(value = "案号")
	private String caseeNumber;

	/**
	 * 案件类型(10000:诉前保全案件 10001:诉讼保全案件 10002:一审诉讼案件 10003:二审诉讼案件 10004:其它案件 10005:首次执行案件 10006:执灰案件)
	 */
	@ApiModelProperty(value = "案件类型(10000:诉前保全案件 10001:诉讼保全案件 10002:一审诉讼案件 10003:二审诉讼案件 10004:其它案件 10005:首次执行案件 10006:执灰案件)")
	private Integer caseeType;

	/**
	 * 立案日期
	 */
	@ApiModelProperty(value = "立案日期")
	private LocalDate startTime;

	/**
	 * 司法费金额
	 */
	@ApiModelProperty(value = "司法费金额")
	private BigDecimal judicialExpenses;

	/**
	 * 承办法院id
	 */
	@ApiModelProperty(value = "承办法院id")
	private Integer courtId;

	/**
	 * 法院部门id
	 */
	@ApiModelProperty(value = "法院部门id")
	private Integer judgeOutlesId;

	/**
	 * 承办法官id
	 */
	@ApiModelProperty(value = "承办法官id")
	private Integer judgeId;

	/**
	 * 法官名称
	 */
	@ApiModelProperty(value = "法官名称")
	private String judgeName;

	/**
	 * 案件总金额
	 */
	@ApiModelProperty(value = "案件总金额")
	private BigDecimal caseeAmount;

	@ApiModelProperty(value = "清收案件详情表")
	private CaseeLiquiDetail caseeLiquiDetail;

	/**
	 * 项目id
	 */
	@ApiModelProperty(value = "项目id")
	private Integer projectId;

	/**
	 * 办理人id
	 */
	@ApiModelProperty(value = "办理人id")
	private Integer userId;

	/**
	 * 办理人名称
	 */
	@ApiModelProperty(value = "办理人名称")
	private String actualName;

	/**
	 * 债务人集合
	 */
	@ApiModelProperty(value = "债务人集合")
	private List<Integer> subjectIdList;

	/**
	 * 附件
	 */
	@ApiModelProperty(value="附件")
	private String appendix;


}
