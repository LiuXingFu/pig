package com.pig4cloud.pig.casee.entity.assets.detail.detailentity;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.entity.assets.detail.detailentity
 * @ClassNAME: 实体财产到款实体类
 * @Author: yd
 * @DATE: 2022/3/4
 * @TIME: 16:14
 * @DAY_NAME_SHORT: 周五
 */
@Data
public class AssetsPayment extends CommonalityData implements Serializable {

	/**
	 * 到款金额
	 */
	private Double amountReceived;

	/**
	 * 最终到款时间
	 */
	@JSONField(format="yyyy-MM-dd")
	private Date finalPaymentDate;

	/**
	 * 最终付款人
	 */
	private String finalPayer;

	/**
	 * 附件
	 */
	private String appendixFile;

	/**
	 * 备注
	 */
	private String remark;

}
