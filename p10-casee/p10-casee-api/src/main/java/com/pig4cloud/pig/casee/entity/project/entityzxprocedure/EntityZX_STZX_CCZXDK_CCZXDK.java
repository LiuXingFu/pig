package com.pig4cloud.pig.casee.entity.project.entityzxprocedure;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.ShareEntity.PaymentRecord;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 财产执行到款
 */
@Data
public class EntityZX_STZX_CCZXDK_CCZXDK extends CommonalityData implements Serializable {
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
