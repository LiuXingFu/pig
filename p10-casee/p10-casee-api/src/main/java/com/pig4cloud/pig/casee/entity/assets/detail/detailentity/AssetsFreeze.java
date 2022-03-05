package com.pig4cloud.pig.casee.entity.assets.detail.detailentity;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 资金资产冻结实体
 */
@Data
public class AssetsFreeze extends CommonalityData implements Serializable {
	/**
	 * 冻结时间
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date freezeTime;

	/**
	 * 冻结到期日
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date freezeExpirationDate;

	/**
	 * 是否首冻(0-否 1-是)
	 */
	private Integer whetherFirstFrozen;

	/**
	 * 冻结金额
	 */
	private Integer frozenAmount;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 附件
	 */
	private String appendixFile;
}
