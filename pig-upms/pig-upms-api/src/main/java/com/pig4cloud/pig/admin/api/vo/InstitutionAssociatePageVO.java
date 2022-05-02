package com.pig4cloud.pig.admin.api.vo;

import com.pig4cloud.pig.admin.api.entity.Institution;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.admin.api.vo
 * @ClassNAME: InstitutionAssociatePageVO
 * @Author: yd
 * @DATE: 2022/2/7
 * @TIME: 16:34
 * @DAY_NAME_SHORT: 周一
 */
@Data
public class InstitutionAssociatePageVO extends Institution {

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

	/**
	 * 关联id
	 */
	private String associateId;

	/**
	 * 关联机构ID
	 */
	private String insAssociateId;

	/**
	 * 关联时间
	 */
	private LocalDateTime associateTime;

	/**
	 * 关联状态  -1-未申请 0-待确认 100-关联 200-拒绝 300-已断开
	 */
	@ApiModelProperty(value = "关联状态 -1-未申请 0-待确认 100-关联 200-拒绝")
	private Integer associateStatus;

	/**
	 * 查询机构状态
	 */
	private Integer institutionType;

}
