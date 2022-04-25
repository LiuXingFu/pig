package com.pig4cloud.pig.casee.dto.paifu;

import com.pig4cloud.pig.casee.entity.liquientity.detail.detailentity.AssetsSeizure;
import com.pig4cloud.pig.casee.entity.paifuentity.AssetsRePaifu;
import com.pig4cloud.pig.casee.entity.paifuentity.detail.AssetsRePaifuDetail;
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
public class AssetsRePaifuModifyDTO extends AssetsRePaifu {

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
	 * 备注
	 */
	@ApiModelProperty(value="备注")
	private String remark;

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

}
