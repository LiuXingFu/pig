package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.casee.entity.liquientity.AssetsReLiqui;
import lombok.Data;

/**
 * 案件财产表
 *
 * @author ligt
 * @date 2022-01-11 10:29:44
 */
@Data
public class AssetsReLiquiProjectVO extends AssetsReLiqui {

	/**
	 * 项目id
	 */
	private Integer projectId;

	/**
	 * 案件id
	 */
	private Integer caseeId;

	/**
	 * 公司业务案号
	 */
	private String companyCode;

	/**
	 * 项目状态
	 */
	private Integer projectStatus;

	/**
	 * 执行案号
	 */
	private String caseeNumber;

	/**
	 * 案件状态
	 */
	private Integer caseeStatus;
}
