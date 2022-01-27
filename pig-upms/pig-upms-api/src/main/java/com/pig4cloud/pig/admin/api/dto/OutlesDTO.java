package com.pig4cloud.pig.admin.api.dto;

import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.admin.api.entity.Outles;
import lombok.Data;

@Data
public class OutlesDTO extends Outles {

	/**
	 * 地址对象
	 */
	Address address;

}
