package com.pig4cloud.pig.casee.entity.project.entityzxprocedure;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 财产执行拍卖公告
 */
@Data
public class EntityZX_STZX_CCZXPMGG_CCZXPMGG extends CommonalityData implements Serializable {

	/**
	 * 拍卖公告阶段(0-一拍 1-二拍 2-变卖)
	 */
	private Integer auctionAnnouncementStage;

	/**
	 * 拍卖撤销(0-未撤销 1-撤销)
	 */
	private Integer revoke=0;

	/**
	 * 撤销日期
	 */
	@JSONField(format="yyyy-MM-dd")
	private Date revocationDate;

	/**
	 * 撤销附件
	 */
	private String revocationFile;

	/**
	 * 撤销备注
	 */
	private String revocationRemark;

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
