package com.pig4cloud.pig.admin.api.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.admin.api.vo
 * @ClassNAME: 合作网点分页VO
 * @Author: yd
 * @DATE: 2022/3/12
 * @TIME: 16:18
 * @DAY_NAME_SHORT: 周六
 */
@Data
public class AssociateOutlesRePageVO {

	/**
	 * 合作网点名称
	 */
	private String cooperateOutlesName;

	/**
	 * 授权机构名称
	 */
	private String authorizeInsName;

	/**
	 * 合作时间
	 */
	private LocalDateTime cooperateTime;

}
