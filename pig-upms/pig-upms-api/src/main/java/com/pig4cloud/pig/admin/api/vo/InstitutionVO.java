package com.pig4cloud.pig.admin.api.vo;

import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.admin.api.entity.Court;
import com.pig4cloud.pig.admin.api.entity.Institution;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InstitutionVO extends Institution {

	/**
	 * 地址对象
	 */
	Address address;

	/**
	 * 关联法院
	 */
	Court court;

	/**
	 * 关联状态  -1-未申请 0-待确认 100-关联 200-拒绝 300-已断开
	 */
	@ApiModelProperty(value = "关联状态 -1-未申请 0-待确认 100-关联 200-拒绝")
	private Integer associateStatus;

}
