package com.pig4cloud.pig.casee.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.liquientity.AssetsReLiqui;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AssetsReLiquiFlowChartPageVO extends AssetsReLiqui {

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
	 * 开拍时间
	 */
	@ApiModelProperty(value="开拍时间")
	private LocalDate auctionStartDate;

	/**
	 * 拍卖公告阶段(0-一拍 1-二拍 2-变卖)
	 */
	private Integer auctionAnnouncementStage;

	/**
	 * 拍卖结束日期
	 */
	@JSONField(format="yyyy-MM-dd")
	private LocalDate auctionEndDate;

	/**
	 * 节点名称
	 */
	@ApiModelProperty(value="节点名称")
	private String nodeName;

	/**
	 * 拍卖id
	 */
	private Integer auctionRecordId;

}
