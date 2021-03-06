package com.pig4cloud.pig.casee.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
public class AssetsReLiquiFlowChartPageDTO {

	/**
	 * 案件类型(1010:诉前保全案件，2010:诉讼保全案件，2020:一审诉讼案件,，2021:二审诉讼案件，2030:其它案件，3010:首次执行案件，3031:执恢案件)
	 */
	@ApiModelProperty(value="案件类型(1010:诉前保全案件，2010:诉讼保全案件，2020:一审诉讼案件,，2021:二审诉讼案件，2030:其它案件，3010:首次执行案件，3031:执恢案件)")
	private Integer caseeType;

	/**
	 * 公司业务案号/案件案号
	 */
	@ApiModelProperty(value = "公司业务案号/案件案号")
	private String caseeNumber;

	/**
	 * 财产名称
	 */
	@ApiModelProperty(value="财产名称")
	private String assetsName;

	/**
	 * 债务人
	 */
	@ApiModelProperty(value="债务人")
	private String executedName;

	/**
	 * 节点KEY
	 */
	@ApiModelProperty(value="节点KEY")
	private String nodeKey;

	/**
	 * 财产类型（20100-资金财产 20200-实体财产）
	 */
	@ApiModelProperty(value="财产类型（20100-资金财产 20200-实体财产）")
	private Integer type;

	/**
	 * 财产性质（资金财产：(20101:银行存款，20102：住房公积金)，实体财产：（20201：房产，20202：车辆，20203：股权，20204：土地，20205：其它））
	 */
	@ApiModelProperty(value="财产性质（资金财产：(20101:银行存款，20102：住房公积金)，实体财产：（20201：房产，20202：车辆，20203：股权，20204：土地，20205：其它））")
	private List<Integer> assetsTypeList;

	/**
	 * 拍卖结果(0-成交 1-流拍)
	 */
	private Integer auctionResults;
}
