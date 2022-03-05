package com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSES;

import com.alibaba.fastjson.annotation.JSONField;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.ShareEntity.ReceiptRecord;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 诉讼阶段二审裁判文书最终送达情况
 */
@Data
public class LiQui_SSES_SSESCPWSZZSDQK_SSESCPWSZZSDQK extends CommonalityData implements Serializable {

	/**
	 * 签收记录
	 */
	private List<ReceiptRecord> receiptRecordList;

	/**
	 * 裁判结果生效日期
	 */
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date effectiveDate;

	/**
	 * 最终送达情况备注
	 */
	private String finalDeliveryRemark;

	/**
	 * 附件
	 */
	private String appendixFile;
}
