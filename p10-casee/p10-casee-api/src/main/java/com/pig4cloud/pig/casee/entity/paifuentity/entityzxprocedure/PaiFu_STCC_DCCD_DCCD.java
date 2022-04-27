package com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 抵偿裁定
 */
@Data
public class PaiFu_STCC_DCCD_DCCD extends CommonalityData implements Serializable {
	/**
	 * 裁定日期
	 */
	private LocalDate dateOfAdjudication;

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
