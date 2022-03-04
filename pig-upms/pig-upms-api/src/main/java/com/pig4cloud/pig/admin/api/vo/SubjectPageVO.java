package com.pig4cloud.pig.admin.api.vo;

import com.pig4cloud.pig.admin.api.entity.Subject;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.admin.api.vo
 * @ClassNAME: SubjectPageVO
 * @Author: yd
 * @DATE: 2022/2/13
 * @TIME: 15:38
 * @DAY_NAME_SHORT: 周日
 */
@Data
public class SubjectPageVO extends Subject {

	/**
	 * 涉案金额
	 */
	private BigDecimal amountInvolved;

}
