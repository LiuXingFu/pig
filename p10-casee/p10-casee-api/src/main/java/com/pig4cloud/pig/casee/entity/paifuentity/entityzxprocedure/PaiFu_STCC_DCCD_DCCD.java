package com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 抵偿裁定
 */
@Data
public class PaiFu_STCC_DCCD_DCCD extends CommonalityData implements Serializable {
	/**
	 * 裁定日期
	 */
	@JSONField(format="yyyy-MM-dd")
	private Date dateOfAdjudication;

	/**
	 * 拍卖结束执行案号
	 */
	private String caseeNumber;

	/**
	 * 抵偿裁定文书
	 */
	private String completionRulingFile;

	/**
	 * 备注
	 */
	private String remark;

}
