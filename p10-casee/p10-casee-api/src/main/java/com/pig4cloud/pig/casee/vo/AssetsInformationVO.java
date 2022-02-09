package com.pig4cloud.pig.casee.vo;


import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.casee.entity.Assets;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 	抵押物信息DTO
 */
@Data
public class AssetsInformationVO extends Assets {
	/**
	 * 抵押金额
	 */
	private BigDecimal mortgageAmount;

	/**
	 * 抵押时间
	 */
	private LocalDateTime mortgageTime;

	/**
	 * 地址信息
	 */
	private Address address;
}
