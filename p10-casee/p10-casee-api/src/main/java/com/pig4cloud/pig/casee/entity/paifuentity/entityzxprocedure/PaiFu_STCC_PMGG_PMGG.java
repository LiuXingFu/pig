package com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure;

import com.pig4cloud.pig.casee.dto.JointAuctionAssetsDTO;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 拍卖公告
 */
@Data
public class PaiFu_STCC_PMGG_PMGG extends CommonalityData implements Serializable {
	/**
	 * 拍卖id
	 */
	private Integer auctionId;

	/**
	 * 拍卖记录id
	 */
	private Integer auctionRecordId;

	/**
	 * 拍卖公告阶段(100-一拍 200-二拍 300-变卖)
	 */
	private Integer auctionType;

	/**
	 * 标题
	 */
	private String auctionTitle;

	/**
	 * 公告发布时间
	 */
	private LocalDate announcementStartTime;

	/**
	 * 起拍价
	 */
	private BigDecimal startingPrice;

	/**
	 * 拍卖平台(0-淘宝司法拍卖 1-京东司法拍卖 2-公拍网 3-人民法院诉讼资产网 4-中国拍卖行业协会 5-工商银行融e购 6-北京产权交易所 7-其它网拍平台)
	 */
	private Integer auctionPlatform;

	/**
	 * 拍卖链接
	 */
	private String auctionLink;

	/**
	 * 拍卖开始日期
	 */
	private LocalDate auctionStartTime;

	/**
	 * 拍卖结束日期
	 */
	private LocalDate auctionEndTime;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 附件
	 */
	private String appendix;

	/**
	 * 拍卖财产
	 */
	List<JointAuctionAssetsDTO> jointAuctionAssetsDTOList;

}
