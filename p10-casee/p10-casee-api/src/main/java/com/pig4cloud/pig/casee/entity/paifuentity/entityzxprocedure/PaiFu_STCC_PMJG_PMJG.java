package com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 拍卖结果
 */
@Data
public class PaiFu_STCC_PMJG_PMJG extends CommonalityData implements Serializable {
	/**
	 * 拍卖结果(0-成交 1-流拍)
	 */
	private Integer auctionResults;

	/**
	 * 成交日期
	 */
	private LocalDate closingDate;

	/**
	 * 成交价格
	 */
	private Double dealPrice;

	/**
	 * 参拍人数
	 */
	private Integer numberOfParticipants;

	/**
	 * 买受人
	 */
	private String buyer;

	/**
	 * 拍辅费用
	 */
	private BigDecimal auxiliaryFee;

	/**
	 * 附件
	 */
	private String appendixFile;

	/**
	 * 备注
	 */
	private String remark;
}
