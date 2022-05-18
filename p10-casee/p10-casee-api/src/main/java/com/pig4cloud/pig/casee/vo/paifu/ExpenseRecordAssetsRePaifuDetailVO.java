package com.pig4cloud.pig.casee.vo.paifu;

import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.casee.entity.paifuentity.AssetsRePaifu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 案件财产表
 */
@Data
public class ExpenseRecordAssetsRePaifuDetailVO extends AssetsRePaifu {

	/**
	 * 财产名称
	 */
	@ApiModelProperty(value="财产名称")
	private String assetsName;

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
	 * 债务人id集合
	 */
	@ApiModelProperty(value="债务人id集合")
	private List<Integer> subjectIdList;

	/**
	 * 债务人集合
	 */
	private List<Subject> subjectList;

	/**
	 * 地址id
	 */
	@ApiModelProperty(value = "地址id")
	private Integer addressId;

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

	/**
	 * 拍卖id
	 */
	@ApiModelProperty(value = "拍卖id")
	private Integer auctionId;

	/**
	 * 禁用
	 */
	@ApiModelProperty(value = "禁用")
	private Boolean disabled;

}
