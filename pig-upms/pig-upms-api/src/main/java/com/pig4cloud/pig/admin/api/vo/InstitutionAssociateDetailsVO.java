package com.pig4cloud.pig.admin.api.vo;

import com.pig4cloud.pig.admin.api.entity.InstitutionAssociate;
import lombok.Data;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.admin.api.vo
 * @ClassNAME: InstitutionAssociateDetailsVO
 * @Author: yd
 * @DATE: 2022/2/11
 * @TIME: 9:27
 * @DAY_NAME_SHORT: 周五
 */
@Data
public class InstitutionAssociateDetailsVO extends InstitutionAssociate {


	/**
	 * 机构名称
	 */
	private String insName;

	/**
	 * 机构类型
	 */
	private Integer insType;

	/**
	 * 地址id
	 */
	private Integer addressId;
	/**
	 * 省
	 */
	private String province;
	/**
	 * 市
	 */
	private String city;
	/**
	 * 区
	 */
	private String area;

	/**
	 * 信息地址
	 */
	private String informationAddress;
	/**
	 * 行政区划编号
	 */
	private String code;

}
