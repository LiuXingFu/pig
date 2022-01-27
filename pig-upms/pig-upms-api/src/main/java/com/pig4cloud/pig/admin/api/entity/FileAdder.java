package com.pig4cloud.pig.admin.api.entity;

import lombok.Data;

/**
 * 文件地址实体类
 */
@Data
public class FileAdder {

	//文件id
	private Long uid;

	//文件名称
	private String name;

	//文件地址
	private String url;

	//提交状态
	private String status;

}
