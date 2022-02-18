package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.casee.entity.Assets;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.vo
 * @ClassNAME: AssetsDeailsVO
 * @Author: yd
 * @DATE: 2022/2/17
 * @TIME: 16:19
 * @DAY_NAME_SHORT: 周四
 */
@Data
public class AssetsDeailsVO extends Assets {

	/**
	 * 财产地址id
	 */
	@ApiModelProperty(value = "财产地址id")
	private Integer addressId;

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
	 * 行政区划编号
	 */
	@ApiModelProperty(value = "行政区划编号")
	private String code;


}
