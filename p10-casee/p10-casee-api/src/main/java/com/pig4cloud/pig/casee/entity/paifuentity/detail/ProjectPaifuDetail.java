package com.pig4cloud.pig.casee.entity.paifuentity.detail;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@ApiModel(value = "拍辅项目详情表")
public class ProjectPaifuDetail {

	/**
	 * 拍卖申请书
	 */
	private String auctionApplicationFile;

	/**
	 * 申请提交时间
	 */
	private LocalDate applicationSubmissionTime;

	/**
	 * 项目总金额
	 */
	BigDecimal projectAmount=BigDecimal.valueOf(0);

	/**
	 * 已回款金额
	 */
	BigDecimal repaymentAmount=BigDecimal.valueOf(0);

}
