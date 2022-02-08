package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.admin.api.entity.Subject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 	债务人信息DTO
 */
@Data
public class SubjectInformationVO extends Subject {
	/**
	 * 债务类型(1-贷款人 2-担保人)
	 */
	@ApiModelProperty(value = "债务类型(1-贷款人 2-担保人)")
	private Integer debtType;

	/**
	 * 地址id
	 */
	@ApiModelProperty(value = "债务类型(1-贷款人 2-担保人)")
	private Integer addass;

}
