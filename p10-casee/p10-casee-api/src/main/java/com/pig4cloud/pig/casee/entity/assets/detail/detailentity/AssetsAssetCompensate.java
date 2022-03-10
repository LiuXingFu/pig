package com.pig4cloud.pig.casee.entity.assets.detail.detailentity;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.entity.assets.detail.detailentity
 * @ClassNAME: 实体财产资产抵偿
 * @Author: yd
 * @DATE: 2022/3/4
 * @TIME: 16:35
 * @DAY_NAME_SHORT: 周五
 */
@Data
public class AssetsAssetCompensate extends CommonalityData implements Serializable {
	/**
	 * 抵偿金额
	 */
	private Double compensationAmount;

	/**
	 * 抵偿日期
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date settlementDate;

	/**
	 * 抵偿人
	 */
	private String indemnifier;

	/**
	 * 拍辅费用
	 */
	private Double AuxiliaryFee;

	/**
	 * 附件
	 */
	private String appendixFile;

	/**
	 * 备注
	 */
	private String remark;
}
