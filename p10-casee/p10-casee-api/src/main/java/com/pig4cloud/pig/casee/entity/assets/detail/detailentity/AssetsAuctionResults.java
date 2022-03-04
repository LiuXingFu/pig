package com.pig4cloud.pig.casee.entity.assets.detail.detailentity;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.entity.assets.detail.detailentity
 * @ClassNAME: 实体财产拍卖结果
 * @Author: yd
 * @DATE: 2022/3/4
 * @TIME: 15:55
 * @DAY_NAME_SHORT: 周五
 */
@Data
public class AssetsAuctionResults extends CommonalityData implements Serializable {

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
	private Integer numberOfParticipants;

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
