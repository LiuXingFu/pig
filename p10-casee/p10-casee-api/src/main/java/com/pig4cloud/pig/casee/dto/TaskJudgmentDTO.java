package com.pig4cloud.pig.casee.dto;

import com.pig4cloud.pig.casee.entity.TaskNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TaskJudgmentDTO extends TaskNode {

	/**
	 * 是否继续执行 0-否 1-是
	 */
	@ApiModelProperty(value="是否继续执行 0-否 1-是")
	private int canContinueExecution;

}
