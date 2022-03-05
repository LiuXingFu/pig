package com.pig4cloud.pig.casee.entity.project.liquiprocedure.ZXZH;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 执行阶段执恢法院到款
 */
@Data
public class LiQui_ZXZH_ZXZHFYDK_ZXZHFYDK extends CommonalityData implements Serializable {
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
	 * 执行案号
	 */
	private String caseeNumber;

	/**
	 * 附件
	 */
	private String appendixFile;

	/**
	 * 备注
	 */
	private String remark;

}
