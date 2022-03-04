package com.pig4cloud.pig.casee.entity.assets.detail.detailentity;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.entity.assets.detail.detailentity
 * @ClassNAME: 实体财产腾退成功
 * @Author: yd
 * @DATE: 2022/3/4
 * @TIME: 16:43
 * @DAY_NAME_SHORT: 周五
 */
@Data
public class AssetsEvacuateSuccessfully extends CommonalityData implements Serializable {

	/**
	 * 腾退日期
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date vacateDate;

	/**
	 * 附件
	 */
	private String appendixFile;

	/**
	 * 备注
	 */
	private String remark;

}
