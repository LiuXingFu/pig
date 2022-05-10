package com.pig4cloud.pig.admin.api.vo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class InsOutlesCourtReVO {

	/**
	 * 机构网点法院关联id
	 */
	private Integer insOutlesCourtReId;

	/**
	 * 法院id
	 */
	private Integer courtId;

	/**
	 * 法院名称
	 */
	private String courtName;

	/**
	 * 机构id
	 */
	private Integer insId;

	/**
	 * 机构名称
	 */
	private String insName;

	/**
	 * 网点id
	 */
	private Integer outlesId;

	/**
	 * 网点名称
	 */
	private String outlesName;

	/**
	 * 入驻时间
	 */
	private LocalDateTime authorizationTime;

}
