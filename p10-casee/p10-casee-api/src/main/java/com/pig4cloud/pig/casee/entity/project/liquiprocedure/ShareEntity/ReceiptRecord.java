package com.pig4cloud.pig.casee.entity.project.liquiprocedure.ShareEntity;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 签收记录
 */
@Data
public class ReceiptRecord extends CommonalityData implements Serializable {
	/**
	 * 签收方(0-原告 1-被告 2-第三人)
	 */
	private Integer signingParty;

	/**
	 * 签收方名字
	 */
	private String signingPartyName;

	/**
	 * 送达方式(0-直接送达 1-邮寄送达 2-留置送达 3-公告送达 4-短信送达)
	 */
	private Integer deliveryMethod;

	/**
	 * 最终签收日期
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date finalReceiptTime;

	/**
	 * 备注
	 */
	private String remark;

}
