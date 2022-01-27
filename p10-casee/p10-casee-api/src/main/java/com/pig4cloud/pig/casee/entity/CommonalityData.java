package com.pig4cloud.pig.casee.entity;

import lombok.Data;

@Data
public class CommonalityData {
	/**
	 * 表单id
	 */
	private String formId;

	/**
	 * 节点名称
	 */
	private String nodeName;

	/**
	 * 顺序
	 */
	private Integer nodeOrder;

	/**
	 * 任务最终进度
	 */
	private Integer finalProcess;

	/**
	 * 当前子节点进度
	 */
	private Integer process;

}
