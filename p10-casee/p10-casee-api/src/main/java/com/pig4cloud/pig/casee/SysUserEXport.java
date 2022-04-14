package com.pig4cloud.pig.casee;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SysUserEXport {

	/**
	 * 用户姓名
	 */
	@Excel(name = "用户姓名", height = 20, width = 30, isImportField = "true_st")
	private String name;

	/**
	 * 用户性别
	 */
	@Excel(name = "用户性别", replace = { "男_0", "女_1", "未知_2" }, height = 20, width = 30, isImportField = "true_st")
	private Integer sex;

	/**
	 * 用户年龄
	 */
	@Excel(name = "用户年龄", suffix = "岁", height = 20, width = 30, isImportField = "true_st")
	private Integer age;

	/**
	 * 用户联系方式
	 */
	@Excel(name = "用户联系方式", height = 20, width = 30, isImportField = "true_st")
	private String phone;

	/**
	 * 用户地址
	 */
	@Excel(name = "用户地址", height = 20, width = 30, isImportField = "true_st")
	private String address;
}
