package com.pig4cloud.pig.casee.entity.assets.detail.detailentity;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.entity.assets.detail.detailentity
 * @ClassNAME: 实体财产成交裁定
 * @Author: yd
 * @DATE: 2022/3/4
 * @TIME: 16:37
 * @DAY_NAME_SHORT: 周五
 */
@Data
public class AssetsCompletionRuling extends CommonalityData implements Serializable {

	/**
	 * 裁定日期
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date dateOfAdjudication;

	/**
	 * 拍卖结束执行案号
	 */
	private String caseeNumber;

	/**
	 * 成交裁定文书
	 */
	private String completionRulingFile;

	/**
	 * 备注
	 */
	private String remark;

}
