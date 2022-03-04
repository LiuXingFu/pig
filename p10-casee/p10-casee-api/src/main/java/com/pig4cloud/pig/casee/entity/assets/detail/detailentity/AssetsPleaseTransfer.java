package com.pig4cloud.pig.casee.entity.assets.detail.detailentity;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.entity.assets.detail.detailentity
 * @ClassNAME: 实体资产商请移送实体类
 * @Author: yd
 * @DATE: 2022/3/4
 * @TIME: 14:11
 * @DAY_NAME_SHORT: 周五
 */
@Data
public class AssetsPleaseTransfer extends CommonalityData implements Serializable {

	/**
	 * 商请移送日期
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date pleaseTransferDate;

	/**
	 * 商请移送情况(0-移送处置权成功 1-移送处置权失败)
	 */
	private Integer businessPleaseTransfer;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 附件
	 */
	private String appendixFile;
}
