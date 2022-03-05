package com.pig4cloud.pig.casee.entity.liquientity.detail.detailentity;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.entity.liquientity.detail.detailentity
 * @ClassNAME: 行为限制限制撤销
 * @Author: yd
 * @DATE: 2022/3/5
 * @TIME: 9:45
 * @DAY_NAME_SHORT: 周六
 */
@Data
public class BehaviorRestrictionsRevoke extends CommonalityData implements Serializable {

	/**
	 * 撤销日期
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date revokeTime;

	/**
	 * 撤销原因
	 */
	private String reasonForRevocation;

	/**
	 * 撤销依据
	 */
	private String groundsForRevocation;

	/**
	 * 附件
	 */
	private String appendixFile;

	/**
	 * 备注
	 */
	private String remark;

}
