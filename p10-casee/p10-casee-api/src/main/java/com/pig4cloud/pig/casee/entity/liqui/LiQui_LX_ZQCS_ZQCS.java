package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.admin.api.entity.FileAdder;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import com.pig4cloud.pig.casee.entity.liqui.detail.CollectionRecordsList;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 执前催收
 */
@Data
public class LiQui_LX_ZQCS_ZQCS extends CommonalityData implements Serializable {

	/**
	 * 执行和解协议 (enforcementSettlementAgreement)
	 */
	@ApiModelProperty("执行和解协议")
	private List<FileAdder> enforcementSettlementAgreementUrl;

	/**
	 * 访谈表
	 */
	@ApiModelProperty("访谈表")
	private List<FileAdder> interviewFormUrl;

	/**
	 * 催收记录列表
	 */
	@ApiModelProperty("催收记录列表")
	List<CollectionRecordsList> collectionRecordsList = new ArrayList<>();

}
