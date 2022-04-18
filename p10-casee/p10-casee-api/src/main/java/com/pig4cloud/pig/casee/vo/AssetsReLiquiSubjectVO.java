package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.casee.entity.Assets;
import com.pig4cloud.pig.casee.entity.liquientity.AssetsReLiqui;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 案件财产表
 *
 * @author ligt
 * @date 2022-01-11 10:29:44
 */
@Data
public class AssetsReLiquiSubjectVO extends AssetsReLiqui {

	/**
	 * 财产信息
	 */
	private Assets assets;

	/**
	 * 省
	 */
	@ApiModelProperty(value = "省")
	private String province;
	/**
	 * 市
	 */
	@ApiModelProperty(value = "市")
	private String city;
	/**
	 * 区
	 */
	@ApiModelProperty(value = "区")
	private String area;
	/**
	 * 信息地址
	 */
	@ApiModelProperty(value = "信息地址")
	private String informationAddress;

	/**
	 * 项目id
	 */
	@ApiModelProperty(value="项目id")
	private Integer projectId;

	/**
	 * 项目状态
	 */
	@ApiModelProperty(value="项目状态")
	private Integer projectStatus;

	/**
	 * 公司业务案号
	 */
	@ApiModelProperty(value="公司业务案号")
	private String companyCode;

	/**
	 * 案件id
	 */
	@ApiModelProperty(value="案件id")
	private Integer caseeId;

	/**
	 * 案号
	 */
	@ApiModelProperty(value="案号")
	private String caseeNumber;

	/**
	 * 案件状态
	 */
	@ApiModelProperty(value="案件状态")
	private Integer caseeStatus;


}
