package com.pig4cloud.pig.casee.entity.assets.detail.detailentity;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.entity.assets.detail.detailentity
 * @ClassNAME: 资产处置移交
 * @Author: yd
 * @DATE: 2022/3/4
 * @TIME: 14:25
 * @DAY_NAME_SHORT: 周五
 */
@Data
public class AssetsDispositionTransfer extends CommonalityData implements Serializable {
	/**
	 * 拍卖申请书
	 */
	private String auctionApplicationFile;

	/**
	 * 申请提交时间
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date applicationSubmissionTime;

	/**
	 * 备注
	 */
	private String remark;
}
