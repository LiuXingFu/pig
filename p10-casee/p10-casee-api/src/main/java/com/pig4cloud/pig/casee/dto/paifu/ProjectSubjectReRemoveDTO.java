package com.pig4cloud.pig.casee.dto.paifu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProjectSubjectReRemoveDTO {

	/**
	 * 项目id
	 */
	@ApiModelProperty(value = "项目id")
	private Integer projectId;

	/**
	 * 案件id
	 */
	@ApiModelProperty(value = "案件id")
	private Integer caseeId;

	/**
	 * 主体id
	 */
	@ApiModelProperty(value = "主体id")
	private Integer subjectId;

	/**
	 * 主体名称
	 */
	@ApiModelProperty(value = "主体名称")
	private String name;

	/**
	 * 类型（0-申请人/原告/上述人/申请执行人等，1-被告/被执行人/被上述人等）
	 */
	@ApiModelProperty(value = "类型（0-申请人/原告/上述人/申请执行人等，1-被告/被执行人/被上述人等）")
	private Integer caseePersonnelType;

}
