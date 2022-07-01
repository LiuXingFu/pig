package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProjectLiQuiAndSubjectListVO {

	/**
	 * 项目信息
	 */
	private ProjectLiqui projectLiqui;

	/**
	 * 债务人集合
	 */
	List<ProjectSubjectVO> projectSubjectVOList;

}
