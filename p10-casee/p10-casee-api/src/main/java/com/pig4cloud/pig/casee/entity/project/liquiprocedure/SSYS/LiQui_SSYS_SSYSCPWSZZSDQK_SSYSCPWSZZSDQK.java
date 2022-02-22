package com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSYS;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.ShareEntity.ReceiptRecord;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 诉讼阶段一审裁判文书最终送达情况
 */
@Data
public class LiQui_SSYS_SSYSCPWSZZSDQK_SSYSCPWSZZSDQK extends CommonalityData implements Serializable {

	/**
	 * 签收记录
	 */
	private List<ReceiptRecord> receiptRecordList;


	/**
	 * 最终送达情况备注
	 */
	private String finalDeliveryRemark;

	/**
	 * 附件
	 */
	private String appendixFile;
}
