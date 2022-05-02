package com.pig4cloud.pig.admin.api.vo;

import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.admin.api.entity.Outles;
import lombok.Data;

import java.util.List;

@Data
public class OutlesVO extends Outles {

	/**
	 * 机构名称
	 */
	String insName;

	/**
	 * 机构类型
	 */
	Integer insType;

	/**
	 * 负责人
	 */
	String nickName;

	/**
	 * 负责人电话
	 */
	String phone;

//	/**
//	 * 省的行政区划编号
//	 */
//	private String province;
//
//	/**
//	 * 市的行政区划编号
//	 */
//	private String city;
//
//	/**
//	 * 区的行政区划编号
//	 */
//	private String area;

	/**
	 * 信息地址
	 */
//	private String informationAddress;

	/**
	 * 地址对象
	 */
	Address address;

	/**
	 * 模板id集合
	 */
	List<Integer> templateId;

}
