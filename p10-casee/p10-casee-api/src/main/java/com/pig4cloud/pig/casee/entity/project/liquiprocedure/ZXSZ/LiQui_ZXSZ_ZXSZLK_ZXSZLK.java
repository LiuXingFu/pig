package com.pig4cloud.pig.casee.entity.project.liquiprocedure.ZXSZ;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.ShareEntity.PaymentRecord;
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
	 * 领款时间
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date collectionTime;

	/**
	 * 领款金额
	 */
	private BigDecimal paymentAmount;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 领款来源(0-法院领款 1-履行到申请人)
	 */
	private Integer sourceOfPayment;

	/**
	 * 分配款项记录
	 */
	private List<PaymentRecord> paymentRecordList;

	/**
	 * 附件
	 */
	private String appendixFile;
}
