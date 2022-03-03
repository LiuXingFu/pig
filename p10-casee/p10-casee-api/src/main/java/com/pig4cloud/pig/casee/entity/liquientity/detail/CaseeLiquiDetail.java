package com.pig4cloud.pig.casee.entity.liquientity.detail;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CaseeLiquiDetail {

	/**
	 * 一审、二审案件最终送达时间
	 */
	private LocalDate finalReceiptTime;
}
