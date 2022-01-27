package com.pig4cloud.pig.casee.entity.liqui;

import com.pig4cloud.pig.admin.api.entity.FileAdder;
import com.pig4cloud.pig.casee.entity.CommonalityData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 执前审查
 */
@Data
public class LiQui_LX_ZQSC_ZQSC extends CommonalityData implements Serializable {

	/**
	 * 强制执行申请书
	 */
	@ApiModelProperty("强制执行申请书")
	private List<FileAdder> enforcementApplicationUrl;

	/**
	 * 执行立案审批表
	 */
	@ApiModelProperty("执行立案审批表")
	private List<FileAdder> carryOutTheCaseApprovalFormUrl;

	/**
	 * 执行法院
	 */
	@ApiModelProperty("执行法院")
	private String enforcementCourtId;

	/**
	 * 承办法官
	 */
	@ApiModelProperty("承办法官")
	private String acceptanceOfficerId;

	/**
	 * 执行案号
	 */
	@ApiModelProperty("执行案号")
	private String executionCaseNumber;

}
