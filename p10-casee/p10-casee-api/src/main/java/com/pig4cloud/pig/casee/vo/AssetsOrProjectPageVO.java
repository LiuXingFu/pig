package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.casee.entity.Project;
import lombok.Data;

@Data
public class AssetsOrProjectPageVO extends Project {

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
	 * 所有权人
	 */
	private String owner;

	/**
	 * 财产编号/账号
	 */

}
