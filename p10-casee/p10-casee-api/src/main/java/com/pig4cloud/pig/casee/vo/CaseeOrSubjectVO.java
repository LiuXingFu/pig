package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.admin.api.entity.Subject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.vo
 * @ClassNAME: CaseeOrSubjectVO
 * @Author: yd
 * @DATE: 2022/2/20
 * @TIME: 16:20
 * @DAY_NAME_SHORT: 周日
 */
@Data
public class CaseeOrSubjectVO extends Subject {

	/**
	 * 类型（0-申请人，1-债务人）
	 */
	@ApiModelProperty(value="类型（0-申请人，1-贷款人，2-担保人）")
	private Integer type;

}
