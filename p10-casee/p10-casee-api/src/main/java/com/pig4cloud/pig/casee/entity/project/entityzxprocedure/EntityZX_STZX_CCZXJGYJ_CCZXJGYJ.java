package com.pig4cloud.pig.casee.entity.project.entityzxprocedure;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.admin.api.entity.FileAdder;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 财产执行价格依据
 */
@Data
public class EntityZX_STZX_CCZXJGYJ_CCZXJGYJ extends CommonalityData implements Serializable {

	/**
	 * 定价日期
	 */
	@JSONField(format="yyyy-MM-dd")
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
