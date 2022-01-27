package com.pig4cloud.pig.admin.api.dto;

import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.admin.api.entity.Court;
import com.pig4cloud.pig.admin.api.entity.Institution;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InstitutionDTO extends Institution {

	/**
	 * 地址对象
	 */
	Address address;

	/**
	 * 法院对象
	 */
	Court court;

}
