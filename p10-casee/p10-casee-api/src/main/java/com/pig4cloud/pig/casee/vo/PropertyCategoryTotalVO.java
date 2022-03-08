package com.pig4cloud.pig.casee.vo;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.vo
 * @ClassNAME: 财产分类实体
 * @Author: yd
 * @DATE: 2022/3/8
 * @TIME: 19:44
 * @DAY_NAME_SHORT: 周二
 */
@Data
public class PropertyCategoryTotalVO {

	/**
	 * 财产类型
	 */
	private Integer type;

	/**
	 * 财产数量
	 */
	private Long numberOfProperties;

}
