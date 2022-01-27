package com.pig4cloud.pig.admin.api.vo;

import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.admin.api.entity.InstitutionAssociate;
import lombok.Data;

@Data
public class InstitutionAssociateVO extends InstitutionAssociate {

	/**
	 * 机构名称
	 */
	private String insName;

	/**
	 * 机构负责人名称
	 */
	private String insPrincipalName;

	/**
	 * 机构负责人联系方式
	 */
	private String insPrincipalPhone;

	/**
	 * 机构类型
	 */
	private Integer insType;

	/**
	 * 机构地址id
	 */
	private Integer addressId;

	/**
	 * 机构地址对象
	 */
	private Address address;

}
