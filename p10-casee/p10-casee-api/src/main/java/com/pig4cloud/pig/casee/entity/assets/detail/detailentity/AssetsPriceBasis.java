package com.pig4cloud.pig.casee.entity.assets.detail.detailentity;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.entity.assets.detail.detailentity
 * @ClassNAME: 实体财产价格依据
 * @Author: yd
 * @DATE: 2022/3/4
 * @TIME: 15:38
 * @DAY_NAME_SHORT: 周五
 */
@Data
public class AssetsPriceBasis extends CommonalityData implements Serializable {

	/**
	 * 定价日期
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date pricingDate;

	/**
	 * 定价方式(0-议价 1-网络询价 2-询价 3-评估)
	 */
	private Integer pricingManner;

	/**
	 * 定价费用
	 */
	private Double pricingFee;

	/**
	 * 定价价格
	 */
	private Double listPrice;

	/**
	 * 附件
	 */
	private String appendixFile;

	/**
	 * 备注
	 */
	private String remark;
}
