package com.pig4cloud.pig.casee.vo.paifu.count;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.liquientity.AssetsReLiqui;
import com.pig4cloud.pig.casee.entity.paifuentity.AssetsRePaifu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AssetsRePaifuFlowChartPageVO extends AssetsRePaifu {

	/**
	 * 公司业务案号
	 */
	@ApiModelProperty(value="公司业务案号")
	private String companyCode;

	/**
	 * 项目id
	 */
	@ApiModelProperty(value="项目id")
	private Integer projectId;

	/**
	 * 节点id
	 */
	@ApiModelProperty(value="节点id")
	private String nodeId;

	/**
	 * 案号
	 */
	@ApiModelProperty(value="案号")
	private String caseeNumber;

	/**
	 * 案件id
	 */
	@ApiModelProperty(value="案件id")
	private Integer caseeId;

	/**
	 * 财产名称
	 */
	@ApiModelProperty(value="财产名称")
	private String assetsName;

	/**
	 * 财产类型（20100-资金财产 20200-实体财产）
	 */
	@ApiModelProperty(value="财产类型（20100-资金财产 20200-实体财产）")
	private Integer type;

	/**
	 * 财产性质（资金财产：(20101:银行存款，20102：住房公积金)，实体财产：（20201：房产，20202：车辆，20203：股权，20204：土地，20205：其它））
	 */
	@ApiModelProperty(value="财产性质（资金财产：(20101:银行存款，20102：住房公积金)，实体财产：（20201：房产，20202：车辆，20203：股权，20204：土地，20205：其它））")
	private Integer assetsType;

	/**
	 * 所有权人
	 */
	@ApiModelProperty(value="所有权人")
	private String owner;

	/**
	 * 财产账号/编号
	 */
	@ApiModelProperty(value="财产账号/编号")
	private String accountNumber;

	/**
	 * 拍卖类型（100-一拍，200-二拍，300-变卖）
	 */
	@ApiModelProperty(value="拍卖类型（100-一拍，200-二拍，300-变卖）")
	private Integer auctionType;

	/**
	 * 当前拍卖状态（100-即将开始，200-正在进行，300-已结束，400-中止，500-撤回）
	 */
	@ApiModelProperty(value="当前拍卖状态（100-即将开始，200-正在进行，300-已结束，400-中止，500-撤回）")
	private Integer auctionStatus;

	/**
	 * 拍卖记录id
	 */
	@ApiModelProperty(value="拍卖记录id")
	private Integer auctionRecordId;

	/**
	 * 公告发布时间
	 */
	@ApiModelProperty(value="公告发布时间")
	private LocalDate announcementStartTime;

	/**
	 * 拍卖开始时间
	 */
	@ApiModelProperty(value="拍卖开始时间")
	private LocalDate auctionStartTime;

	/**
	 * 拍卖结束时间
	 */
	@ApiModelProperty(value="拍卖结束时间")
	private LocalDate auctionEndTime;

	/**
	 * 节点名称
	 */
	@ApiModelProperty(value="节点名称")
	private String nodeName;

}
