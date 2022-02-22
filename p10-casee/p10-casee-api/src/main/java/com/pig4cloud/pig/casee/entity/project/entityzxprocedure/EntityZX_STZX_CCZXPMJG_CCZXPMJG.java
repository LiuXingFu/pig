package com.pig4cloud.pig.casee.entity.project.entityzxprocedure;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 财产执行拍卖结果
 */
@Data
public class EntityZX_STZX_CCZXPMJG_CCZXPMJG extends CommonalityData implements Serializable {
	/**
	 * 拍卖结果(0-成交 1-流拍)
	 */
	private Integer auctionResults;

	/**
	 * 成交日期
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date closingDate;

	/**
	 * 成交价格
	 */
	private Double dealPrice;

	/**
	 * 参拍人数
	 */
	private Integer NumberOfParticipants;

	/**
	 * 买受人
	 */
	private String buyer;

	/**
	 * 拍辅费用
	 */
	private Double auxiliaryFee;

	/**
	 * 附件
	 */
	private String appendixFile;

	/**
	 * 备注
	 */
	private String remark;
}
