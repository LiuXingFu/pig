package com.pig4cloud.pig.casee.vo;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableId;
import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.casee.entity.Assets;
import com.pig4cloud.pig.casee.entity.liquientity.detail.AssetsReCaseeDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AssetsPaifuVO extends Assets {

	/**
	 * 地址id 主键自增
	 */
	@TableId
	@ApiModelProperty(value = "地址id 主键自增")
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

	@ApiModelProperty(value = "财产关联id")
	private String assetsReId;

	@ApiModelProperty(value = "财产关联详情")
	private String assetsReDetail;

	@ApiModelProperty(value = "项目案件关联财产详情")
	private AssetsReCaseeDetail assetsReCaseeDetail;

	@ApiModelProperty(value = "债务人id")
	private Integer subjectId;

	@ApiModelProperty(value = "抵押物记录id")
	private Integer mortgageAssetsRecordsId;

	@ApiModelProperty(value = "债务人id集合")
	private List<Integer> subjectIds;

	public void setAssetsReCaseeDetail(AssetsReCaseeDetail assetsReCaseeDetail) {
		this.assetsReCaseeDetail = assetsReCaseeDetail;
		this.setAssetsReDetail(JSON.toJSONString(assetsReCaseeDetail));
	}

	public void initAssetsReCaseeDetail() {
		assetsReCaseeDetail = JSON.parseObject(this.getAssetsReDetail(), AssetsReCaseeDetail.class);
	}

	public AssetsReCaseeDetail getAssetsReCaseeDetail() {
		if (assetsReCaseeDetail == null && this.getAssetsReDetail() != null) {
			assetsReCaseeDetail = JSON.parseObject(this.getAssetsReDetail(), AssetsReCaseeDetail.class);
		}
		return assetsReCaseeDetail;
	}

}
