package com.pig4cloud.pig.casee.entity.project.beillegalprocedure;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实施行为违法
 */
@Data
public class BeIllegal_XWWF_SSXWWF_SSXWWF extends CommonalityData implements Serializable {
	/**
	 * 限制日期
	 */
	@JSONField(format="yyyy-MM-dd")
	private Date limitTime;

	/**
	 * 案号
	 */
	private String caseNumber;

	/**
	 * 制裁行为类型(0-拘留 1-罚款 2-移送侦查机关)
	 */
	private Integer sanctionsType;

	/**
	 * 拘留天数
	 */
	private Integer daysOfDetention;

	/**
	 * 拘留结束日期
	 */
	private String detentionEndDate;

	/**
	 * 费用金额
	 */
	private BigDecimal expenses;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 附件
	 */
	private String appendixFile;
}
