package com.pig4cloud.pig.casee.entity.project.liquiprocedure.ZXSZ;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.ShareEntity.PaymentRecord;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.ShareEntity.ReceiptRecord;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 执行阶段首次执行领款
 */
@Data
public class LiQui_ZXSZ_ZXSZLK_ZXSZLK extends CommonalityData implements Serializable {
	/**
	 * 到款时间
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date arrivalTime;

	/**
	 * 到款总金额
	 */
	private BigDecimal arrivalTotalAmount;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 款项来源(0-法院执行到款 1-自动履行到法院 2-自动履行到申请人)
	 */
	private Integer sourceOfPayment;

	/**
	 * 款项记录
	 */
	private List<PaymentRecord> paymentRecordList;
}
