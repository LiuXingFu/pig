package com.pig4cloud.pig.casee.entity.assets.detail.detailentity;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.entity.assets.detail.detailentity
 * @ClassNAME: 实体财产不动产现勘入户
 * @Author: yd
 * @DATE: 2022/3/4
 * @TIME: 15:34
 * @DAY_NAME_SHORT: 周五
 */
@Data
public class AssetsRealEstateExplorationHome extends CommonalityData implements Serializable {

	/**
	 * 装修情况(0-毛坯 1-简装 2-中装 3-精装 4-豪装)
	 */
	private Integer renovationCondition;

	/**
	 * 实际户型
	 */
	private String houseType;

	/**
	 * 是否租赁(0-否 1-是)
	 */
	private Integer whetherLease;

	/**
	 * 是否居住(0-否 1-是)
	 */
	private Integer whetherLive;

	/**
	 * 财产所有人配合度
	 */
	private Integer propertyOwnerCooperation;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 附件
	 */
	private String appendixFile;
}
