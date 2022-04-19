package com.pig4cloud.pig.casee.dto.paifu;

import com.pig4cloud.pig.casee.entity.liquientity.detail.detailentity.AssetsSeizure;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 案件财产表
 *
 * @author ligt
 * @date 2022-01-11 10:29:44
 */
@Data
public class AssetsRePaifuSaveDTO {

	/**
	 * 财产id
	 */
	@ApiModelProperty(value="财产id")
	private Integer assetsId;

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
	 * 项目id
	 */
	@ApiModelProperty(value="项目id")
	private Integer projectId;

	/**
	 * 案件id
	 */
	@ApiModelProperty(value="案件id")
	private Integer caseeId;

	/**
	 * 债务人名称
	 */
	@ApiModelProperty(value="债务人名称")
	private String subjectName;

	/**
	 * 债务人id集合
	 */
	@ApiModelProperty(value="债务人id集合")
	private List<Integer> subjectIdList;

	/**
	 * 备注
	 */
	@ApiModelProperty(value="备注")
	private String remark;

	/**
	 *   0: 申请人,1: 案外人,2: 无抵押权人
	 */
	@ApiModelProperty(value = "0: 申请人,1: 案外人,2: 无抵押权人")
	private Integer mortgagee;

	/**
	 * 抵押时间
	 */
	@ApiModelProperty(value = "抵押开始时间")
	private LocalDate mortgageStartTime;

	/**
	 * 抵押时间
	 */
	@ApiModelProperty(value = "抵押结束时间")
	private LocalDate mortgageEndTime;

	/**
	 * 抵押金额
	 */
	@ApiModelProperty(value = "抵押金额")
	private BigDecimal mortgageAmount;

	/**
	 * 实体资产查封实体
	 */
	@ApiModelProperty(value = "实体资产查封")
	private AssetsSeizure assetsSeizure;

	/**
	 * 省
	 */
	@ApiModelProperty(value = "省")
	private String province;
	/**
	 * 市
	 */
	@ApiModelProperty(value = "市")
	private String city;
	/**
	 * 区
	 */
	@ApiModelProperty(value = "区")
	private String area;
	/**
	 * 信息地址
	 */
	@ApiModelProperty(value = "信息地址")
	private String informationAddress;
	/**
	 * 行政区划编号
	 */
	@ApiModelProperty(value = "行政区划编号")
	private String code;

}
