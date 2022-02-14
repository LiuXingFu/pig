package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.casee.entity.Project;
import lombok.Data;

import java.util.List;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.vo
 * @ClassNAME: ProjectLiquiOrTargetPageVO
 * @Author: yd
 * @DATE: 2022/2/14
 * @TIME: 12:11
 * @DAY_NAME_SHORT: 周一
 */
@Data
public class ProjectLiquiOrBehaviorPageVO extends Project {

	/**
	 * 行为信息
	 */
	private List<BehaviorOrCaseeVO> behaviorList;

}
