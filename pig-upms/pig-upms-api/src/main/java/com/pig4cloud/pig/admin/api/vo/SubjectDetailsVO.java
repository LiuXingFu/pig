package com.pig4cloud.pig.admin.api.vo;

import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.admin.api.entity.Subject;
import lombok.Data;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.admin.api.vo
 * @ClassNAME: SubjectDetailsVO
 * @Author: yd
 * @DATE: 2022/2/25
 * @TIME: 14:18
 * @DAY_NAME_SHORT: 周五
 */
@Data
public class SubjectDetailsVO extends Subject {

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
