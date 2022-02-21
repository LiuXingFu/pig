package com.pig4cloud.pig.casee.vo;

import com.alibaba.fastjson.JSON;
import com.pig4cloud.pig.casee.entity.project.entityzxprocedure.EntityZX_STZX_CCZXCF_CCZXCF;
import com.pig4cloud.pig.casee.entity.project.fundingzxprocedure.FundingZX_ZJZX_ZJZXDJ_ZJZXDJ;
import lombok.Data;

@Data
public class AssetsOrProjectPageVO {

	/**
	 * 财产id
	 */
	private String assetsId;

	/**
	 * 财产名称
	 */
	private String assetsName;

	/**
	 * 财产性质
	 */
	private Integer assetsType;

	/**
	 * 财产类型
	 */
	private Integer type;

	/**
	 * 所有权人
	 */
	private String owner;


	/**
	 * 财产编号/账号
	 */
	private String accountNumber;

	/**
	 * 项目id
	 */
	private Integer projectId;

	/**
	 * 公司业务案号
	 */
	private String companyCode;

	/**
	 * 项目状态
	 */
	private Integer status;

	/**
	 * 债务人
	 */
	private String subjectPersons;

	/**
	 * 是否抵押
	 */
	private Integer assetsSource;

	/**
	 * 财产关联表id
	 */
	private Integer assetsReId;

	/**
	 * 是否首冻(0-否 1-是)
	 */
	private String whetherFirstFrozen;

	/**
	 * 查封情况(0-首封 1-轮候)
	 */
	private String sealUpCondition;

}
