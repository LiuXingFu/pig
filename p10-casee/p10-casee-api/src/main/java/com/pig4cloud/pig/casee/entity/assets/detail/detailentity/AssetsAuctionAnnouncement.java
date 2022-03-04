package com.pig4cloud.pig.casee.entity.assets.detail.detailentity;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.entity.assets.detail.detailentity
 * @ClassNAME: 实体财产拍卖公告
 * @Author: yd
 * @DATE: 2022/3/4
 * @TIME: 15:40
 * @DAY_NAME_SHORT: 周五
 */
@Data
public class AssetsAuctionAnnouncement extends CommonalityData implements Serializable {

	/**
	 * 拍卖公告阶段(0-一拍 1-二拍 2-变卖)
	 */
	private Integer auctionAnnouncementStage;

	/**
	 * 公告发布时间
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date announcementReleaseTime;

	/**
	 * 起拍价
	 */
	private Double startingPrice;

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
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date auctionStartDate;

	/**
	 * 拍卖结束日期
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date auctionEndDate;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 附件
	 */
	private String appendixFile;

}
