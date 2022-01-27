package com.pig4cloud.pig.casee.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "清收案件人员、协办网点VO")
public class CaseePersonnelVO {
	/**
	 * 案件信息
	 */
	@ApiModelProperty(value="案件信息")
	private CaseeBizLiquiVO caseeBizLiqui;
	/**
	 * 委托详情
	 */
	@ApiModelProperty(value="委托详情")
	private CaseeDetailsVO commissionDetails;

	/**
	 * 受托详情
	 */
	@ApiModelProperty(value="受托详情")
	private CaseeDetailsVO entrustedDetails;

	/**
	 * 被执行人列表
	 */
	@ApiModelProperty(value="被执行人列表")
	private List<CaseePersonnelTypeVO> executedPersonList;
	/**
	 * 担保人列表
	 */
	@ApiModelProperty(value="担保人列表")
	private List<CaseePersonnelTypeVO> guarantorList;

	/**
	 * 协办网点列表
	 */
	@ApiModelProperty(value="协办网点列表")
	private List<CaseeOutlesDealReVo> outlesDealResList;




}
