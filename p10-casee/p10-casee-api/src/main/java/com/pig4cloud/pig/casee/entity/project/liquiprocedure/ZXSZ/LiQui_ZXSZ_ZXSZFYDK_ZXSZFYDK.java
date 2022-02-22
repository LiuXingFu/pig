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
 * 执行阶段首次执行法院到款
 */
@Data
public class LiQui_ZXSZ_ZXSZFYDK_ZXSZFYDK extends CommonalityData implements Serializable {
	/**
	 * 到款时间
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date arrivalTime;

	/**
	 * 到款类型(0-法院执行到款 1-法院履行款)
	 */
	private Integer arrivalType;

	/**
	 * 到款金额
	 */
	private BigDecimal arrivalTotalAmount;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 附件
	 */
	private String appendixFile;
}
