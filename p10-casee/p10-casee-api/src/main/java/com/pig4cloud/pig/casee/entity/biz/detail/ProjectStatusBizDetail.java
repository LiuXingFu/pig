package com.pig4cloud.pig.casee.entity.biz.detail;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProjectStatusBizDetail {

	/**
	 * 附件
	 */
	@ApiModelProperty(value="附件")
	private String appendix;

	/**
	 * 状态选择类型名称
	 */
	@ApiModelProperty(value="状态选择类型名称")
	private String statusNameType;

}
