package com.pig4cloud.pig.casee.entity.assets.detail.detailentity;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.entity.assets.detail.detailentity
 * @ClassNAME: 资金财产划扣
 * @Author: yd
 * @DATE: 2022/3/4
 * @TIME: 21:14
 * @DAY_NAME_SHORT: 周五
 */
@Data
public class AssetsSnap extends CommonalityData implements Serializable {

	/**
	 * 划扣时间
	 */
	@JSONField(format="yyyy-MM-dd")
	private Date deductionTime;

	/**
	 * 划扣金额
	 */
	private Integer deductionAmount;

	/**
	 * 附件
	 */
	private String appendixFile;

	/**
	 * 备注
	 */
	private String remark;
}
