package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.casee.entity.Assets;
import lombok.Data;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.vo
 * @ClassNAME: CaseeOrAssetsVO
 * @Author: yd
 * @DATE: 2022/2/20
 * @TIME: 16:48
 * @DAY_NAME_SHORT: 周日
 */
@Data
public class CaseeOrAssetsVO extends Assets {

	/**
	 * 抵押情况
	 */
	private Integer assetsSource;

	/**
	 * 面积/余额
	 */
	private double areaOrBalance;

	/**
	 * 是否首冻(0-否 1-是)
	 */
	private String whetherFirstFrozen;

	/**
	 * 查封情况(0-首封 1-轮候)
	 */
	private String sealUpCondition;

}
