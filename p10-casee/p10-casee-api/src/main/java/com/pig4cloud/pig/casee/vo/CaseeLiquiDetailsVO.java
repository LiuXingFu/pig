package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.casee.entity.liquientity.CaseeLiqui;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.vo
 * @ClassNAME: CaseeLiquiDetailsVO
 * @Author: yd
 * @DATE: 2022/2/20
 * @TIME: 15:37
 * @DAY_NAME_SHORT: 周日
 */
@Data
public class CaseeLiquiDetailsVO extends CaseeLiqui {

	/**
	 * 程序id
	 */
	@ApiModelProperty(value="程序id")
	private Integer targetId;

	/**
	 * 项目id
	 */
	@ApiModelProperty(value="项目id")
	private Integer projectId;

	/**
	 * 公司业务案号
	 */
	@ApiModelProperty(value="公司业务案号")
	private String companyCode;

	/**
	 * 机构id
	 */
	@ApiModelProperty(value="机构id")
	private Integer insId;

	/**
	 * 网点id
	 */
	@ApiModelProperty(value="网点id")
	private Integer outlesId;

	/**
	 * 代理律师名称
	 */
	@ApiModelProperty(value="代理律师名称")
	private String lawyerName;

	/**
	 * 法院名称
	 */
	@ApiModelProperty(value="法院名称")
	private String courtName;

	/**
	 * 办理人名称
	 */
	@ApiModelProperty(value="办理人名称")
	private String userName;

	/**
	 * 债务人集合
	 */
	@ApiModelProperty(value="债务人集合")
	List<CaseeOrSubjectVO> caseeOrSubjectVOList;

	/**
	 * 财产集合
	 */
	@ApiModelProperty(value = "财产集合")
	List<CaseeOrAssetsVO> caseeOrAssetsVOList;

}
