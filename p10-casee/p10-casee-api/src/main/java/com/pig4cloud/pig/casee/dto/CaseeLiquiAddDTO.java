package com.pig4cloud.pig.casee.dto;

import com.alibaba.fastjson.JSON;
import com.pig4cloud.pig.casee.entity.Casee;
import com.pig4cloud.pig.casee.entity.CaseeSubjectRe;
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
	 * 案件类型(1010:诉前保全案件，2010:诉讼保全案件，2020:一审诉讼案件,，2021:二审诉讼案件，2030:其它案件，3010:首次执行案件，3031:执恢案件)
	 */
	@ApiModelProperty(value = "案件类型(1010:诉前保全案件，2010:诉讼保全案件，2020:一审诉讼案件,，2021:二审诉讼案件，2030:其它案件，3010:首次执行案件，3031:执恢案件)")
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
	 * 申请人/原告/上诉人/申请执行人等集合
	 */
	@ApiModelProperty(value = "申请人/原告/上诉人/申请执行人等集合")
	private List<CaseeSubjectReDTO> applicantList;

	/**
	 * 被告/被执行人/被上诉人等集合
	 */
	@ApiModelProperty(value = "被告/被执行人/被上诉人等集合")
	private List<CaseeSubjectReDTO> executedList;

	/**
	 * 附件
	 */
	@ApiModelProperty(value="附件")
	private String appendix;

	/**
	 * 被告/被执行人/被上诉人等
	 */
	@ApiModelProperty(value="被告/被执行人/被上诉人等")
	private String executedName;

	/**
	 * 申请人/原告/上诉人/申请执行人等
	 */
	@ApiModelProperty(value="申请人/原告/上诉人/申请执行人等")
	private String applicantName;

	/**
	 * 父案件id
	 */
	@ApiModelProperty(value = "父案件id")
	private Integer parentId;

	/**
	 * 类别（0-一审 1-二审 2-首执 3- 执恢）
	 */
	@ApiModelProperty(value = "类别（0-一审 1-二审 2-首执 3- 执恢）")
	private Integer category;

}
